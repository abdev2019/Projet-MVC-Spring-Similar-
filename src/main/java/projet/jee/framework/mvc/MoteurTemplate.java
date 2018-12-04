package projet.jee.framework.mvc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class MoteurTemplate implements IMoteurTemplate
{ 
	private Document doc; 
	private HttpServletRequest request; 
	private String prefix 		= "myTemplate";
	private String prefixText 	= prefix+":text";
	private String prefixHref 	= prefix+":href";
	private String prefixFor 	= prefix+":for";
	private String prefixIf 	= prefix+":if";
	private String prefixValue 	= prefix+":value";
	private String prefixInsert = prefix+":insert";
	private String prefixAction = prefix+":action"; 
	private String prefixTemplate = prefix+":template";
	private String prefixBlock 	  = prefix+":bloc"; 
	Map<String, Object> data = new HashMap<String, Object>();

	
	

	public MoteurTemplate() { }
	 
	public String getVue(String nomVue, HttpServletRequest request) { 
		this.request = request;
		loadVue(nomVue);
		return doc.toString();
	}
	
	private void loadVue(String nomVue) 
	{ 
		File input = new File(nomVue); 
		try
		{ 
			doc = getTemplate(Jsoup.parse(input, "UTF-8"));
			load(doc.body()); 
		} 
		catch (IOException e) { e.printStackTrace();}
	}
	
	private Document getTemplate(Document doc)
	{
		Element html = doc.getElementsByTag("html").first();
		if(html.hasAttr(prefixTemplate))
		{
			String nameOfTemplate = "/views/html/"+html.attr(prefixTemplate)+".html"; 
			File input = new File(request.getServletContext().getRealPath(nameOfTemplate));
			try
			{
				Document tmpDoc = Jsoup.parse(input, "UTF-8"); 
				Element contenu = doc.getElementsByAttribute(prefixBlock).first();
				tmpDoc.getElementsByAttribute(prefixBlock).html(contenu.html());
				return tmpDoc;
			} 
			catch (IOException e) { e.printStackTrace();}
		}
		return doc;
	}


	private void load(Element elm)
	{  
		Elements elements = elm.children(); 
		
		for(Element element : elements)
		{
			if(element.hasAttr(prefixIf))
			{ 
				if( request.getAttribute(element.attr(prefixIf))==null )
				{
					element.remove();
					continue;
				}
			} 
			
			if(element.hasAttr(prefixText)) 
			{
				element.html( getValueOf( element.attr(prefixText) ) );    
				element.removeAttr(prefixText);
			}
			
			else if(element.hasAttr(prefixFor))   
				traiterBoucleFor(element);  
			
			else if(element.hasAttr(prefixInsert))
			{
				String page = includePage(element.attr(prefixInsert));
				element.html(page);
				element.removeAttr(prefixInsert);
			}
			else
			
				if(element.hasAttr(prefixAction))
				{  
					element.attr("action",request.getContextPath()+element.attr(prefixAction));
					element.removeAttr(prefixAction);
					load(element);
				} 
				
				else if(element.hasAttr(prefixValue)) 
				{
					if(request.getParameter(element.attr(prefixValue))!=null)
						element.attr("value",request.getParameter(element.attr(prefixValue)));
					else 
						element.attr("value",getValueOf(element.attr(prefixValue)));
					element.removeAttr(prefixValue);
				}
			
				else if(element.hasAttr(prefixHref))
				{
					String expression = getResultOf(element.attr(prefixHref)); 
					element.attr("href",request.getContextPath()+expression);
					element.removeAttr(prefixHref);
				} 
			
			else load(element);
		}  
	}

	private void traiterBoucleFor(Element element) 
	{  
		try 
		{ 
			Collection<?> collection = getCollectionOf(element);  
			 
			for(Object obj : collection)
			{   
				data.put(element.attr(prefix+":for").split(":")[0], obj);
				Element elm = element.clone();
				load(elm);  
				element.after(elm); 
				elm.removeAttr(prefix+":for");
			}    
			data.remove(element.attr(prefix+":for").split(":")[0]);
			element.remove();
		} 
		catch (Exception exp) { exp.printStackTrace(); } 
	}
	
	private Collection<?> getCollectionOf(Element element) 
	{
		Collection<?> collection=null;  
		String[] partsExprFor = element.attr(prefix+":for").split(":"); 
		try 
		{
			if(partsExprFor[1].indexOf(".") > 0)
			{
				String[] partsVal = partsExprFor[1].split("\\.");   	
				String nameMeth = "get"+partsVal[1].substring(0, 1).toUpperCase() + partsVal[1].substring(1);
 
				Object obj = data.get(partsVal[0])!=null?
					data.get(partsVal[0]) : request.getAttribute(partsVal[0]);
				Method meth = obj.getClass().getMethod( nameMeth ); 
	
				if(meth.getReturnType().isArray()) 
					collection = 
						new ArrayList<Object>( Arrays.asList( toObjectArray(meth.invoke( obj )) ) );
				
				else collection = (Collection<?>) meth.invoke( obj ); 
			}
			else 
			{
				if(request.getAttribute(partsExprFor[1]) != null)
				{
					if(request.getAttribute(partsExprFor[1]).getClass().isArray())
						collection =  new ArrayList<Object>( Arrays.asList(toObjectArray(request.getAttribute(partsExprFor[1])) ) );
					else 
						collection = (Collection<?>) request.getAttribute(partsExprFor[1]);   
				} 
				else if(data.get(partsExprFor[1]) != null)
				{ 
					if(data.get(partsExprFor[1]).getClass().isArray())
						collection =  new ArrayList<Object>( Arrays.asList(toObjectArray(data.get(partsExprFor[1])) ) );
					else
						collection = (Collection<?>) data.get(partsExprFor[1]); 
				}
			}
		}catch (Exception e) {}
		
		return collection;
	}

	private String getValueOf(String variable) 
	{ 
		String value = "";
		try 
		{ 
			if(variable.indexOf(".")>0)
			{
				String[] parts = variable.split("\\.");  
				String nameMeth = "get"+parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1); ;
				Method meth = null;
				if(data.get(parts[0])!=null)
					meth = data.get(parts[0]).getClass().getMethod( nameMeth );
				else if( request.getAttribute(parts[0]) != null ) 
					meth = request.getAttribute(parts[0]).getClass().getMethod( nameMeth );
				else return "NULL";
				
				Object obj = data.get(parts[0])!=null?
						data.get(parts[0]) : request.getAttribute(parts[0]);
				
				Object result = meth.invoke( obj );
				value = result.toString();
			} 
			else if(request.getParameter(variable)!=null)
				value = request.getParameter(variable).toString(); 
			else if(request.getAttribute(variable)!=null)
					value = request.getAttribute(variable).toString();
			else if(data.get(variable)!=null)
				value = data.get(variable).toString();
			
			else value ="";
		} 
		catch (Exception e) { e.printStackTrace(); }  
		return value;
	}
 
	private String getResultOf(String attr) 
	{
		if( (attr.indexOf("{")==-1) ) return attr;
		
		String href = attr.substring( attr.indexOf("{") + 1, attr.lastIndexOf("}")); 
		return attr.replace("{"+href+"}", getValueOf(href));   
	}

	private String includePage(String name) {
		String relativeWebPath = "/views/html/"+name;
		String nameFileHtml = request.getServletContext().getRealPath(relativeWebPath); 
		return new MoteurTemplate()
					.getVue(nameFileHtml, request);
	}

	
	
	
	private static Object[] toObjectArray(Object array) {
	    int length = java.lang.reflect.Array.getLength(array);
	    Object[] ret = new Object[length];
	    for(int i = 0; i < length; i++)
	        ret[i] = java.lang.reflect.Array.get(array, i);
	    return ret;
	} 
}

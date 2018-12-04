package projet.jee.framework.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.nashorn.internal.parser.JSONParser;
 


public class DispacherController implements IDispacher 
{
	private static final long serialVersionUID = 1L;
        
	private NodeList nList;
	private Map 	 instances = new HashMap<String, Object>();
	private String 	 pathInstances="";
	private String 	 url=""; 
	private String 	 packageProjet="";
	
	private IMoteurTemplate moteurTemplate=null;

	 
	public void dispach(HttpServletRequest request, HttpServletResponse response) 
	{        
		String[] parts = url.split("/"); 
		try  
		{    
			String path = ((HttpServletRequest) request).getRequestURI();
			if (path.endsWith(".css")) {
			     //return;
				System.out.println(path);
			} 
			
			Class cls = Class.forName( 
					packageProjet + parts[2]
				);   

			Controller controller = (Controller) cls.newInstance();
			for(Object obj : instances.values())
            {
                if( obj.getClass().equals(cls) )
                {
                	controller = (Controller) obj;
                	System.out.println("\tInjected instance : "+obj.getClass().getName());
                	break;
                }
            } 

			if(controller==null)
				controller = (Controller) cls.newInstance();
			
			controller.setRequest(request);
			controller.setResponse(response);
			controller.setSession(request.getSession()); 
			 
			// recuperation de donnés
			Map<String, Object> data = new HashMap<String, Object>(); 
			Enumeration<String> names = request.getParameterNames();  
			while( names.hasMoreElements() ) 
			{
				String name = names.nextElement();  ;
				data.put(name, request.getParameter(name)); 
			}
			request.setAttribute("data", data);
			
			// call of method part[3] 
			Method meth = cls.getMethod( parts[3] , new Class[]{Map.class});
			Object result = meth.invoke(controller, data);
			
			
			// resultat de call of method
			if(result instanceof String)
			{
				System.out.println("\tAffichage du page : "+result.toString());  
				if(((String) result).contains("redirect:/"))
				{ 
					response.sendRedirect(  "/"
								+
							((String) result).split("redirect:/")[1]
						);
				}
				else 
				{
					if(moteurTemplate != null)
					{ 
						String relativeWebPath = "/views/html/"+result.toString()+".html";
						String nameFileHtml = request.getServletContext().getRealPath(relativeWebPath);  
						PrintWriter out = response.getWriter();   
				        out.println( moteurTemplate.getVue(nameFileHtml,request) );
				        out.flush();
				        out.close();
					}
					else request.getRequestDispatcher("/views/jsp/"+result.toString()+".jsp").forward(request, response); 
				}
			}
			else 
			{
				if(result instanceof Serializable)
				{
					System.out.println("\tEnvoi d'un objet");
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();   
					
					ObjectMapper mapper = new ObjectMapper();
					
					String ok = "problem";
					try {
						ok = mapper.writeValueAsString(result);
					}
					catch (Exception e) {
					    e.printStackTrace();
					}   
					out.print(ok);
					out.flush();
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Dispacher :\n"+e.getMessage());
			try {
				request.getRequestDispatcher("/views/404.jsp").forward(request, response);
			} catch (Exception e1) {} 
		}
	} 
	
	 
	public void initInstances() 
	{ 
		try 
		{ 
	         File inputFile = new File(pathInstances);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize(); 
	         nList = doc.getElementsByTagName("instance");  
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) 
	         {
			    Node nNode = nList.item(temp);  
			    
			    if (nNode.getNodeType() == Node.ELEMENT_NODE)  
			    { 
			       Element eElement = (Element) nNode;
			         
			       Class cls = Class.forName( 
		    		   eElement.getAttribute("of") 
	    		   );  
			       Object obj = cls.newInstance();  
	  				 
	  				if( eElement.hasChildNodes() ) 
	  				{ 
	  					for(int i=0; i<eElement.getElementsByTagName("inject").getLength(); i++)
	  					{ 
	       					Element cElement = (Element)eElement.getElementsByTagName("inject").item(0);
	       					Object property = this.getBean( cElement.getAttribute("instanceId") ); 
	       					String nm = cElement.getAttribute("property");
	       					String nameMeth = "set"+nm.substring(0, 1).toUpperCase() + nm.substring(1); 
	       					System.out.println("\tAppel à : "+nameMeth+" de "+cls.getName());
	       					Method meth = cls.getMethod( nameMeth , new Class[]{ property.getClass().getInterfaces()[0] });
	       					String label = (String)meth.invoke(obj, property);
	  					} 
	  				} 
			       instances.put(cls.getName(), obj);
			    }
	         }
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public Object  getBean(String idClass) 
	{
		Object obj = null; 
		Class cls=null;
		
		try 
		{ 
			for (int temp = 0; temp < nList.getLength(); temp++) 
			{
	            Node nNode = nList.item(temp);  
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE)  
	            { 
	               Element eElement = (Element) nNode; 
	                 
	               if( idClass.equals(eElement.getAttribute("id")) ) 
	               {   
	            	   cls = Class.forName( 
            	   			(eElement.getAttribute("of")) 
        	   			);  
	       				obj = cls.newInstance();  
	       				 
	       				if( eElement.hasChildNodes() ) 
	       				{ 
	       					for(int i=0; i<eElement.getElementsByTagName("inject").getLength(); i++)
	       					{ 
		       					Element cElement = (Element)eElement.getElementsByTagName("inject").item(0);
		       					Object property = this.getBean( cElement.getAttribute("instanceId") ); 
		       					String nm = cElement.getAttribute("property");
		       					String nameMeth = "set"+nm.substring(0, 1).toUpperCase() + nm.substring(1); 
		       					Method meth = cls.getMethod( nameMeth , new Class[]{ property.getClass().getInterfaces()[0] });
		       					String label = (String)meth.invoke(obj, property);
	       					} 
	       				} 
	               } 
	            } 
			} 
			 
		} 
		catch(Exception e) { e.printStackTrace(); } 
		
		return cls.cast(obj);
	}

	
	
  
	public void setPathInstances(String pathInstances) {
		this.pathInstances = pathInstances;
	}
 
	public void setUrl(String url) {
		this.url = url;
	}
 
	public void setPackageProjet(String packageProjet) {
		this.packageProjet = packageProjet;
	}
 
	public void setMoteurTemplate(IMoteurTemplate moteurTemplate) {
		this.moteurTemplate = moteurTemplate;
		
	}
	 
	
}

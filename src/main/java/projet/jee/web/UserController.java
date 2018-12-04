package projet.jee.web;

import java.util.Map;

import projet.jee.entities.User;
import projet.jee.framework.mvc.Controller;
import projet.jee.idao.IUserDAO;


public class UserController extends Controller
{
	private IUserDAO dao;
	
	public String profile(Map<String, Object> model)
	{ 
		if( this.getSession().getAttribute("user") == null )
			return "login";
		this.getRequest().setAttribute( "user",getSession().getAttribute("user") );
		return "profile";
	}
	 
	public String login(Map<String, Object> model)
	{ 
		if( this.getSession().getAttribute("user") != null ) {
			this.getRequest().setAttribute( "user",model.get("user") );
			return "profile";
		}
	 
		if(this.getRequest().getMethod().equals("POST"))
		{
			String username = model.get("username").toString();
			String password = model.get("password").toString();
			
			User user = dao.findByUsernameAndPassword(username,password);
 
			if(user != null)
			{  
				this.getSession().setAttribute("user", user);
				this.getRequest().setAttribute( "user",user );
				return "profile";
			} 
			else {
				this.getRequest().setAttribute("errorLogin", true);
				return "login";
			}
		}
		
		return "login";
	}
	
	public String deconnecter(Map<String, Object> model)
	{
		this.getSession().removeAttribute("user"); 
		return "login";
	}
 
	public void setDao(IUserDAO dao) {
		this.dao = dao;
	}
}

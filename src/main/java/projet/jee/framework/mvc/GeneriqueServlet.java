package projet.jee.framework.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

@WebServlet(name="dc",urlPatterns="/")
public class GeneriqueServlet extends HttpServlet 
{ 
	private IDispacher dispacher; 
          
	public void init() 
	{  
		// inject static
		dispacher = new DispacherController();
		dispacher.setPathInstances( "instances.xml" );
		dispacher.setPackageProjet("projet.jee.web.");
		dispacher.setMoteurTemplate( new MoteurTemplate() );
		dispacher.initInstances();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispacher.setUrl( request.getRequestURI() ); 
		dispacher.dispach(request,response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

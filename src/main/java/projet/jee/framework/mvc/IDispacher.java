package projet.jee.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDispacher 
{
	public void initInstances();
	public void dispach(HttpServletRequest request, HttpServletResponse response);
	public Object  getBean(String idClass);
	
	public void setPathInstances(String pathInstances);
	public void setUrl(String url);
	public void setPackageProjet(String packageProjet);


	public void setMoteurTemplate(IMoteurTemplate moteurTemplate);
	
}

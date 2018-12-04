package projet.jee.framework.mvc;

import javax.servlet.http.HttpServletRequest;

public interface IMoteurTemplate 
{
	public String getVue(String nomVue, HttpServletRequest request);
}

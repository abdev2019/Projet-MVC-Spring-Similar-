package projet.jee.web;

import java.util.List;
import java.util.Map;

import projet.jee.entities.Etudiant;
import projet.jee.entities.User;
import projet.jee.framework.mvc.Controller;
import projet.jee.idao.IEtudiantDAO; 
 
 

public class EtudiantController extends Controller
{ 
	IEtudiantDAO dao;
	
	public Etudiant getObjectEtudiant(Map<String, Object> model)
	{ 
		return dao.find( 
				Long.valueOf( model.get("id").toString() ) 
		); 
	}
	
	public String index(Map<String, Object> model)
	{ 
		List<Etudiant> liste = null;
		
		String motCleNom = model.get("motCleNom")==null?"":model.get("motCleNom").toString();
		String motCleFiliere = model.get("motCleFiliere")==null?"":model.get("motCleFiliere").toString(); 
		String motCleCne = model.get("motCleCne")==null?"":model.get("motCleCne").toString(); 
 
		liste = dao.findByKeys(motCleNom, motCleCne, motCleFiliere);

		Model m = new Model();
		m.setListeEtudiants(liste);
		m.setName("listeEtudiants");
		this.getRequest().setAttribute("motCleNom", motCleNom);
		this.getRequest().setAttribute("motCleFiliere", motCleFiliere);
		this.getRequest().setAttribute("motCleCne", motCleCne);
		this.getRequest().setAttribute("model", m);
		this.getRequest().setAttribute("listeEtudiants",liste);
		
		return "etudiants";
	}
	
	public String afficher(Map<String, Object> model)
	{ 
		Etudiant etd = dao.find( Long.valueOf(model.get("id").toString()) );
		this.getRequest().setAttribute("etudiant", etd); 
		return "confirmation"; 
	} 
	
	
	public String update(Map<String, Object> model)
	{  
		Etudiant etd = null;
		
		if(this.getRequest().getMethod().equals("POST"))
		{   
			Long id = Long.valueOf( model.get("id").toString() );
			 
			etd = dao.find(id);
			if(etd==null) return "404";
			 
			try {
				etd.setAge( Integer.valueOf( model.get("age").toString() ) );
			}
			catch (Exception e) {
				this.getRequest().setAttribute("errorAge", true); 
				this.getRequest().setAttribute("etudiant", etd); 
				return "updateEtudiant";
			}
			etd.setCne( model.get("cne").toString() );
			etd.setFiliere( model.get("filiere").toString() );
			etd.setNom( model.get("nom").toString() );
			dao.save(etd);
			this.getRequest().setAttribute("updateOk", true); 
		}
		else {
			Long id = Long.valueOf( model.get("id").toString() ); 
			etd = dao.find(id);
			if(etd==null) return "404";
		}
		 
		this.getRequest().setAttribute("etudiant", etd);
		return "updateEtudiant"; 
	}
	
	
	// ajout de nouveau etudiant
	public String ajouter(Map<String, Object> model)
	{   		 
		if(this.getRequest().getMethod().equals("POST"))
		{ 
			Etudiant etd = new Etudiant();
			 
			try {
				etd.setAge( Integer.valueOf( model.get("age").toString() ) );
			}
			catch (Exception e) {
				this.getRequest().setAttribute("errorAge", true);
				return "ajout"; 
			}
			etd.setCne( model.get("cne").toString() );
			etd.setFiliere( model.get("filiere").toString() );
			etd.setNom( model.get("nom").toString() );

			etd = dao.save(etd);
			
			this.getRequest().setAttribute("etudiant", etd);
			this.getRequest().setAttribute("addOk", true);
			return "confirmation"; 
		}
		
		return "ajout"; 
	}
	
	public String delete(Map<String, Object> model)
	{ 
		Long id = Long.valueOf( model.get("id").toString() ); 
		if( dao.delete(id) )
			this.getRequest().setAttribute("deleteOk", true);
		return index(model); 
	}
	
	
	public void setDao(IEtudiantDAO dao)
	{
		this.dao = dao;
	}
}






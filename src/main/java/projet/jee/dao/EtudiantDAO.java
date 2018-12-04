package projet.jee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projet.jee.entities.Etudiant;
import projet.jee.framework.mvc.BddAccess;
import projet.jee.framework.mvc.Factory;
import projet.jee.idao.IEtudiantDAO;



public class EtudiantDAO implements IEtudiantDAO
{   
	private Factory factory;
	
	public EtudiantDAO() { 
		factory = new Factory<Etudiant>(Etudiant.class); 
	}  
   
	public Etudiant save(Etudiant etd) 
	{
		if(etd.getId()==0)
			etd = (Etudiant) factory.insertObject(etd); 
		else 
			factory.updateObject(etd);
		return etd;
	}
	
	// recupere la liste des etudiant par rapport au nom; cne; filiere
	public List<Etudiant> findByKeys( Object... arguments) 
	{
		String nom = arguments[0].toString(); 
		String cne = arguments[1].toString(); 
		String filiere = arguments[2].toString();
		List<Etudiant> lst = new ArrayList<Etudiant>();
		try 
		{ 	
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement(
				"select * from etudiant where nom like ? and cne like ? and filiere like ?"
			);
	       
			pStmt.setString(1, "%"+nom+"%");
			pStmt.setString(2, "%"+cne+"%");
			pStmt.setString(3, "%"+filiere+"%");
	        pStmt.execute();
			
	        ResultSet rs = pStmt.getResultSet(); 

	        while (rs.next()) 
			{
	        	Etudiant etd= (Etudiant) factory.makeObject(rs);
	        	lst.add(etd);
			} 
		}
		catch (Exception e) {System.err.println("findByKey");}

		return lst;
	}

	
	public Etudiant find(long id) {
		Etudiant etd = (Etudiant)factory.find(id);
		return etd;
	}

	
	public boolean delete(long id) {
		return factory.delete(id);
	}

	
	public List<Etudiant> findAll() {
		List<Etudiant> lst = factory.findAll();   
		return lst;
	}
 
}

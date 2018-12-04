package projet.jee.web;

import java.util.List;

import projet.jee.entities.Etudiant;

public class Model
{
	public String name;
	public List<Etudiant> listeEtudiants;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Etudiant> getListeEtudiants() {
		return listeEtudiants;
	}
	public void setListeEtudiants(List<Etudiant> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}
	
	
}

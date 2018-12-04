package projet.jee.entities;

import java.io.Serializable;



public class Etudiant implements Serializable
{ 
	private long id; 
	private String nom;
	private String cne;
	private String filiere;
	private int age;
	private static final long serialVersionUID = 1L;
	
	private double notes[] = {1d,2d,3d,4d,5d};
	 

	public Etudiant() {}
	
	 
	public Etudiant(long id, String nom, String cne, String filiere, int age) {
		super();
		this.id = id;
		this.cne = cne;
		this.filiere = filiere;
		this.age = age;
		this.nom = nom;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	

	public double[] getNotes() {
		return notes;
	}


	public void setNotes(double[] notes) {
		this.notes = notes;
	}

	
	@Override
	public String toString() {
		return "{"+id+","+nom+","+cne+","+filiere+","+age+"}";
	}

}

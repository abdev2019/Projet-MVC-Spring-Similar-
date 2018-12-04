package projet.jee.idao;

import java.util.List;

import projet.jee.entities.Etudiant;
 

public interface IDao <T>
{
	public T find(long id);
	public T save(T obj);
	public boolean delete(long id); 
	public List<T> findAll();
	public List<T> findByKeys(Object... arguments); 
	
}

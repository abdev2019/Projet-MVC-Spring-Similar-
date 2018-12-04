package projet.jee.dao;

import java.util.ArrayList;
import java.util.List;

import projet.jee.entities.Etudiant;
import projet.jee.entities.User;
import projet.jee.idao.IUserDAO;

public class UserDAO implements IUserDAO
{
	public static List<User> data = new ArrayList<User>()
	{{
		add( new User(1,"username","password") );
		add( new User(2,"admin"   ,"admin") ); 
	}};
	
	public User findByUsernameAndPassword(String username, String password) 
	{
		for(User user : data)
		{
			if( user.getPassword().equals(password) && user.getUsername().equals(username) ) 
				return user;
		}
		
		return null;
	}

	public User find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User save(User obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findByKeys(Object... arguments) {
		// TODO Auto-generated method stub
		return null;
	}

}

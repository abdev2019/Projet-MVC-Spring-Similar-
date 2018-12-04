package projet.jee.idao;
 
import projet.jee.entities.User;

public interface IUserDAO extends IDao<User>
{
	public User findByUsernameAndPassword(String username, String password);
}

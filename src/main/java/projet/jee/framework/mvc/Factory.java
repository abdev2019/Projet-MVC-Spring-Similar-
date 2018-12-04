package projet.jee.framework.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData; 
import java.util.ArrayList;
import java.util.List;

import projet.jee.entities.Etudiant;


public class Factory<T>
{ 
	private Class<?> typeObject;
	private ResultSetMetaData metaData;
	private String table;
	
	public Factory(Class<?> class1) {
		typeObject=class1; 
		table = class1.getSimpleName().toLowerCase(); 
		loadMetaData();
	}

	public Object makeObject(ResultSet rs) 
	{ 
		Object obj=null; 
		try 
		{  
			obj = typeObject.newInstance(); 
  
			for (int i = 1; i <= metaData.getColumnCount(); i++)
			{
				String columnName = metaData.getColumnLabel(i);
				String nameMethod = "set"+columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
				Class<?> typeField = getFieldClass(typeObject, columnName); 
				Method methObject = typeObject.getMethod(nameMethod,new Class[]{ typeField });
		   
				// method like rs.getInt(); 
				String type = typeField.getSimpleName();
				  
				Method methResult = rs.getClass().getMethod(
						"get"+type.substring(0, 1).toUpperCase() + type.substring(1),
						new Class[]{ String.class }
				);
		  
				Object res = methResult.invoke(rs,columnName);  
				methObject.invoke(obj, res); 
		   }  
		}
		catch (Exception e) {System.err.println("makeObject");e.printStackTrace();}
	        
        return obj;
	} 
	
	public Object insertObject(Object obj) 
	{
		try 
		{ 	  
			String req = "insert into "+table+"(";  
			String values="values(";   
 
			for(int i=1; i<metaData.getColumnCount();i++)
			{    
				req += metaData.getColumnLabel(i)+","; 
				values += "?,";
			}
			values += "?)"; 
			req += metaData.getColumnLabel(metaData.getColumnCount())+")"+values;
			
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement(req);
			 
			for (int i = 1; i <= metaData.getColumnCount(); i++)
			{    
				Field field = obj.getClass().getDeclaredField(metaData.getColumnLabel(i)); 
				if(field==null) continue;
				field.setAccessible(true);  

				String type = field.getType().getSimpleName();    
				Method methResult = pStmt.getClass().getMethod(
						"set"+type.substring(0, 1).toUpperCase() + type.substring(1),
						new Class[]{ int.class, field.getType() }
				);
				methResult.invoke(pStmt,i,field.get(obj));   
			}   
			
			System.out.println(pStmt);
		 
	        pStmt.execute();  
	        pStmt.close();  
	        return findLastInserted();
		}
		catch (Exception e) {System.err.println("insertObject"); e.printStackTrace();} 
 
		return null;
	}
	
	public boolean updateObject(Object obj) 
	{
		try 
		{ 	 
			String req = "update "+table+" set ";   
			for(int i=2; i<metaData.getColumnCount();i++)  
				req += metaData.getColumnLabel(i)+"=?,"; 
			req += metaData.getColumnLabel( metaData.getColumnCount() )+"=? where "+metaData.getColumnLabel(1)+"=?";
			
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement(req);
			   
			Field field = null;  
			for (int i = 2; i <= metaData.getColumnCount(); i++)
			{  
				field = obj.getClass().getDeclaredField( metaData.getColumnLabel(i) );
				if(field==null) continue;
				field.setAccessible(true);
				String type = field.getType().getSimpleName();  
				Method methResult = pStmt.getClass().getMethod(
						"set"+type.substring(0, 1).toUpperCase() + type.substring(1),
						new Class[]{ int.class, field.getType() }
				);
				methResult.invoke(pStmt,i-1,field.get(obj));   
		   }   
			field = obj.getClass().getDeclaredField( metaData.getColumnLabel(1) ); 
			field.setAccessible(true);
			String type = field.getType().getSimpleName();  
			Method methResult = pStmt.getClass().getMethod(
					"set"+type.substring(0, 1).toUpperCase() + type.substring(1),
					new Class[]{ int.class, field.getType() }
			);
			methResult.invoke(pStmt,metaData.getColumnCount(),field.get(obj));   
 
			System.out.println(pStmt);
	        pStmt.execute();  
	        pStmt.close();
	        return true;
		}
		catch (Exception e) {System.err.println("updateObject"); e.printStackTrace();} 
 
		return false;
	}
	
	public Object find(long id)
	{
		Object obj=null;
		try 
		{ 	
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement(
				"select * from "+table+" where "+typeObject.getDeclaredFields()[0].getName()+"=?"
			);  
			pStmt.setLong(1, id);
	        pStmt.execute();  
	        ResultSet rs = pStmt.getResultSet(); 

	        while (rs.next())  obj = this.makeObject(rs); 
	        rs.close();
	        pStmt.close();
		}
		catch (Exception e) {System.err.println("Factory.find");}
		return obj;
	}

	public Object findLastInserted()
	{
		Object obj=null;
		try 
		{ 	 
			String idColumn = typeObject.getDeclaredFields()[0].getName();
			
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement( 
				"SELECT * FROM "+table+" where "+idColumn+" =( SELECT max(id) from "+table+" )"
			);   
	        pStmt.execute();  
	        ResultSet rs = pStmt.getResultSet();  
	        while (rs.next())  obj = this.makeObject(rs); 
	        rs.close();
	        pStmt.close();
		}
		catch (Exception e) {System.err.println("Factory.findLastInserted");}
		return obj;
	}
	
	public boolean delete(long id)
	{ 
		try 
		{ 	
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement(
				"delete from "+table+" where "+typeObject.getDeclaredFields()[0].getName()+"=?"
			);  
			pStmt.setLong(1, id);
	        pStmt.execute();  
	        pStmt.close();
	        return true;
		}
		catch (Exception e) {System.err.println("Factory.delete");}
		return false;
	}

	public List<T> findAll() 
	{ 
		List<T> list = new ArrayList<T>();
		T obj=null;
		try 
		{ 	
			PreparedStatement pStmt  = BddAccess.getConnection().prepareStatement( "select * from "+table );  
	        pStmt.execute(); 
	        ResultSet rs = pStmt.getResultSet(); 

	        while (rs.next())  {
	        	obj = (T) this.makeObject(rs); 
	        	list.add(obj);
	        }
	        rs.close();
	        pStmt.close();
		}
		catch (Exception e) {System.err.println("Factory.findAll");}
		
		return list;
	}
	
	private void loadMetaData()
	{
		try 
		{
			PreparedStatement st  = BddAccess.getConnection().prepareStatement("select * from "+table+" where 1<>2");
			ResultSet rs = st.executeQuery();
			metaData = rs.getMetaData(); 
			rs.close();
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	private static Class<?> getFieldClass(Class<?> clazz, String name) {
	    if (clazz==null || name==null || name.isEmpty()) {
	        return null;
	    }
	    name = name.toLowerCase();
	    Class<?> propertyClass = null;
	    for (Field field : clazz.getDeclaredFields()) {
	        field.setAccessible(true);
	        if (field.getName().equals(name)) {
	            propertyClass = field.getType();
	            break;
	        }
	    }
	    return propertyClass;
	}
	
}

	
	 
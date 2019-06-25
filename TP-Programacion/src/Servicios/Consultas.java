package Servicios;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Anotaciones.Id;
import Anotaciones.Columna;
import Anotaciones.Tabla;

import Utilidades.UBean;
import Utilidades.UConexion;

public class Consultas {
	
	public static Object guardar(Object o) 
	{
		try{ 
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();	
			String valores = "";
			String atributos = "";
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			
			for(Field f: fields)
			{
				if(valores =="")
				{
					if(f.getAnnotation(Id.class) == null)
					{
						valores += "'"+f.get(o)+"'";
					}
				}
				else
				{
					if(f.getAnnotation(Id.class) == null)
					{
						valores +=",'"+f.get(o)+"'";
					}
				}
			}
			
			
			
			for(Field f: fields)
			{
				if(atributos =="")
				{
					if(f.getAnnotation(Id.class) == null)
					{
						atributos =atributos + "("+((Columna)f.getAnnotation(Columna.class)).nombre();
					}
				}
				else{
					if(f.getAnnotation(Id.class) == null)
					{
						atributos =atributos + ","+((Columna)f.getAnnotation(Columna.class)).nombre();
					}
				}
			}
			atributos =atributos + ")";
			
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			Statement st = con.createStatement();
			st.execute("INSERT into "+ tabla + atributos +" values ("+valores+")",Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			
			if (rs.next()) 
			{
		         UBean.ejecutarSet(o, "Id", rs.getInt(1));
			}
			con.close();
			return o;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return o;
	}
	
	public static void modificar(Object o)
	{
		try{
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();
			String consulta = "";
			String valor = "";
			String atributo = "";
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			for(Field f: fields)
			{
				if(f.getAnnotation(Id.class) != null)
				{
					valor = f.get(o).toString();
					atributo = f.getName().toLowerCase();
					
				}
				else
				{
					if(consulta == "")
					{
						consulta = ((Columna)f.getAnnotation(Columna.class)).nombre() + "='" +f.get(o).toString()+"'";
					}
					else
					{
						consulta = consulta + ","+((Columna)f.getAnnotation(Columna.class)).nombre() + "='" +f.get(o).toString()+"'";
				
					}
				}
				
			}
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			Statement st = con.createStatement();
			st.execute("UPDATE "+ tabla + " SET "+consulta +" WHERE "+atributo + "="+valor);
			con.close();
			}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	
	
	public static void eliminar(Object o)
	{
		try{
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			String valor = "";
			String atributo = "";
			
			for(Field f: fields)
			{
				if(f.getAnnotation(Id.class) != null)
				{
					valor = f.get(o).toString();
					atributo = f.getName().toLowerCase();
				}
			}
			
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			
			Statement st = con.createStatement();
			st.execute("DELETE FROM "+ tabla + " WHERE "+atributo + "="+valor);
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static Object obtenerPorId(Class c,Object ob)
	{
		try{
			Object o= c.getConstructor(null).newInstance(null);
			UBean.ejecutarSet(o, "Id", ob);
			Field[] fields = o.getClass().getDeclaredFields();
			String valor = "";
			String atributo = "";
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			for(Field f: fields)
			{
				if(f.getAnnotation(Id.class) != null)
				{
					valor = f.get(o).toString();
					atributo = f.getName().toLowerCase();
				}
			}
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			PreparedStatement st = con.prepareStatement("SELECT *"+" FROM "+tabla + " WHERE "+atributo +" = "+valor);
			ResultSet rs = st.executeQuery();
			
			System.out.println(rs);
			while(rs.next())
			{
				for(Field f: fields)
				{
					if(f.getAnnotation(Columna.class) != null)
					{
						UBean.ejecutarSet(o, f.getName(), rs.getObject(((Columna)f.getAnnotation(Columna.class)).nombre()));
					}
				}
			}
			con.close();
			return o;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ob;
	}
	


}

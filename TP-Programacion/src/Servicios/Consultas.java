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
	
	public static void guardar(Object o) 
	{
		try{ 
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();	
			String valores = "";
			String columnas = "";
			
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
			
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			for(Field f: fields)
			{
				if(columnas =="")
				{
					if(f.getAnnotation(Id.class) == null)
					{
						columnas =columnas + "("+((Columna)f.getAnnotation(Columna.class)).nombre();
					}
				}
				else{
					if(f.getAnnotation(Id.class) == null)
					{
						columnas =columnas + ","+((Columna)f.getAnnotation(Columna.class)).nombre();
					}
				}
			}
			columnas =columnas + ")";
			
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			Statement st = con.createStatement();
			st.execute("INSERT into "+ tabla + columnas +" values ("+valores+")",Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = st.getGeneratedKeys();
			
			if (generatedKeys.next()) 
			{
			         System.out.println(generatedKeys.getInt(1));
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}
	}
	
	public static void modificar(Object o)
	{
		try{
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();
			String query = "";
			String valorCondicion = "";
			String columnaCondicion = "";
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			for(Field f: fields)
			{
				if(f.getAnnotation(Id.class) != null)
				{
					valorCondicion = f.get(o).toString();
					columnaCondicion = ((Columna)f.getAnnotation(Columna.class)).nombre();
				}
				else
				{
					if(query == "")
					{
						query = ((Columna)f.getAnnotation(Columna.class)).nombre() + "='" +f.get(o).toString()+"'";
					}
					else
					{
						query = query + ","+((Columna)f.getAnnotation(Columna.class)).nombre() + "='" +f.get(o).toString()+"'";
				
					}
				}
			}
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			
				Statement st = con.createStatement();
				st.execute("UPDATE "+ tabla + " SET "+query +" WHERE "+columnaCondicion + "="+valorCondicion);
				con.close();
			}
		catch(Exception e){
			System.out.println(e.getMessage()); 
		}
	}
	
	
	public static void eliminar(Object o)
	{
		try{
			Class c = o.getClass();
			Field[] fields = o.getClass().getDeclaredFields();
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			String valorCondicion = "";
			String columnaCondicion = "";
			
			for(Field f: fields)
			{
				if(f.getAnnotation(Id.class) != null)
				{
					valorCondicion = f.get(o).toString();
					columnaCondicion = ((Columna)f.getAnnotation(Columna.class)).nombre();
				}
			}
			
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			
			Statement st = con.createStatement();
			st.execute("DELETE FROM "+ tabla + " WHERE "+columnaCondicion + "="+valorCondicion);
			con.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage()); 
		}
	}
	
	
	public static Object obtenerPorId(Class c,Object o)
	{
		try{
			Field[] fields = o.getClass().getDeclaredFields();
			String columnas = "";
			String valorCondicion = "";
			String columnaCondicion = "";
			String tabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();
			
			for(Field f: fields)
			{
				if(columnas == "")
				{
					columnas = ((Columna)f.getAnnotation(Columna.class)).nombre();
				}
				else
				{
					columnas += ","+((Columna)f.getAnnotation(Columna.class)).nombre();
				}
				if(f.getAnnotation(Id.class) != null)
				{
					valorCondicion = f.get(o).toString();
					columnaCondicion = ((Columna)f.getAnnotation(Columna.class)).nombre();
				}
			}
			UConexion u = UConexion.crearInstancia();
			Connection con = u.getConection();
			
			System.out.println("SELECT "+columnas+" FROM "+tabla + " WHERE "+columnaCondicion +" = ?");
			PreparedStatement st = con.prepareStatement("SELECT "+columnas+" FROM "+tabla + " WHERE "+columnaCondicion +" = ?");
			st.setLong(1, Long.valueOf(valorCondicion));
			ResultSet rs = st.executeQuery();
			while(rs.next())
			{
				for(Field field: fields)
				{
					UBean.ejecutarSet(o, field.getName(), rs.getObject(((Columna)field.getAnnotation(Columna.class)).nombre()));
				}
				
			}
			con.close();
			System.out.println(o.toString());
		}
		catch(Exception e){
			System.out.println(e.getMessage()); 
		}
		return o;
	}
	


}

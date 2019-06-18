package Utilidades;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class UConexion {
	
	private static UConexion instancia;
	private Connection con= null;
	
	private UConexion()
	{
		//DriverManager.getConnection(s,"root","");
	}
	
	public static UConexion crearInstancia()
	{
		if(instancia==null)
		{
			instancia = new UConexion();
		}
			
		return instancia;
	
	}

private Connection Conectar(){
		
		Properties p = new Properties();
		try{
			p.load(new FileReader("framework.properties"));
			Class.forName(p.getProperty("driver"));
			String s = p.getProperty("base");
			this.con = DriverManager.getConnection(s,p.getProperty("usuario"),p.getProperty("clave"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return this.con;
	}
	public Connection getConection(){
		if(this.con==null)
		{
			return this.Conectar();
		}
		else
		{
			return this.con;
		}
	}
}

package Utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UBean {
	
	public static ArrayList<Field> obtenerAtributos(Object o)
	{
		List<Field> Retorno = new ArrayList<Field>();
		Class c = o.getClass();
		Field[] attr = c.getDeclaredFields();
		
		for(Field f: attr)
		{
			Retorno.add(f);
		}
		
		return (ArrayList<Field>) Retorno;
	}
	
	public static void ejecutarSet(Object o, String att, Object valor)
	{
		try{
			Class c = o.getClass();
			Object[] obj = new Object[1];
			obj[0] = valor;
			Method[] met = c.getDeclaredMethods();
			String metodo = "set".concat( att.substring(0, 1).toUpperCase()).concat( att.substring(1).toLowerCase());  
			
			for(Method m: met)
			{
				if(m.getName().equals(metodo))
				{	
					m.invoke(o, obj);
				}
			}
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static Object ejecutarGet(Object o, String att)
	{
		try 
		{
			Class c = o.getClass();
			Object obj = new Object();
			Method[] met = c.getDeclaredMethods();
			String metodo = "get".concat( att.substring(0, 1).toUpperCase()).concat(att.substring(1).toLowerCase());  
			
			for(Method m: met)
			{
				if(m.getName().equals(metodo))
				{
						return m.invoke(o, null);
				}
			}
			
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}

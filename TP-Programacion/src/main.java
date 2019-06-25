import Servicios.Consultas;
import Utilidades.UBean;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("main");
		persona p =new persona("nombre","apellido");
		persona p2 =new persona();
		//System.out.println( Consultas.guardar(p).toString());
		
		//UBean.ejecutarSet(p, "Id", 2);
		//UBean.ejecutarSet(p, "nombre", "juan");
		//Consultas.modificar(p);
		
		
		
		//UBean.ejecutarSet(p, "Id", 4);
		//Consultas.eliminar(p);
		
		UBean.ejecutarSet(p2, "Id", 2);
		System.out.println( Consultas.obtenerPorId(p2.getClass(),2));
	}

}

import Servicios.Consultas;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("main");
		persona p =new persona("nombre","apellido");
		Consultas.guardar(p);
	}

}

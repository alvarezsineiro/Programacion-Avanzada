import Anotaciones.Columna;
import Anotaciones.Tabla;
import Anotaciones.Id;

@Tabla(nombre = "personas")
public class persona {

	
	@Id
	public Integer Id;
	@Columna(nombre = "nombre")
	public String nombre;
	@Columna(nombre = "apellido")
	public String apellido;
	
	
	
	public persona()
	{
		
	}
	
	public persona(String nombre, String apellido) {
		super();
		//Id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "Persona [Id=" + Id + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}
	
	
}

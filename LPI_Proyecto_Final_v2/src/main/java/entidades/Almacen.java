package entidades;

public class Almacen {

	private int almacenId;
	private String nombre;
	private String ubicacion;
	private String descripcion;
	
	public Almacen(int almacenId, String nombre, String ubicacion, String descripcion) {
		super();
		this.almacenId = almacenId;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
	}
	public Almacen(String nombre, String ubicacion, String descripcion) {
		this(0, nombre, ubicacion, descripcion);
	}
	public Almacen() {
		this(0, "", "", "");
	}
	public int getAlmacenId() {
		return almacenId;
	}
	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
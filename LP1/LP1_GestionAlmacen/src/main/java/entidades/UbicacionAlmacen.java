package entidades;

public class UbicacionAlmacen {
    private int id_ubicacion;
    private String nombre_ubicacion;
    private String descripcion;

    // Constructores, Getters y Setters...
    public UbicacionAlmacen() {}

    public UbicacionAlmacen(int id_ubicacion, String nombre_ubicacion, String descripcion) {
        this.id_ubicacion = id_ubicacion;
        this.nombre_ubicacion = nombre_ubicacion;
        this.descripcion = descripcion;
    }

	public int getId_ubicacion() {
		return id_ubicacion;
	}

	public void setId_ubicacion(int id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	public String getNombre_ubicacion() {
		return nombre_ubicacion;
	}

	public void setNombre_ubicacion(String nombre_ubicacion) {
		this.nombre_ubicacion = nombre_ubicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

   	@Override
}
package entidades;

public class Proveedor {
	
	private int proveedorId;
	private String nombre;
	private String contacto;
	private String telefono;
	private String email;
	
	public Proveedor(int proveedorId, String nombre, String contacto, String telefono, String email) {
		super();
		this.proveedorId = proveedorId;
		this.nombre = nombre;
		this.contacto = contacto;
		this.telefono = telefono;
		this.email = email;
	}

	public Proveedor(String nombre, String contacto, String telefono, String email) {
		this(0, nombre, contacto, telefono, email);
	}
	public Proveedor() {
		this(0, "", "", "", "");
	}

	public int getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(int proveedorId) {
		this.proveedorId = proveedorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

package entidades;

public class Personal {
    private int id_personal;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private String tipo_personal; // 'Operador', 'Administrador', 'Supervisor'
    private String username;
    private String password_hash;

    // Constructores, Getters y Setters...
    public Personal() {}

    public Personal(int id_personal, String nombre, String apellido, String dni, String email, String telefono, String tipo_personal, String username, String password_hash) {
        this.id_personal = id_personal;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.tipo_personal = tipo_personal;
        this.username = username;
        this.password_hash = password_hash;
    }

	public int getId_personal() {
		return id_personal;
	}

	public void setId_personal(int id_personal) {
		this.id_personal = id_personal;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo_personal() {
		return tipo_personal;
	}

	public void setTipo_personal(String tipo_personal) {
		this.tipo_personal = tipo_personal;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
    
    @Override
	public String toString() {
		return "Personal{" +
				"id_personal=" + id_personal +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", dni='" + dni + '\'' +
				", email='" + email + '\'' +
				", telefono='" + telefono + '\'' +
				", tipo_personal='" + tipo_personal + '\'' +
				", username='" + username + '\'' +
				", password_hash='" + password_hash + '\'' +
				'}';
	}
}
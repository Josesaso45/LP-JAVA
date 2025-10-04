package entidades;

public class Chofer {
	private int codigo;
    private String nombre;
    private String apellidos;
    private int estado;
    
    public Chofer() {
    }
    
    public Chofer(int codigo, String nombre, String apellidos, int estado) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.estado = estado;
	}
    
    public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}

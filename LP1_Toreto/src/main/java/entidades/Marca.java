package entidades;

public class Marca {
	private int marcaId;
	private String nombreMarca;
	
	public Marca(int marcaId, String nombreMarca) {
		super();
		this.marcaId = marcaId;
		this.nombreMarca = nombreMarca;
	}
	public Marca(String nombreMarca) {
		this(0, nombreMarca);
	}
	public Marca() {
		this(0, "");
	}
	
	public int getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(int marcaId) {
		this.marcaId = marcaId;
	}
	public String getNombreMarca() {
		return nombreMarca;
	}
	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}
	@Override
	public String toString() {
		return "Marca [marcaId=" + marcaId + ", nombreMarca=" + nombreMarca + "]";
	}
}

package entidades;

import java.math.BigDecimal;


public class Auto {
	
	private int autoId;
	private String modelo;
	private int marcaId;
	private String color;
	private BigDecimal precio;
	
	private String nombreMarca;
	
	public Auto(int autoId, String modelo, int marcaId, String color, BigDecimal precio) {
		super();
		this.autoId = autoId;
		this.modelo = modelo;
		this.marcaId = marcaId;
		this.color = color;
		this.precio = precio;
	}
	public Auto(String modelo, int marcaId, String color, BigDecimal precio) {
		this(0, modelo, marcaId, color, precio);
	}
	public Auto() {
		this(0, "", 0, "", new BigDecimal(0));
	}
	
	public int getAutoId() {
		return autoId;
	}
	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(int marcaId) {
		this.marcaId = marcaId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public String getNombreMarca() {
		return nombreMarca;
	}
	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}
	@Override
	public String toString() {
		return "Auto [autoId=" + autoId + ", modelo=" + modelo + ", marcaId=" + marcaId + ", color=" + color
				+ ", precio=" + precio + ", nombreMarca=" + nombreMarca + "]";
	}
}

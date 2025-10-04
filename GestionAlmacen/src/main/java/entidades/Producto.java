package entidades;

import java.math.BigDecimal;

public class Producto {
    private int id_producto;
    private String nombre_producto;
    private String descripcion;
    private BigDecimal precio_unitario;
    private int cantidad_stock;
    private int id_proveedor;
    private int id_ubicacion;

    // Constructores, Getters y Setters
    public Producto() {}

    public Producto(int id_producto, String nombre_producto, String descripcion, BigDecimal precio_unitario, int cantidad_stock, int id_proveedor, int id_ubicacion) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
        this.cantidad_stock = cantidad_stock;
        this.id_proveedor = id_proveedor;
        this.id_ubicacion = id_ubicacion;
    }

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(BigDecimal precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public int getCantidad_stock() {
		return cantidad_stock;
	}

	public void setCantidad_stock(int cantidad_stock) {
		this.cantidad_stock = cantidad_stock;
	}

	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public int getId_ubicacion() {
		return id_ubicacion;
	}

	public void setId_ubicacion(int id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

    	@Override
}
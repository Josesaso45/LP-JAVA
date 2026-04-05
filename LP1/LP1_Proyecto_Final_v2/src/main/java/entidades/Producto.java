package entidades;

public class Producto {
	private int productoId;
	private String nombre;
	private String descripcion;
	private int precio;
	private String stock;
	private int proveedorId;
	private int almacenId;
	
	private String nombreProveedor;
	private String nombreAlmacen;
	
	public Producto(int productoId, String nombre, String descripcion, int precio, String stock, int proveedorId, int almacenId) {
		super();
		this.productoId = productoId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.proveedorId = proveedorId;
		this.almacenId = almacenId;
	}
	
	public Producto(String nombre, String descripcion, int precio, String stock, int proveedorId, int almacenId) {
		this(0 , nombre, descripcion, precio, stock, proveedorId, almacenId);
	}
	
	public Producto() {
		this(0, "", "", 0, "", 0, 0 );
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(int proveedorId) {
		this.proveedorId = proveedorId;
	}

	public int getAlmacenId() {
		return almacenId;
	}

	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getNombreAlmacen() {
		return nombreAlmacen;
	}

	public void setNombreAlmacen(String nombreAlmacen) {
		this.nombreAlmacen = nombreAlmacen;
	}
	
}
	
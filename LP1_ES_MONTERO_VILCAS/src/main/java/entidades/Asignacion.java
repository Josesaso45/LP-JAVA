package entidades;

public class Asignacion {
	
	private int numero;
    private int codigo_chofer;
    private String placa_bus;
    private String fecha;
    
	private String nombre_completo_chofer;

    public Asignacion() {
	}
    
    public Asignacion(int numero, int codigo_chofer, String placa_bus, String fecha) {
		this.numero = numero;
		this.codigo_chofer = codigo_chofer;
		this.placa_bus = placa_bus;
		this.fecha = fecha;
	}
    
    public String getNombre_completo_chofer() {
        return nombre_completo_chofer;
    }

    public void setNombre_completo_chofer(String nombre_completo_chofer) {
        this.nombre_completo_chofer = nombre_completo_chofer;
    }

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodigo_chofer() {
		return codigo_chofer;
	}

	public void setCodigo_chofer(int codigo_chofer) {
		this.codigo_chofer = codigo_chofer;
	}

	public String getPlaca_bus() {
		return placa_bus;
	}

	public void setPlaca_bus(String placa_bus) {
		this.placa_bus = placa_bus;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
    
    
}

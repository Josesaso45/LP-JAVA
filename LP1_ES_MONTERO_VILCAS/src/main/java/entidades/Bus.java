package entidades;

public class Bus {
	private String placa;
    private String modelo;
    private int capacidad;
    
    public Bus() {
	}
    
    public Bus(String placa, String modelo, int capacidad) {
    			this.placa = placa;
    			this.modelo = modelo;
    			this.capacidad = capacidad;
    }

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
    
    
}

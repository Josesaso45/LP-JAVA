package entidades;

public class Curso {
	private int cursoId; //curso_ID
	private String codigo;
	private String nombre;
	private int vacantes;
	
	public Curso(int cursoId, String codigo, String nombre, int vacantes) {
		super();
		this.cursoId = cursoId;
		this.codigo = codigo;
		this.nombre = nombre;
		this.vacantes = vacantes;
	}
	
	public Curso(String codigo, String nombre, int vacantes) {
		this(0,codigo,nombre,vacantes);
	}
	
	public Curso() {
		this(0,"","",0);
	}
}

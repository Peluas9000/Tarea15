package pojo;

public class Grupo {
	private int id;
	private Alumno a;
	private int niaAlumno;
	private String nombreAlumno;

	public Grupo(int id, Alumno a) {
		super();
		this.id = id;
		this.a = a;
		this.niaAlumno = a.getNia();
		this.nombreAlumno = a.getNombre();
	}

	public Grupo(int id, int niaAlumno, String nombreAlumno) {
		super();
		this.id = id;
		this.niaAlumno = niaAlumno;
		this.nombreAlumno = nombreAlumno;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", a=" + a + ", niaAlumno=" + niaAlumno + ", nombreAlumno=" + nombreAlumno + "]";
	}
	
	
	
	

}

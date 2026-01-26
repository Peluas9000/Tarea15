package dao;

public interface AlumnoDAO {
	
	void insertarAlumnos();
	
	void insertarGrupos();
	
	void mostrarTodosLosAlumnos();
	
	void guardarTodosLosAlumnosEnFichero();
	
	void leerAlumnosFicheroYGuardarBBDD();
	
	void modificarNombrAlumnoPorPkEnBBDD();
	
	void eliminarAlumnoPorPK();
	
	void eliminarAlumnoPorCurso();
	
	void guardarAlumnosJson_Xml();
	
	void leerFichero_JsonXmlGruposYGuardar_EnBBDD();
}

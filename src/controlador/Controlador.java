package controlador;

public class Controlador {
	
	
	public static void main(String[] args) {
		
		AlumnoDAO modelo = new DAOMysql();
		InterfazDAO vista = new DAOInterfaz() ;
		
		new Controlador().ejecutar(modelo,vista);
		
	}
	
	
	public void ejecutar(AlumnoDAO alumno,InterfazDAO interfaz) {
			
	}	

}

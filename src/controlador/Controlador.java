package controlador;

import dao.AlumnoDAO;
import dao.InterfazDAO;
import implementacion.DAOInterfaz;
import implementacion.DAOMysql;

public class Controlador {

	public static void main(String[] args) {

		AlumnoDAO modelo = new DAOMysql();
		InterfazDAO vista = new DAOInterfaz();

		new Controlador().ejecutar(modelo,vista);

	}

	public void ejecutar(AlumnoDAO alumno, InterfazDAO interfaz) {
		interfaz.menu(alumno);
	}

}

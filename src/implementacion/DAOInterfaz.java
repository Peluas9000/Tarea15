package implementacion;

import java.util.Scanner;

import dao.AlumnoDAO;
import dao.InterfazDAO;
import pojo.Alumno;

public class DAOInterfaz implements InterfazDAO {

	AlumnoDAO dao = new DAOMysql();

	@Override
	public void menu(AlumnoDAO a) {

		Scanner entrada=new Scanner(System.in);
		int num=0;
		do {
		System.out.println("1 Se puedan insertar alumnos.\r\n" 
							+ "2 Se puedan insertar grupos.\r\n"
				+ "3 Mostar todos los alumnos (en consola):\r\n" 
				+ "4 Guardar todos los alumnos en un fichero \r\n"
				+ "5 Leer alumnos de un fichero (con el formato anterior), y guardarlo"
				+ "en una BD: No cambia.\r\n" 
				+ "6 Modificar el nombre de un alumno guardado en la base de datos"
				+ "a partir de su Primary Key (PK): No cambia.\r\n"
				+ "7 Eliminar un alumno a partir de su (PK): No cambia.\r\n"
				+ "8 Eliminar los alumnos del curso indicado por el usuario (debes"
				+ "mostrarle previamente los cursos existentes).\r\n"
				+ "9 Guardar todos los grupos (con toda su información como "
				+ "atributos) en un fichero XML o JSON. Para cada grupo se "
				+ "guardará también el listado de alumnos de ese grupo. Los datos "
				+ "del alumno serán atributos en el XML.\r\n"
				+ "10 Leer un fichero XML o JSON de grupos (con en formato anterior)\r\n" + "y guardarlos en la BD.\r\n"
				+ "");
			 num=entrada.nextInt();
			 
			 switch(num) {
			 case 1:
				 dao.insertarAlumnos();
				 break;
			 case 2:
				 dao.insertarGrupos();
				 break;
			 case 3:
				 dao.mostrarTodosLosAlumnos();
				 break;
			 case 4:
				 dao.guardarTodosLosAlumnosEnFichero();
				 break;
			 case 5:
				 dao.leerAlumnosFicheroYGuardarBBDD();
				 break;
			 case 6:
				 dao.modificarNombrAlumnoPorPkEnBBDD();
				 break;
			 case 7:
				 dao.eliminarAlumnoPorPK();
				 break;
			 case 8:
				 dao.eliminarAlumnoPorCurso();
				 break;
			 case 9:
				 dao.guardarAlumnosJson_Xml();
				 break;
			 case 10:
				 dao.leerFichero_JsonXmlGruposYGuardar_EnBBDD();
				 break;
			 case 0:
				 System.out.println("Saliendo del sistema");
				 break;
			 default:
				System.out.println("Ningun numero incorrecto entre el 0-10");
				break;
			 }
		}while(num!=0);
	
	}
}

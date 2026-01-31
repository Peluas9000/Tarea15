package controlador;

import dao.AlumnoDao;
import dao.GrupoDao;
import implementacion.AlumnoDaoImpl;
import implementacion.GrupoDaoImpl;
import pojo.Alumno;
import pojo.Grupo;
import java.util.List;
import java.util.Scanner;

public class Controlador {

    public static void main(String[] args) {
        AlumnoDao alumnoDao = new AlumnoDaoImpl();
        GrupoDao grupoDao = new GrupoDaoImpl();
        
        Fichero conversorTxt = new ConversorTexto();
        Fichero conversorJson = new ConversorJson();

        new Controlador().ejecutar(alumnoDao, grupoDao, conversorTxt, conversorJson);
    }

    public void ejecutar(AlumnoDao aDao, GrupoDao gDao, Fichero fTxt, Fichero fJson) {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        do {
            System.out.println("\n1. Insertar Grupo\n2. Insertar Alumno\n3. Ver Alumnos\n4. Guardar Alumnos TXT");
            System.out.println("5. Cargar Alumnos TXT a BD\n6. Guardar Grupos JSON\n7. Cargar JSON a BD\n0. Salir");
            op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1:
                    System.out.println("ID y Nombre:");
                    gDao.insertar(new Grupo(sc.nextInt(), sc.next()));
                    break;
                case 2:
                    System.out.println("NIA, Nombre, ID Grupo:");
                    aDao.insertar(new Alumno(sc.nextInt(), sc.next(), sc.nextInt()));
                    break;
                case 3:
                    List<String> vista = aDao.mostrarTodosConGrupo();
                    vista.forEach(System.out::println);
                    break;
                case 4:
                    fTxt.guardar(aDao.mostrarTodosConGrupo(), "alumnos.txt");
                    break;
                case 5:
                    List<?> cargados = fTxt.cargar("importar_alumnos.txt"); 
                    for (Object o : cargados) aDao.insertar((Alumno) o);
                    break;
                case 6:
                    fJson.guardar(gDao.obtenerTodos(), "grupos.json");
                    break;
                case 7:
                    List<?> gruposJson = fJson.cargar("grupos.json");
                    for (Object o : gruposJson) gDao.insertar((Grupo) o);
                    break;
            }
        } while (op != 0);
    }
}
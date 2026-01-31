package dao;

import pojo.Alumno;
import java.util.List;

public interface AlumnoDao {
    boolean insertar(Alumno a);
    List<String> mostrarTodosConGrupo(); // Retorna Strings formateados
    boolean modificarNombre(int nia, String nuevoNombre);
    boolean eliminarPorPK(int nia);
    boolean eliminarPorCurso(String curso); // Nota: En el modelo simplificado no puse curso, pero lo mantengo por la interfaz
}
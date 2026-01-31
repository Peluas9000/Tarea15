package pojo;
import java.util.List;

public class Grupo {
    private int idGrupo; // PK
    private String nombre;
    private List<Alumno> listaAlumnos; // Para el JSON anidado

    public Grupo(int idGrupo, String nombre) {
        this.idGrupo = idGrupo;
        this.nombre = nombre;
    }
    public Grupo() {}

    // ... Getters y Setters ...
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int id) { this.idGrupo = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public List<Alumno> getListaAlumnos() { return listaAlumnos; }
    public void setListaAlumnos(List<Alumno> l) { this.listaAlumnos = l; }
}
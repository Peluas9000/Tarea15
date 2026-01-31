package pojo; // Recomendado usar paquete modelo, si no, quita esta linea
import java.time.LocalDate;

public class Alumno {
    private int nia; // PK
    private String nombre;
    private int idGrupo; // FK
    // Constructor vacío, lleno, getters y setters
    public Alumno(int nia, String nombre, int idGrupo) {
        this.nia = nia;
        this.nombre = nombre;
        this.idGrupo = idGrupo;
    }
    public Alumno() {}
    
    // ... Getters y Setters ...
    public int getNia() { return nia; }
    public void setNia(int nia) { this.nia = nia; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
}
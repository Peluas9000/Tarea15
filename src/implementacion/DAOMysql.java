package interfaz;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.AlumnoDAO;
import dao.MyDataSource;
import modelo.Alumno;
import modelo.Grupo;

public class DAOMysql implements AlumnoDAO {
    
    private Scanner sc = new Scanner(System.in);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Para JSON bonito

    // --- 1. INSERTAR GRUPOS ---
    @Override
    public void insertarGrupos() {
        System.out.println("--- INSERTAR GRUPO ---");
        System.out.print("ID Grupo: "); int id = sc.nextInt(); sc.nextLine();
        System.out.print("Nombre Grupo: "); String nom = sc.nextLine();
        System.out.print("Aula: "); String aula = sc.nextLine();

        String sql = "INSERT INTO grupos (id_grupo, nombre_grupo, aula) VALUES (?, ?, ?)";
        
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.setString(2, nom);
            pst.setString(3, aula);
            pst.executeUpdate();
            System.out.println("Grupo insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 2. INSERTAR ALUMNOS ---
    @Override
    public void insertarAlumnos() {
        System.out.println("--- INSERTAR ALUMNO ---");
        // Pedimos datos básicos (puedes ampliarlo)
        System.out.print("NIA: "); int nia = sc.nextInt(); sc.nextLine();
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("ID Grupo al que pertenece: "); int idGrupo = sc.nextInt();

        String sql = "INSERT INTO alumnos (nia, nombre, id_grupo) VALUES (?, ?, ?)";
        
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, nia);
            pst.setString(2, nombre);
            pst.setInt(3, idGrupo);
            pst.executeUpdate();
            System.out.println("Alumno insertado.");
        } catch (SQLException e) {
            System.err.println("Error: Quizás el ID de grupo no existe.");
            e.printStackTrace();
        }
    }

    // --- 3. MOSTRAR ALUMNOS (CON JOIN) ---
    @Override
    public void mostrarTodosLosAlumnos() {
        // Hacemos JOIN para ver el nombre del grupo, no solo el ID
        String sql = "SELECT a.nia, a.nombre, g.nombre_grupo FROM alumnos a LEFT JOIN grupos g ON a.id_grupo = g.id_grupo";
        
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            System.out.println("--- LISTADO DE ALUMNOS ---");
            while(rs.next()) {
                System.out.println("NIA: " + rs.getInt("nia") + 
                                   " | Nombre: " + rs.getString("nombre") + 
                                   " | Grupo: " + rs.getString("nombre_grupo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 4. GUARDAR ALUMNOS EN FICHERO DE TEXTO (NO XML/JSON) ---
    @Override
    public void guardarTodosLosAlumnosEnFichero() {
        String sql = "SELECT * FROM alumnos";
        try (Connection conn = MyDataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             BufferedWriter bw = new BufferedWriter(new FileWriter("alumnos_backup.txt"))) {

            while(rs.next()) {
                // Formato CSV: NIA;Nombre;IdGrupo
                String linea = rs.getInt("nia") + ";" + rs.getString("nombre") + ";" + rs.getInt("id_grupo");
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Fichero alumnos_backup.txt creado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- 5. LEER FICHERO TEXTO Y GUARDAR EN BD ---
    @Override
    public void leerAlumnosFicheroYGuardarBBDD() {
        String linea;
        String sql = "INSERT INTO alumnos (nia, nombre, id_grupo) VALUES (?, ?, ?)";
        
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos_backup.txt"));
             Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";"); // Separamos por punto y coma
                if (partes.length >= 3) {
                    pst.setInt(1, Integer.parseInt(partes[0]));
                    pst.setString(2, partes[1]);
                    pst.setInt(3, Integer.parseInt(partes[2]));
                    try {
                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("El alumno " + partes[0] + " ya existía o error de FK.");
                    }
                }
            }
            System.out.println("Importación finalizada.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- 6. MODIFICAR NOMBRE POR PK ---
    @Override
    public void modificarNombrAlumnoPorPkEnBBDD() {
        System.out.print("NIA del alumno a modificar: "); int nia = sc.nextInt(); sc.nextLine();
        System.out.print("Nuevo nombre: "); String nuevoNombre = sc.nextLine();
        
        String sql = "UPDATE alumnos SET nombre = ? WHERE nia = ?";
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nuevoNombre);
            pst.setInt(2, nia);
            int filas = pst.executeUpdate();
            System.out.println("Actualizados: " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 7. ELIMINAR POR PK ---
    @Override
    public void eliminarAlumnoPorPK() {
        System.out.print("NIA del alumno a eliminar: "); int nia = sc.nextInt();
        String sql = "DELETE FROM alumnos WHERE nia = ?";
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, nia);
            pst.executeUpdate();
            System.out.println("Alumno eliminado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 8. ELIMINAR ALUMNOS POR CURSO (Mostrando cursos antes) ---
    @Override
    public void eliminarAlumnoPorCurso() {
        // 1. Mostrar cursos disponibles (DISTINCT)
        System.out.println("Cursos disponibles:");
        try (Connection conn = MyDataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT DISTINCT curso FROM alumnos WHERE curso IS NOT NULL")) {
             while(rs.next()) System.out.println("- " + rs.getString("curso"));
        } catch (SQLException e) { e.printStackTrace(); }

        // 2. Pedir curso y eliminar
        System.out.print("Escribe el nombre del curso a borrar: "); 
        sc.nextLine(); // limpiar buffer
        String curso = sc.nextLine();
        
        String sqlDelete = "DELETE FROM alumnos WHERE curso = ?";
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pst = conn.prepareStatement(sqlDelete)) {
            pst.setString(1, curso);
            int borrados = pst.executeUpdate();
            System.out.println("Se han eliminado " + borrados + " alumnos del curso " + curso);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- 9. GUARDAR GRUPOS + ALUMNOS EN JSON ---
    @Override
    public void guardarAlumnosJson_Xml() {
        List<Grupo> listaGrupos = new ArrayList<>();
        String sqlGrupos = "SELECT * FROM grupos";
        
        try (Connection conn = MyDataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlGrupos)) {

            while(rs.next()) {
                Grupo g = new Grupo(rs.getInt("id_grupo"), rs.getString("nombre_grupo"), rs.getString("aula"));
                
                // --- SUB-CONSULTA: Para cada grupo, sacamos sus alumnos ---
                String sqlAlumnos = "SELECT * FROM alumnos WHERE id_grupo = ?";
                try (PreparedStatement pstAl = conn.prepareStatement(sqlAlumnos)) {
                    pstAl.setInt(1, g.getIdGrupo()); // Asumo getter getIdGrupo
                    ResultSet rsAl = pstAl.executeQuery();
                    List<Alumno> alumnosDelGrupo = new ArrayList<>();
                    while(rsAl.next()) {
                        // Crear objeto alumno con datos de la BD
                         Alumno a = new Alumno(); 
                         a.setNia(rsAl.getInt("nia"));
                         a.setNombre(rsAl.getString("nombre"));
                         // ... setear resto de campos
                         alumnosDelGrupo.add(a);
                    }
                    g.setListaAlumnos(alumnosDelGrupo);
                }
                listaGrupos.add(g);
            }
            
            // Escribir JSON
            try (FileWriter writer = new FileWriter("grupos_completo.json")) {
                gson.toJson(listaGrupos, writer);
                System.out.println("Fichero grupos_completo.json generado con éxito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- 10. LEER JSON DE GRUPOS Y GUARDAR EN BD ---
    @Override
    public void leerFichero_JsonXmlGruposYGuardar_EnBBDD() {
        try (FileReader reader = new FileReader("grupos_completo.json")) {
            // Convertir JSON a Lista de Objetos Java
            java.lang.reflect.Type tipoLista = new TypeToken<ArrayList<Grupo>>(){}.getType();
            List<Grupo> listaGrupos = gson.fromJson(reader, tipoLista);

            try (Connection conn = MyDataSource.getConnection()) {
                // Desactivar autocommit para hacer transacción
                conn.setAutoCommit(false);
                
                String sqlGrupo = "INSERT INTO grupos (id_grupo, nombre_grupo, aula) VALUES (?, ?, ?)";
                String sqlAlumno = "INSERT INTO alumnos (nia, nombre, id_grupo) VALUES (?, ?, ?)";

                try (PreparedStatement pstGrupo = conn.prepareStatement(sqlGrupo);
                     PreparedStatement pstAlumno = conn.prepareStatement(sqlAlumno)) {

                    for (Grupo g : listaGrupos) {
                        // 1. Insertar Grupo
                        pstGrupo.setInt(1, g.getIdGrupo()); // Asumo getter
                        pstGrupo.setString(2, g.getNombre());
                        pstGrupo.setString(3, g.getAula());
                        pstGrupo.executeUpdate();

                        // 2. Insertar Alumnos de ese Grupo
                        if (g.getListaAlumnos() != null) {
                            for (Alumno a : g.getListaAlumnos()) {
                                pstAlumno.setInt(1, a.getNia());
                                pstAlumno.setString(2, a.getNombre());
                                pstAlumno.setInt(3, g.getIdGrupo()); // FK
                                pstAlumno.executeUpdate();
                            }
                        }
                    }
                    conn.commit(); // Confirmar cambios
                    System.out.println("Datos importados del JSON correctamente.");
                } catch (SQLException ex) {
                    conn.rollback(); // Si falla, deshacer todo
                    ex.printStackTrace();
                } finally {
                    conn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
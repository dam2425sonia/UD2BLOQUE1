import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class App { 
    // URL de la base de datos, usuario y contraseña
    static final String DB_URL = "jdbc:mysql://localhost:3306/dam2";  // Reemplaza 'tu_basedatos'
    static final String USER = "root";  // Cambia según tu configuración
    static final String PASS = "Dam2425*";  // Cambia según tu configuración

    public static void main(String[] args) {
        // Conexión a la base de datos
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement()) {
            
            // 1.a Crear la tabla Empleado
            String createEmpleado = "CREATE TABLE IF NOT EXISTS Empleado (" +
                                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                    "nombre VARCHAR(20), " +
                                    "fecha_nacimiento DATE, " +
                                    "genero tinyint(1),"+
                                    "departamento_id INT);"; 
            stmt.executeUpdate(createEmpleado);
            System.out.println("Tabla Empleado creada o ya existe.");

            // 1.b Crear la tabla Departamento
            String createDepartamento = "CREATE TABLE IF NOT EXISTS Departamento (" +
                                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                        "nombre VARCHAR(20), " +
                                        "ubicacion VARCHAR(100));";
            stmt.executeUpdate(createDepartamento);
            System.out.println("Tabla Departamento creada o ya existe.");

            // 2.a. Crear la Foreign Key en la tabla Empleado
            String createForeignKey = "ALTER TABLE Empleado " +
                                      "ADD CONSTRAINT fk_departamento " +
                                      "FOREIGN KEY (departamento_id) " +
                                      "REFERENCES Departamento(id) " +
                                      "ON DELETE SET NULL " +
                                      "ON UPDATE CASCADE;";
            /* Restricciones adicionales: 
            Si deseas que la eliminación o actualización de un departamento afecte a los empleados asociados, 
            puedes agregar las cláusulas ON DELETE o ON UPDATE. 
            ON DELETE SET NULL: Si se elimina un departamento, el valor de departamento_id en los empleados relacionados se establece a NULL.
            ON UPDATE CASCADE: Si el valor de id en Departamento cambia, los registros relacionados en Empleado se actualizarán automáticamente.
            */

            stmt.executeUpdate(createForeignKey);
            System.out.println("Foreign Key creada entre Empleado y Departamento.");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

  

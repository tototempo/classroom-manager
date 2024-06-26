package Db.DAO;

import Db.DbConnect.DbConnect;
import Main.Docente;
import Main.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAOImpl implements PersonaDAO{
    private final DbConnect dbConnect;
    private static DocenteDAOImpl instance = null;

    private DocenteDAOImpl() {
        this.dbConnect = DbConnect.instance();
    }

    public static DocenteDAOImpl instance(){
        if(instance == null){
            instance = new DocenteDAOImpl();
        }
        return instance;
    }

    @Override
    public void insert(Persona persona) {
        Docente docente = (Docente) persona;

        String sql = "INSERT INTO docentes(nombre, apellido, dni, fecha_nacimiento, edad, cv) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, docente.getNombre());
            preparedStatement.setString(2, docente.getApellido());
            preparedStatement.setInt(3, docente.getDni());
            preparedStatement.setDate(4, Date.valueOf(docente.getFechaDeNacimiento()));
            preparedStatement.setInt(5, docente.getEdad());
            preparedStatement.setString(6, docente.getCv().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Persona persona){
        Docente docente = (Docente) persona;

        String sql = "DELETE FROM docentes es WHERE dni=?";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, docente.getDni());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id){
        Persona personaAEliminar = this.getById(id);
        this.delete(personaAEliminar);
    }

    @Override
    public Persona getById(int id) {
        String sql = "SELECT * FROM docentes WHERE id=?";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToDocente(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Persona> getAll() {
        List<Persona> docentes = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try (Connection connection = dbConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                docentes.add(mapResultSetToDocente(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    @Override
    public int countRows(){
        String sql = "SELECT COUNT(*) FROM docentes";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getLastPersonaId() {
        String sql = "SELECT MAX(id) FROM docentes";

        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean existsInDb(int dni){
        return getIdByDni(dni) != -1 ? true : false;
    }

    @Override
    public boolean existsInDbById(int id){
        return getById(id) != (null);
    }

    @Override
    public int getIdByDni(int dni){
        String sql = "SELECT * FROM docentes WHERE dni=?";
        try (Connection connection = dbConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, dni);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean hasDescuento(int dni){
        //Los docentes no tienen descuento
        return false;
    }

    @Override
    public Persona getByDni(int dni){
        return getById(getIdByDni(dni));
    }

    private Persona mapResultSetToDocente(ResultSet resultSet) throws SQLException {
        Docente docente = new Docente(
                resultSet.getString("nombre"),
                resultSet.getString("apellido"),
                resultSet.getInt("dni"),
                resultSet.getDate("fecha_nacimiento").toLocalDate(),
                resultSet.getInt("edad")
        );
        docente.setCvFromString(resultSet.getString("cv"));
        return docente;
    }

}

package com.dh.clinica.repository.impl;

import com.dh.clinica.repository.IDao;
import com.dh.clinica.model.Domicilio;
import com.dh.clinica.repository.configuration.ConfigurationJDBC;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DomicilioDaoH2 implements IDao<Domicilio> {

   private ConfigurationJDBC configurationJDBC;

    public DomicilioDaoH2(ConfigurationJDBC configuracionJDBC) {
        this.configurationJDBC = configuracionJDBC;
    }

    @Override
    public Domicilio guardar(Domicilio domicilio) {

        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = String.format("INSERT INTO domicilios(calle,numero,localidad,provincia) VALUES('%s','%s','%s','%s')", domicilio.getCalle(),
                domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                domicilio.setId(keys.getInt(1));
            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return domicilio;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = String.format("DELETE FROM domicilios where id = %s", id);
        execute(connection, query);


    }

    @Override
    public Domicilio buscar(Integer id) {
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = String.format("SELECT id,calle,numero,localidad,provincia FROM domicilios where id = '%s'", id);
        Domicilio domicilio = null;

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                domicilio = crearObjetoDomicilio(result);
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return domicilio;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = "SELECT *  FROM domicilios";
        List<Domicilio> domicilios = new ArrayList<>();

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {

                domicilios.add(crearObjetoDomicilio(result));

            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return domicilios;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        String query = String.format("UPDATE domicilios SET calle = '%s', numero = '%s',localidad = '%s',provincia = '%s'  WHERE id = '%s'",
                domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia(), domicilio.getId());
        execute(connection, query);
        return domicilio;
    }

    private Domicilio crearObjetoDomicilio(ResultSet result) throws SQLException {
        Integer id = result.getInt("id");
        String calle = result.getString("calle");
        String numero = result.getString("numero");
        String localidad = result.getString("localidad");
        String provincia = result.getString("provincia");
        return new Domicilio(id, calle, numero, localidad, provincia);

    }

    private void execute(Connection connection, String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


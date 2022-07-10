package com.dh.clinica.repository.impl;


import com.dh.clinica.model.Domicilio;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IDao;
import com.dh.clinica.model.Turno;
import com.dh.clinica.repository.configuration.ConfigurationJDBC;
import com.dh.clinica.util.Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TurnoDaoH2 implements IDao<Turno> {

    final static Logger log = Logger.getLogger(TurnoDaoH2.class);
    private ConfigurationJDBC configurationJDBC;
    private OdontologoDaoH2 odontologoDaoH2;
    private PacienteDaoH2 pacienteDaoH2;

    public TurnoDaoH2(OdontologoDaoH2 odontologoDaoH2, PacienteDaoH2 pacienteDaoH2, ConfigurationJDBC configurationJDBC) {
        this.configurationJDBC = configurationJDBC;
        this.pacienteDaoH2 = pacienteDaoH2;
        this.odontologoDaoH2 = odontologoDaoH2;
    }

    @Override
    public Turno guardar(Turno turno) {
        log.debug("guardando un nuevo Turno");
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;

        String query = String.format("INSERT INTO turnos(paciente_id,odontologo_id,fecha) VALUES('%s','%s','%s')", turno.getPaciente().getId(),turno.getOdontologo().getId(),
                Util.utilDateToSqlDate(turno.getDate()));

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next())
                turno.setId(keys.getInt(1));

            stmt.close();
            connection.close();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return turno;
    }


    @Override
    public Turno buscar(Integer id) {

        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = String.format("SELECT id,paciente_id,odontologo_id,fecha  FROM turnos where id = '%s'", id);
        Turno turno = null;

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                turno = crearObjetoTurno(result);
            }

            stmt.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

      return turno;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = String.format("DELETE FROM turnos where id = %s", id);
        execute(connection, query);
    }

    @Override
    public List<Turno> buscarTodos() {

        log.debug("Buscando todos los turnos");
        Connection connection = configurationJDBC.conectarConBaseDeDatos();
        Statement stmt = null;
        String query = "SELECT *  FROM turnos";
        List<Turno> turnos = new ArrayList<>();

        try{
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                turnos.add(crearObjetoTurno(result));
            }

            stmt.close();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }


        return turnos;
    }

     @Override
    public Turno actualizar(Turno turno) {
         log.debug("Actualizando un turno: " + turno.toString());
         Connection connection = configurationJDBC.conectarConBaseDeDatos();

         String query = String.format("UPDATE turnos SET fecha='%s' WHERE id = '%s'", Util.utilDateToSqlDate(turno.getDate()),turno.getId() );

         execute(connection, query);

        return turno;
     }

    private Turno crearObjetoTurno(ResultSet result) throws SQLException {

        int idTurno = result.getInt("id");
        int idPaciente = result.getInt("paciente_id");
        int idOdontologo = result.getInt("odontologo_id");
        Date fecha = result.getDate("fecha");

        Paciente paciente = pacienteDaoH2.buscar(idPaciente);
        Odontologo odontologo = odontologoDaoH2.buscar(idOdontologo);

        return new Turno(idTurno,paciente,odontologo,fecha);

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

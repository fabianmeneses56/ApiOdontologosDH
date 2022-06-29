package com.dh.clinica.repository.impl;


import com.dh.clinica.model.Domicilio;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IDao;
import com.dh.clinica.model.Turno;
import com.dh.clinica.util.Util;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {

    final static Logger log = Logger.getLogger(TurnoDaoH2.class);

    private final static String DB_JDBC_DRIVER = "org.h2.Driver";
    //con la instruccion INIT=RUNSCRIPT cuando se conecta a la base ejecuta el script de sql que esta en dicho archivo
    private final static String DB_URL = "jdbc:h2:~/db_clinica;INIT=RUNSCRIPT FROM 'create.sql'";
    private final static String DB_USER ="sa";
    private final static String DB_PASSWORD = "";

    private OdontologoDaoH2 odontologoDaoH2 = new OdontologoDaoH2();
    private PacienteDaoH2 pacienteDaoH2 = new PacienteDaoH2();
    private DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();

    @Override
    public Turno guardar(Turno turno) {
        log.debug("guardando un nuevo Turno");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
/*
            Domicilio domicilio = domicilioDaoH2.guardar(turno.getPaciente().getDomicilio());
            turno.getPaciente().getDomicilio().setId(domicilio.getId());

            Paciente paciente = pacienteDaoH2.guardar(turno.getPaciente());
            turno.getPaciente().setId(paciente.getId());

            Odontologo odontologo =  odontologoDaoH2.guardar(turno.getOdontologo());
            turno.getOdontologo().setId(odontologo.getId());
*/
            preparedStatement = connection.prepareStatement("INSERT INTO turnos(paciente_id,odontologo_id,fecha) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,turno.getPaciente().getId());
            preparedStatement.setInt(2,turno.getOdontologo().getId());
            preparedStatement.setDate(3, Util.utilDateToSqlDate(turno.getDate()));
            /*
            Paciente paciente = pacienteDaoH2.buscar(turno.getPaciente().getId());
            */
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            if(keys.next())
                turno.setId(keys.getInt(1));

            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }

        return turno;
    }

    @Override
    public Turno buscar(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Turno turno = null;

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT id,paciente_id,odontologo_id,fecha  FROM turnos where id = ?");
            preparedStatement.setInt(1,id);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                int idTurno = result.getInt("id");
                int idPaciente = result.getInt("paciente_id");
                int idOdontologo = result.getInt("odontologo_id");
                Date fecha = result.getDate("fecha");

                Paciente paciente = pacienteDaoH2.buscar(idPaciente);
                Odontologo odontologo = odontologoDaoH2.buscar(idOdontologo);

                 turno = new Turno(idTurno,paciente,odontologo,fecha);
            }

            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        return turno;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("DELETE FROM turnos where id = ?");
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Turno> buscarTodos() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Turno> turnos = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT *  FROM turnos");

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                int idTurno = result.getInt("id");
                int idPaciente = result.getInt("paciente_id");
                int idOdontologo = result.getInt("odontologo_id");
                Date fecha = result.getDate("fecha");

                Paciente paciente = pacienteDaoH2.buscar(idPaciente);
                Odontologo odontologo = odontologoDaoH2.buscar(idOdontologo);

                Turno turno = new Turno(idTurno,paciente,odontologo,fecha);
                turnos.add(turno);
            }

            preparedStatement.close();
        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }


        return turnos;
    }

     @Override
    public Turno actualizar(Turno turno) {

         Connection connection = null;
         PreparedStatement preparedStatement = null;

         try {
             Class.forName(DB_JDBC_DRIVER);
             connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             Domicilio domicilio = domicilioDaoH2.actualizar(turno.getPaciente().getDomicilio());
             Odontologo odontologo = odontologoDaoH2.actualizar(turno.getOdontologo());
             Paciente paciente = pacienteDaoH2.actualizar(turno.getPaciente());


             preparedStatement = connection.prepareStatement("UPDATE turnos SET paciente_id=?, odontologo_id=?, fecha=?  WHERE id = ?");

             preparedStatement.setInt(1,turno.getPaciente().getId());
             preparedStatement.setInt(2,turno.getOdontologo().getId());
             preparedStatement.setDate(3, Util.utilDateToSqlDate(turno.getDate()));
             preparedStatement.setInt(4,turno.getId());

             preparedStatement.executeUpdate();
             preparedStatement.close();

         }catch (SQLException | ClassNotFoundException throwables) {
             throwables.printStackTrace();
         }
        return turno;
    }

}

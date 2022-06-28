package com.dh.clinica.repository.impl;


import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.IDao;
import com.dh.clinica.model.Turno;
import org.apache.log4j.Logger;

import java.sql.*;
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

    @Override
    public Turno guardar(Turno turno) {
        log.debug("guardando un nuevo Turno");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            Odontologo odontologo =  odontologoDaoH2.guardar(turno.getOdontologo());
            Paciente paciente = pacienteDaoH2.guardar(turno.getPaciente());

            turno.getOdontologo().setId(odontologo.getId());
            turno.getPaciente().setId(paciente.getId());



            preparedStatement = connection.prepareStatement("INSERT INTO turnos(paciente_id,odontologo_id,fecha) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,turno.getPaciente());

        }catch (SQLException | ClassNotFoundException throwables){
            throwables.printStackTrace();
        }

        return turno;
    }

    @Override
    public Turno buscar(Integer id) {
        return null;
    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public List<Turno> buscarTodos() {
        return null;
    }

    @Override
    public Turno actualizar(Turno turno) {
        return null;
    }
}

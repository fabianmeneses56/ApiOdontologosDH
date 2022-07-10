package com.dh.clinica.model;

import java.util.Date;

public class Turno {

    private Integer id;
    private Integer id_paciente;
    private Integer id_odontologo;
    private Date date;

    public Turno() {

    }

    public Turno(Integer id, Integer id_paciente, Integer id_odontologo, Date date) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_odontologo = id_odontologo;
        this.date = date;
    }

    public Turno(Integer id_paciente, Integer id_odontologo, Date date) {
        this.id_paciente = id_paciente;
        this.id_odontologo = id_odontologo;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Integer id_paciente) {
        this.id_paciente = id_paciente;
    }

    public Integer getId_odontologo() {
        return id_odontologo;
    }

    public void setId_odontologo(Integer id_odontologo) {
        this.id_odontologo = id_odontologo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", id_paciente=" + id_paciente +
                ", id_odontologo=" + id_odontologo +
                ", date=" + date +
                '}';
    }
}

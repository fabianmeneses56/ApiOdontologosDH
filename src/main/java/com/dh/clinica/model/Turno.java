package com.dh.clinica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "turnos")

@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})

public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.MERGE)
    @JoinColumn(name = "paciente_id",referencedColumnName = "id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.MERGE)
    @JoinColumn(name = "odontologo_id",referencedColumnName = "id")
    private Odontologo odontologo;

    @Column
    private Date date;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
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
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", date=" + date +
                '}';
    }
}

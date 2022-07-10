package com.dh.clinica.service;


import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    PacienteRepository repository;

    public Paciente guardar(Paciente p) throws BadRequestException {

        if(p.getDomicilio().getId() == null)
            throw new BadRequestException("El paciente debe contener un domicilio");

        if(p.getDni() == null)
            throw new BadRequestException("El paciente debe contener un Dni");

        if(p.getNombre() == null)
            throw new BadRequestException("El paciente debe contener un Nombre");

        if(p.getApellido() == null)
            throw new BadRequestException("El paciente debe contener un Apellido");

        if(p.getFechaIngreso()== null)
            throw new BadRequestException("El paciente debe contener un fecha de ingreso");

        p.setFechaIngreso(new Date());
        return repository.save(p);
    }

    public Optional<Paciente> buscar(Integer id) {
        return repository.findById(id);
    }

    public List<Paciente> buscarTodos() {
        return repository.findAll();
    }

    public void eliminar(Integer id) { repository.deleteById(id); }

    public Paciente actualizar(Paciente p) throws BadRequestException {

        if(p.getDomicilio().getId() == null)
            throw new BadRequestException("El paciente debe contener un domicilio");

        if(p.getDni() == null)
            throw new BadRequestException("El paciente debe contener un Dni");

        if(p.getNombre() == null)
            throw new BadRequestException("El paciente debe contener un Nombre");

        if(p.getApellido() == null)
            throw new BadRequestException("El paciente debe contener un Apellido");

        if(p.getFechaIngreso()== null)
            throw new BadRequestException("El paciente debe contener un fecha de ingreso");

        return repository.save(p);
    }
}

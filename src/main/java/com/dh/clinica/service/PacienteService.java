package com.dh.clinica.service;


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

    public Paciente guardar(Paciente p) {
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

    public Paciente actualizar(Paciente p) {
        return repository.save(p);
    }
}

package com.dh.clinica.service;

import com.dh.clinica.model.Turno;
import com.dh.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  TurnoService {

    @Autowired
    TurnoRepository repository;

    public Turno registrarTurno(Turno turno){
        return  repository.save(turno);
    }
    public List<Turno> listar(){
        return repository.findAll();
    }
    public void eliminar(Integer id){
        repository.deleteById(id);
    }
    public Turno actualizar(Turno turno){
        return repository.save(turno);
    }
    public Optional<Turno> buscar(Integer id){
        return  repository.findById(id);
    }

}

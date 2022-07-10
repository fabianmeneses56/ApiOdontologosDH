package com.dh.clinica.service;


import com.dh.clinica.repository.DomicilioRepository;
import com.dh.clinica.model.Domicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService {

    @Autowired
    DomicilioRepository repository;

    public Domicilio guardar(Domicilio d){

        return repository.save(d);
    }
    public Optional<Domicilio> buscar(Integer id){
        return repository.findById(id);
    }
    public List<Domicilio> buscarTodos(){
        return repository.findAll();
    }
    public void eliminar(Integer id){
        repository.deleteById(id);
    }

}

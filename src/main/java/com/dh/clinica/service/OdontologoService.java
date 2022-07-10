package com.dh.clinica.service;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class OdontologoService {

    @Autowired
   OdontologoRepository repository;

    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return repository.save(odontologo);

    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    public Optional<Odontologo> buscar(Integer id) {
        return repository.findById(id);
    }

    public List<Odontologo> buscarTodos() {
        return repository.findAll();
    }

    public Odontologo actualizar(Odontologo odontologo) {
        return  repository.save(odontologo);
    }


}

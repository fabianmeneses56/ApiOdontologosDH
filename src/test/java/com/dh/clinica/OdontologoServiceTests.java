package com.dh.clinica;

import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.model.Odontologo;
import com.dh.clinica.service.OdontologoService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class OdontologoServiceTests {
    @Autowired
    private OdontologoService odontologoService;


    public void cargarDataSet() throws BadRequestException {
        this.odontologoService.registrarOdontologo(new Odontologo("Santiago", "Paz", 3455647));


    }

    @Test
    public void cargarNuevoOdontologo() throws BadRequestException {
        this.cargarDataSet();
        Odontologo odontologo = odontologoService.registrarOdontologo(new Odontologo("fabian","meneses",1234));
        Assert.assertTrue(odontologo.getId() != null);
    }

    @Test
    public void eliminarOdontologoTest()  {
        odontologoService.eliminar(1);
        Assert.assertTrue(odontologoService.buscar(1).isEmpty());

    }

    @Test
    public void traerTodos() {
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        Assert.assertTrue(!odontologos.isEmpty());
        Assert.assertTrue(odontologos.size() == 1);
        System.out.println(odontologos);
    }
}

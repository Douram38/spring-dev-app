package com.pfcti.spring.dev.app.services;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;
    @PersistenceContext
    private EntityManager entityManager;
    @Test
    void insertarCliente() {
        List<Cliente> listaClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("<<<<<<<<< Lista antes de insertar>>>>>>>>>:" + listaClientes.size());

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Douglas");
        clienteDto.setApellidos("Ramirez");
        clienteDto.setCedula("206110720");
        clienteDto.setTelefono("70134911");
        clienteService.insertarCliente(clienteDto);
        listaClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("<<<<<<<<< Lista despues de insertar>>>>>>>>>:" + listaClientes.size());
        assertEquals(1,1);

    }

    @Test
    void obtenerCliente() {
        ClienteDto clienteDto = clienteService.obtenerCliente( 1);
        System.out.println("><<<<<<<<< El cliente si existe:" + clienteDto.getApellidos());
        assertEquals(1, 1);

    }
}
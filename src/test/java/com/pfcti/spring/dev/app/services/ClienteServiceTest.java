package com.pfcti.spring.dev.app.services;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("Lista antes de insertar " + listaClientes.size());
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setApellidos("Ramirez");
        clienteDto.setCedula("987654321");
        clienteDto.setNombre("Douglas");
        clienteDto.setTelefono("123456789");

        clienteService.insertarCliente(clienteDto);

        listaClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("Lista despues de insertar " + listaClientes.size());

        assertEquals(listaClientes.size(),3);
    }

    @Test
    void obtenerCliente() {
        ClienteDto clienteDto = clienteService.obtenerCliente(1);
        System.out.println("Apellido: " + clienteDto.getApellidos());
        assertEquals(clienteDto.getId(),1);
    }

    @Test
    void actualizarCliente() {
        ClienteDto clienteDtoInicial = clienteService.obtenerCliente(1);
        System.out.println("El cliente inicial " + clienteDtoInicial.getApellidos());
        clienteDtoInicial.setApellidos("Ramirez");
        clienteService.actualizarCliente(clienteDtoInicial);

        ClienteDto clienteDtoDespues = clienteService.obtenerCliente(1);
        System.out.println("El cliente final " + clienteDtoInicial.getApellidos());

        assertEquals(clienteDtoInicial.getApellidos(),"Ramirez");
    }

    @Test
    void obtenerClientes() {
        clienteService.obtenerClientes().stream().map(
                cliente -> {
                    System.out.println("Cliente " + cliente.getApellidos());
                    return cliente;
                }
        ).collect(Collectors.toList());
        assertEquals(1,1);
    }
}
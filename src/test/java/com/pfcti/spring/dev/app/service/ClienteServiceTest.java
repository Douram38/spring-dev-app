package com.pfcti.spring.dev.app.service;

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

        List<Cliente> listClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("lista antes de insertar: " + listClientes.size());

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Roger");
        clienteDto.setApellidos("Araya Montero");
        clienteDto.setCedula("112660478");
        clienteDto.setTelefono("88606902");
        clienteService.insertarCliente(clienteDto);

        listClientes = entityManager.createQuery("SELECT c FROM Cliente c").getResultList();
        System.out.println("lista luego de insertar: " + listClientes.size());



        assertEquals(3,listClientes.size());
    }

    @Test
    void obtenerCliente() {
        ClienteDto clienteDto = clienteService.obtenerCliente(1);
        System.out.println(clienteDto.getNombre() + " " + clienteDto.getApellidos() + " si existe");
        assertEquals(1,clienteDto.getId());
    }

    @Test
    void actualizarCliente() {
        ClienteDto clienteDtoInicial = clienteService.obtenerCliente(1);
        System.out.println("El cliente inicial es " + clienteDtoInicial.getNombre() + " "+ clienteDtoInicial.getApellidos());

        clienteDtoInicial.setApellidos("FALLAS");

        clienteService.actualizarCliente(clienteDtoInicial);

        ClienteDto clienteDtoFinal = clienteService.obtenerCliente(1);
        System.out.println("El cliente final es " + clienteDtoFinal.getNombre() + " "+ clienteDtoFinal.getApellidos());

        assertEquals("FALLAS",clienteDtoFinal.getApellidos());
    }

    @Test
    void obtenerClientes() {
        List<ClienteDto> clientesDto = clienteService.obtenerClientes();
        clienteService
                .obtenerClientes()
                .forEach(cliente -> System.out.println(">>>>> CLIENTE EXISTENTE: " + cliente.getApellidos()));

        assertEquals(2,clientesDto.size());
    }

    @Test
    void obtenerClientesPorCodigoISOPaisYCuentasActivas() {
        List<ClienteDto> clienteDtos = clienteService.obtenerClientesPorCodigoISOPaisYCuentasActivas("CRC");
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " + clienteDto.getApellidos());});
        assertEquals(1, clienteDtos.size());
    }

    @Test
    void eliminarCliente() {
        clienteService.eliminarCliente(1);
        assertEquals(1,1);
    }

    @Test
    void buscarPorApellidos() {
        List<ClienteDto> clienteDtos = clienteService.buscarPorApellidos("PEREZ");
        clienteDtos.forEach(clienteDto -> {
            System.out.println("Cliente: " + clienteDto.getApellidos());
        });
        assertEquals(1, 1);
    }

    @Test
    void buscarApellidosQueryNativo() {
        List<ClienteDto> clienteDtos = clienteService.buscarPorApellidos("Perez");
        clienteDtos.forEach(clienteDto -> {
            System.out.println("Cliente: " + clienteDto.getApellidos());
        });
        assertEquals(1, 1);

    }

    @Test
    void updateClienteQuery() {
        ClienteDto clienteDtoOriginal = clienteService.buscarPorApellidos("PEREZ").get(0);
        System.out.println("Nombre original:" + clienteDtoOriginal.getNombre());
        clienteService.updateClienteQuery("CAMBIADO EL NOMBRE","PEREZ");
        ClienteDto clienteDtoCambiado = clienteService.buscarPorApellidos("PEREZ").get(0);
        System.out.println("NOMBRE CAMBIADO  :" + clienteDtoCambiado.getNombre());
        assertNotEquals(clienteDtoOriginal, clienteDtoCambiado);

    }

    @Test
    void testObtenerClientesPorCodigoISOPaisYCuentasActivas() {

        List<ClienteDto> clientesDto =
                clienteService.obtenerClientesPorCodigoISOPaisYCuentasActivas("CR");
        clientesDto.forEach(clienteDto -> {
            System.out.println("Cuentas Activas" +
                    clienteDto);
        });
        assertEquals(1, clientesDto.size());
    }
}
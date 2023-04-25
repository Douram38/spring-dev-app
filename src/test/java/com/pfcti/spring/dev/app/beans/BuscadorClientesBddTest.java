package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.spring.dev.app.dto.enums.ClienteQueryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuscadorClientesBddTest {

    @Autowired
    private BuscadorClientes baseDeDatos;

    @Autowired
    @Qualifier("baseDeDatos")
    private BuscadorClientes buscadorClientesBaseDatos;

    @Autowired
    @Qualifier("sistemaExterno")
    private BuscadorClientes buscadorClientes;

    @Test
    void obtenerListaClientes() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);
        List<ClienteDto> clienteDtos = baseDeDatos.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " +clienteDto.getApellidos());});
        assertTrue(clienteDtos.size() == 1);
    }

    @Test
    void obtenerListaClientesBDDConQualifier() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);
        List<ClienteDto> clienteDtos = buscadorClientesBaseDatos.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " +clienteDto.getApellidos());});
        assertTrue(clienteDtos.size() == 1);
    }

    @Test
    void obtenerListaClientesSistemaExterno() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType(ClienteQueryType.NOMBRES);
        List<ClienteDto> clienteDtos = buscadorClientes.obtenerListaClientes(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> {System.out.println("Cliente: " +clienteDto.getApellidos());});
        assertTrue(clienteDtos.size() == 0);
    }
}
package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.spring.dev.app.dto.enums.ClienteQueryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdministradorClientesAsBeanTest {

    @Autowired
    private AdministradorClientes defaultNombres;

    @Autowired
    @Qualifier("defaultNombres")
    private AdministradorClientes administradorClientes;


    @Test
    void obtenerListaClientePorCriterio() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();

        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType((ClienteQueryType.NOMBRES));


        List<ClienteDto> clienteDtos = defaultNombres.obtenerListaClientePorCriterio(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> {
            System.out.println("Cliente: " + clienteDto.getApellidos());
        });
        assertTrue(clienteDtos.size() == 1);
    }

    @Test
    void obtenerListaClientePorCriterioConAnotacion() {
        ClienteQueryDto clienteQueryDto = new ClienteQueryDto();
        clienteQueryDto.setTextoBusqueda("ROBERTO");
        clienteQueryDto.setClienteQueryType((ClienteQueryType.NOMBRES));

        List<ClienteDto> clienteDtos = defaultNombres.obtenerListaClientePorCriterio(clienteQueryDto);
        clienteDtos.forEach(clienteDto -> {
            System.out.println("Cliente: " + clienteDto.getApellidos());
        });
        assertTrue(clienteDtos.size() == 1);
    }
}

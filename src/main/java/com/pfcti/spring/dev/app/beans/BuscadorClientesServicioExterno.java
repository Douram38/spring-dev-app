package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sistemaExterno2")
public class BuscadorClientesServicioExterno implements  BuscadorClientes{
    @Override
    public List<ClienteDto> obtenerListaClientes(ClienteQueryDto clienteQueryDto) {
        return new ArrayList<>();
    }
}

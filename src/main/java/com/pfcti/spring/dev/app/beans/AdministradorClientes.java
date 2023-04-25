
package com.pfcti.spring.dev.app.beans;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.ClienteQueryDto;
import com.pfcti.spring.dev.app.dto.enums.ClienteQueryType;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AdministradorClientes {
    private ClienteRepository clienteRepository;
    private ClienteQueryType defaultClienteQuerytype;

    public AdministradorClientes(ClienteRepository clienteRepository, ClienteQueryType defaultClienteQuerytype) {
        System.out.println("<<<<<<<< AdministradorClientes INSTANCIA: " + this);
        this.clienteRepository = clienteRepository;
        this.defaultClienteQuerytype = defaultClienteQuerytype;
    }


    public List<ClienteDto> obtenerListaClientePorCriterio(ClienteQueryDto clienteQueryDto) {
        List<Cliente> clientes = null;
        if (ClienteQueryType.CEDULA.equals(clienteQueryDto.getClienteQueryType())) {
            clientes = clienteRepository.findClientesByCedula(clienteQueryDto.getTextoBusqueda());

        } else if (ClienteQueryType.NOMBRES.equals(clienteQueryDto.getClienteQueryType())) {
            clientes = clienteRepository.findClientesByNombreContainingIgnoreCaseOrNombreContainingIgnoreCase(clienteQueryDto.getTextoBusqueda(), clienteQueryDto.getTextoBusqueda());
        }

        return clientes
                .stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());
    }

    private ClienteDto fromClienteToClienteDto(Cliente cliente) {
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);

        return clienteDto;
    }

}

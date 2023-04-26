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
    private ClienteQueryType defaultClienteQueryType;

//    public AdministradorClientes(ClienteRepository clienteRepository) {
//        this.clienteRepository = clienteRepository;
//    }

    public AdministradorClientes(ClienteRepository clienteRepository, ClienteQueryType defaultClienteQueryType) {
        System.out.println(">>>>> Administrador Clientes INSTANCIA " + this);
        this.clienteRepository = clienteRepository;
        this.defaultClienteQueryType = defaultClienteQueryType;
    }

    public List<ClienteDto> obtenerListaClientePorCriterio(ClienteQueryDto clienteQueryDto){
        List<Cliente> clientes = null;
        if(ClienteQueryType.CEDULA.equals(clienteQueryDto.getClienteQueryType())){
            clientes = clienteRepository.findClientesByCedula(clienteQueryDto.getTextoBusqueda());

        }else if (ClienteQueryType.NOMBRES.equals(clienteQueryDto.getClienteQueryType())){
            clientes = clienteRepository.findClientesByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(clienteQueryDto.getTextoBusqueda(),clienteQueryDto.getTextoBusqueda());
        }

            return  clientes
                    .stream()
                    .map(this::fromClienteToClienteDto)
                    .collect(Collectors.toList());
    }

    private ClienteDto fromClienteToClienteDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);
        /*clienteDto.setId(cliente.getId());*/
        /*clienteDto.setNombre(cliente.getNombre());*/
        /*clienteDto.setApellidos(cliente.getApellidos());*/
        /*clienteDto.setCedula(cliente.getCedula());*/
        /*clienteDto.setTelefono(cliente.getTelefono());*/

        return clienteDto;
    }

}

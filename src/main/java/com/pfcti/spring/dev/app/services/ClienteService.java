package com.pfcti.spring.dev.app.services;


import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;



    public void insertarCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());
        clienteRepository.save(cliente);
    }

    public ClienteDto obtenerCliente(int clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                            throw new RuntimeException("Cliente no existe");
                        }
                );

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTelefono(cliente.getTelefono());
        clienteDto.setId(cliente.getId());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setTelefono(cliente.getTelefono());

        return clienteDto;
    }

    public void actualizarCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setCedula(clienteDto.getCedula());
        cliente.setId(clienteDto.getId());
        cliente.setTelefono(cliente.getTelefono());
        cliente.setApellidos(cliente.getApellidos());
        cliente.setNombre(cliente.getNombre());

        clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

}

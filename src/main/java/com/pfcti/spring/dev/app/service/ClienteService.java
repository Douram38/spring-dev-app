package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import com.pfcti.spring.dev.app.repository.DireccionRepository;
import com.pfcti.spring.dev.app.repository.InversionRepository;
import com.pfcti.spring.dev.app.repository.TarjetaRepository;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ClienteService {
    private ClienteRepository clienteRepository;
    private CuentaRepository cuentaRepository;
    private DireccionRepository direccionRepository;
    private InversionRepository inversionRepository;
    private TarjetaRepository tarjetaRepository;


    public void insertarCliente(ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());

        clienteRepository.save(cliente);
    }

    // public ClienteDto buscarClientePorId(int id){
    //     clienteRepository.findById(id);
    // }

    public ClienteDto obtenerCliente(int clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->{
                    throw new RuntimeException("Cliente No existe");
                });

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(cliente.getId());
        clienteDto.setNombre(cliente.getNombre());
        clienteDto.setApellidos(cliente.getApellidos());
        clienteDto.setCedula(cliente.getCedula());
        clienteDto.setTelefono(cliente.getTelefono());

        return clienteDto;
    }

    public void actualizarCliente(ClienteDto clienteDto){

        Cliente cliente = clienteRepository.findById(clienteDto.getId())
                .orElseThrow(() ->{
                    throw new RuntimeException("Cliente No existe");
                });

        cliente.setId(clienteDto.getId());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(clienteDto.getTelefono());

        clienteRepository.save(cliente);
    }

    private ClienteDto fromClienteToClienteDto(Cliente cliente){
        ClienteDto clienteDto = new ClienteDto();
        BeanUtils.copyProperties(cliente, clienteDto);

        return clienteDto;
    }

    public List<ClienteDto> obtenerClientes(){
        List<ClienteDto>  clienteReturn = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach( cliente ->{
            clienteReturn.add(fromClienteToClienteDto(cliente));
                }
        );
        return clienteReturn;
    }

    /*public List<ClienteDto> obtenerClientesPorCodigoISOPaisYCuentasActivas(String codigoISO){
        List<Cliente> clientes = clienteRepository.findClientesByPaisAndCuentas_ActivaIsTrue(codigoISO);
        List<ClienteDto> clientesDto = new ArrayList<>();

        clientes.forEach(cliente ->{
            clientesDto.add(fromClienteToClienteDto(cliente));
        });

        return clientesDto;
    }*/

    public void eliminarCliente(Integer clienteId){
        direccionRepository.deleteAllByCliente_Id(clienteId);
        cuentaRepository.deleteAllByCliente_Id(clienteId);
        inversionRepository.deleteAllByCliente_Id(clienteId);
        tarjetaRepository.deleteAllByCliente_Id(clienteId);
        clienteRepository.deleteById(clienteId);
    }

    public List<ClienteDto> buscarPorApellidos(String apellidos){
        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.buscarPorApellidos(apellidos);
        clientes.forEach(cliente -> clienteDtos.add(fromClienteToClienteDto(cliente)));
        return clienteDtos;

    }

    public List<ClienteDto> buscarApellidosQueryNativo(String apellidos){

        List<ClienteDto> clienteDtos = new ArrayList<>();
        List<Tuple> tuples = clienteRepository.buscarPorApellidosQueryNativo(apellidos);
        tuples.forEach(tuple -> {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setNombre((String) tuple.get("apellidos"));
            clienteDto.setNombre((String) tuple.get("nombre"));
            clienteDto.setCedula((String) tuple.get("cedula"));
            clienteDto.setTelefono((String) tuple.get("telefono"));
            clienteDto.setId((Integer) tuple.get("id"));
        });
        return  clienteDtos;
    }

    public void updateClienteQuery(String nombre, String apellidos){

        clienteRepository.updateClienteQuery(nombre,apellidos);

    }


    public List<ClienteDto> obtenerClientesPorCodigoISOPaisYCuentasActivas(String codigoISOPais){
        List<ClienteDto> resultadoClientesDto = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findClientesByPaisNacimientoAndCuentas_EstadoIsTrue(codigoISOPais);
        ClienteDto clienteDto = new ClienteDto();
        clientes.forEach(cliente -> {
            clienteDto.setId(cliente.getId());
            clienteDto.setApellidos(cliente.getApellidos());
            clienteDto.setNombre(cliente.getNombre());
            clienteDto.setCedula(cliente.getCedula());
            clienteDto.setPais(cliente.getPais());
            resultadoClientesDto.add(clienteDto);
        });
        return resultadoClientesDto;
    }

    public List<ClienteDto> obtenerClientesExtranjerosYTarjetasInactivas(String codigoISO){
        return clienteRepository.findClientesByPaisNotAndTarjetas_ActivaIsFalse(codigoISO)
                .stream()
                .map(this::fromClienteToClienteDto)
                .collect(Collectors.toList());


    }




}

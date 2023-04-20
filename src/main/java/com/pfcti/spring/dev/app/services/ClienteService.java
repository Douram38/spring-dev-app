package com.pfcti.spring.dev.app.services;


import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;



    public void insertarCliente(ClienteDto clienteDto) {

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellidos(clienteDto.getApellidos());
        cliente.setCedula(clienteDto.getCedula());
        cliente.setTelefono(cliente.getTelefono());
        clienteRepository.save(cliente);
    }

   public ClienteDto obtenerCliente(int clienID){

       Cliente cliente= clienteRepository.findById(clienID).orElseThrow(
               () ->
               {
                   throw new RuntimeException("Cliente no existe");

               }
       );
       ClienteDto clienteDto = new ClienteDto();
       clienteDto.setId(cliente.getId());
       clienteDto.setNombre(cliente.getNombre());
       clienteDto.setApellidos(cliente.getApellidos());
       clienteDto.setCedula(cliente.getCedula());
       cliente.setTelefono(cliente.getTelefono());
        return clienteDto;


   }

   public void  actualizarCliente(ClienteDto clientedto){
         ClienteDto clienteDtoInicial= clienteService.obtenerCliente(clientedto.getId());
            Cliente cliente = new Cliente();
            cliente.setNombre(clienteDtoInicial.getNombre());
            cliente.setApellidos(clienteDtoInicial.getApellidos());
            cliente.setCedula(clienteDtoInicial.getCedula());
            cliente.setTelefono(clienteDtoInicial.getTelefono());
            clienteRepository.save(cliente);

            System.out.println("El cliente fue actualizado de forma exitosa");

   }

}

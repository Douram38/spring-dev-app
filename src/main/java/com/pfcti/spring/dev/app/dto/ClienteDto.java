package com.pfcti.spring.dev.app.dto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.repository.ClienteRepository;
import com.pfcti.spring.dev.app.services.ClienteService;
import lombok.Data;
import java.util.List;

@Data
public class ClienteDto {

    private int id;
    private String nombre;
    private String apellidos;
    private String cedula;
    private String Telefono;

    private List<DireccionDto> direcciones;


    //private ClienteRepository clienteRepository(ClienteDto clienteDto){

       // ClienteService cliente = new ClienteService();
       // cliente.insertarCliente(clienteDto);


  //  }
}

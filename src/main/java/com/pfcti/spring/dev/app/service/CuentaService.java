package com.pfcti.spring.dev.app.service;


import com.pfcti.spring.dev.app.dto.NotificationDto;
import com.pfcti.spring.dev.app.jms.publishers.NotificationPubSubSender;
import com.pfcti.spring.dev.app.jms.senders.NotificationSender;
import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CuentaService {
    private CuentaRepository cuentaRepository;
    private CuentaSpecification cuentaSpecification;
    private NotificationSender notificationSender;
    private ClienteService clienteService;
    private NotificationPubSubSender notificationPubSubSender;


    private CuentaDto fromCuentaToCuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }
    public List<CuentaDto> buscarDinamicamentePorCriterios(CuentaDto cuentaDtoFilter){
        return cuentaRepository
                .findAll(cuentaSpecification.buildFilter(cuentaDtoFilter))
                .stream()
                .map(this::fromCuentaToCuentaDto)
                .collect(Collectors.toList());
    }


    public List<CuentaDto> obtenerCuentas(){
        List<CuentaDto>  cuentaReturn = new ArrayList<>();
        List<Cuenta> cuentas = cuentaRepository.findAll();
        cuentas.forEach( cliente ->{
                cuentaReturn.add(fromCuentaToCuentaDto(cliente));
                }
        );
        return cuentaReturn;
    }

    public void updateCuentaEstadoByNumeroQuery(String numero){

        cuentaRepository.updateCuentaEstadoByNumeroQuery(numero, false);
    }


    private CuentaDto fromCuentaToCcuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);

        return cuentaDto;
    }


    public List<CuentaDto>findCuentaByCliente_Id (int id){
        List<CuentaDto> cuentaDtos = new ArrayList<>();
        List<Cuenta> cuentas =  cuentaRepository.findCuentaByCliente_Id(id);
        cuentas.forEach(cuenta -> {
            cuentaDtos.add(fromCuentaToCuentaDto(cuenta));
        });
        return cuentaDtos;
    }

    public void insertarCuenta(CuentaDto cuentaDto) {
        Cliente cliente= new Cliente();
        cliente.setId(cuentaDto.getClienteId());
        Cuenta cuenta = new Cuenta();
        //cuenta.setId(cuentaDto.getId());
        cuenta.setTipo(cuentaDto.getTipo());
        cuenta.setNumero(cuentaDto.getNumero());
        cuenta.setCliente(cliente);
        cuenta.setActiva(cuentaDto.getActiva());

        cuentaRepository.save(cuenta);
    }


    public void sendNotification(CuentaDto cuentaDto) {
        ClienteDto clienteDto = clienteService.obtenerCliente(cuentaDto.getClienteId());
        NotificationDto notificacionDto = new NotificationDto();
        notificacionDto.setPhoneNumber(clienteDto.getTelefono());
        notificacionDto.setMailBody(getMailBody(cuentaDto, clienteDto));
        this.notificationSender.sendMail(notificacionDto);
    }

    private static String getMailBody(CuentaDto cuentaDto, ClienteDto clienteDto) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Estimado ");
        bodyBuilder.append(clienteDto.getNombre());
        bodyBuilder.append(" ");
        bodyBuilder.append(clienteDto.getApellidos());
        bodyBuilder.append(" tu cuenta número ");
        bodyBuilder.append(cuentaDto.getNumero());
        bodyBuilder.append(" se ha creado con éxito.");
        return bodyBuilder.toString();

    }

    public void creacionDeCuentaYNotificacion(CuentaDto cuentaDto) {
        insertarCuenta(cuentaDto);
        sendNotification(cuentaDto);
    }

    public void sendCreateAccountNotification(CuentaDto cuentaDto) {
        log.info("Empezando envío de notificaciones");
        Message<CuentaDto> message = MessageBuilder.withPayload(cuentaDto).build();
        Message<String> result = notificationPubSubSender.sendNotification(message);
        log.info("Resultado envío notificación: {}", result.getPayload());
    }

    public void creacionDeCuentaPublishSub(CuentaDto cuentaDto) {
        insertarCuenta(cuentaDto);
        sendCreateAccountNotification(cuentaDto);
    }


}
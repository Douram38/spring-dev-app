package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.criteria.ClienteSpecification;
import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.model.Cliente;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CuentaService {
    private CuentaRepository cuentaRepository;

    private CuentaSpecification cuentaSpecification;

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


   public List<CuentaDto> buscarCuentasPorIdCliente(String ClientId){
       List<CuentaDto>  cuentaReturn = new ArrayList<>();
       List<Cuenta> cuentas = cuentaRepository.buscarCuentasPorIdCliente(ClientId);
       cuentas.forEach( cuenta ->{
           cuentaReturn.add(fromCuentaToCcuentaDto(cuenta));
               }
       );
       return cuentaReturn;


   }

    private CuentaDto fromCuentaToCcuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);

        return cuentaDto;
    }



}
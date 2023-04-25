package com.pfcti.spring.dev.app.service;

import com.pfcti.spring.dev.app.criteria.ClienteSpecification;
import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.repository.CuentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
  /*  public List<CuentaDto> buscarDinamicamentePorCriterios(CuentaDto cuentaDtoFilter){
        return cuentaRepository
                .findAll(cuentaSpecification.buildFilter(cuentaDtoFilter))
                
                .stream()
                .map(this::fromCuentaToCuentaDto)
                .collect(Collectors.toList());
    }*/


}
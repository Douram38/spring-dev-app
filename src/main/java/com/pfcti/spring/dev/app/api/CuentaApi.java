package com.pfcti.spring.dev.app.api;


import com.pfcti.spring.dev.app.criteria.CuentaSpecification;
import com.pfcti.spring.dev.app.dto.ClienteDto;
import com.pfcti.spring.dev.app.dto.CuentaDto;
import com.pfcti.spring.dev.app.model.Cuenta;
import com.pfcti.spring.dev.app.service.CuentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/cuenta")
public class CuentaApi {

    @Autowired
    private CuentaService cuentaService;

    private CuentaSpecification cuentaSpecification;

    private CuentaDto fromCuentaToCuentaDto(Cuenta cuenta){
        CuentaDto cuentaDto = new CuentaDto();
        BeanUtils.copyProperties(cuenta, cuentaDto);
        return cuentaDto;
    }

    @GetMapping(value = "/all")
    public List<CuentaDto> buscarTodasCuentas(){
        return cuentaService.obtenerCuentas(); }

    @GetMapping(value = "/{idCliente}")
    public List<CuentaDto> buscarTodasCuentasDeCliente(String idCliente){
        return cuentaService.buscarCuentasPorIdCliente(idCliente); }



}

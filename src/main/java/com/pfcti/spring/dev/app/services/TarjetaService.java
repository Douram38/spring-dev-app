package com.pfcti.spring.dev.app.services;


import com.pfcti.spring.dev.app.repository.TarjetaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TarjetaService {

    private TarjetaRepository tarjetaRepository;
}

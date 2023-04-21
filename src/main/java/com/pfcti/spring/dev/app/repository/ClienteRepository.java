package com.pfcti.spring.dev.app.repository;

import com.pfcti.spring.dev.app.model.Cliente;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    List<Cliente> findClientesByPaisAndCuentas_ActivaIsTrue(String pais);

    @Query(value = "select c from Cliente c where c.apellidos =:apellidos")
    List<Cliente> buscarPorApellidos(String apellidos);


    @Query(value = "select nombre, apellidos, cedula, telefono, id from TCLIENTE where apellidos =:apellidos", nativeQuery = true)
    List<Tuple> buscarPorApellidosQueryNativo(String apellidos);

    List<Cliente> findClienteByApellidos(String apellidos);

}

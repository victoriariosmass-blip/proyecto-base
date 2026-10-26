package com.verdegreen.repository;

import com.verdegreen.model.ParametroSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametroRepository extends JpaRepository<ParametroSistema, String> {

    Optional<ParametroSistema> findByClave(String clave);
}

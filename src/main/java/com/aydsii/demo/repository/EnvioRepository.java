package com.verdegreen.repository;

import com.verdegreen.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** save(envio) (CU-01) y findById(id) (CU-02) se heredan de JpaRepository. */
@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
}

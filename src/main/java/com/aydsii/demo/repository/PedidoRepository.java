package com.aydsii.demo.repository;

import com.aydsii.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** save(pedido) se hereda de JpaRepository. */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

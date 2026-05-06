package com.smartlogix.mspedidos.repository;

import com.smartlogix.mspedidos.model.EstadoPedido;
import com.smartlogix.mspedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);
    List<Pedido> findByEstado(EstadoPedido estado);
}
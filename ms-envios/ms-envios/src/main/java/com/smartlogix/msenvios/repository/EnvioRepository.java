package com.smartlogix.msenvios.repository;

import com.smartlogix.msenvios.model.Envio;
import com.smartlogix.msenvios.model.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByPedidoId(Long pedidoId);
    List<Envio> findByEstado(EstadoEnvio estado);
    List<Envio> findByTransportista(String transportista);
}
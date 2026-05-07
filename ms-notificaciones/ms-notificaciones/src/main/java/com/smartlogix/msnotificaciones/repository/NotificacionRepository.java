package com.smartlogix.msnotificaciones.repository;

import com.smartlogix.msnotificaciones.model.NotificacionRegistro;
import com.smartlogix.msnotificaciones.model.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionRegistro, Long> {
    List<NotificacionRegistro> findByPedidoId(Long pedidoId);
    List<NotificacionRegistro> findByTipo(TipoNotificacion tipo);
    List<NotificacionRegistro> findByEnviado(Boolean enviado);
}
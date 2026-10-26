package com.aydsii.demo.service;

import com.aydsii.demo.model.Pedido;
import com.aydsii.demo.model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Notificaciones del CU Comprar.
 * Las notificaciones push quedan fuera del alcance: se registran en el log de la aplicación.
 */
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final String telefonoEmpresa;

    public NotificacionService(@Value("${verdegreen.whatsapp.telefono}") String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    /** Aviso a Ventas: nuevo pedido con comprobante (estado ACEPTADO). */
    public void notificarNuevoPedido(Pedido pedido) {
        log.info("[NOTIFICACION -> VENTAS] Nuevo pedido #{} con comprobante '{}' (total ${})",
                pedido.getId(), pedido.getComprobante(), pedido.getTotal());
    }

    /** Aviso a Producción: el producto quedó debajo de su stock mínimo. */
    public void notificarStockMinimo(Producto producto) {
        log.warn("[NOTIFICACION -> PRODUCCION] Stock mínimo: '{}' ({}) quedó con {} unidades",
                producto.getNombre(), producto.getCodigo(), producto.getStock());
    }

    /** Enlace wa.me con el mensaje que corresponde según el pedido sea personalizado o no. */
    public String generarEnlaceWhatsApp(Pedido pedido) {
        String mensaje = pedido.isPersonalizado()
                ? "Pedido " + pedido.getId() + " enviado, comuníquese por WhatsApp"
                : "Pedido " + pedido.getId() + " enviado, aguarde acreditación del pago";
        return "https://wa.me/" + telefonoEmpresa + "?text=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
    }
}

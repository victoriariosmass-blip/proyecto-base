package com.aydsii.demo.service;

import com.aydsii.demo.dto.CompraRequestDTO;
import com.aydsii.demo.dto.PedidoResponseDTO;
import com.aydsii.demo.exception.RecursoNoEncontradoException;
import com.aydsii.demo.exception.ReglaNegocioException;
import com.aydsii.demo.model.*;
import com.aydsii.demo.repository.CarritoRepository;
import com.aydsii.demo.repository.EnvioRepository;
import com.aydsii.demo.repository.ParametroRepository;
import com.aydsii.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CU-01 Comprar.
 * Sigue el mismo orden de mensajes que el diagrama de secuencia.
 * El límite por categoría y el stock NO se validan acá: se controlan al agregar
 * productos al carrito (RN-02), así que el carrito ya llega con cantidades válidas.
 */
@Service
public class PedidoService {

    private static final String HORAS_VENCIMIENTO = "HORAS_VENCIMIENTO";
    private static final String COSTO_ENVIO = "COSTO_ENVIO";
    private static final String COSTO_PERSONALIZACION = "COSTO_PERSONALIZACION";

    private final CarritoRepository carritoRepository;
    private final PedidoRepository pedidoRepository;
    private final ParametroRepository parametroRepository;
    private final EnvioRepository envioRepository;
    private final NotificacionService notificacionService;

    public PedidoService(CarritoRepository carritoRepository,
                         PedidoRepository pedidoRepository,
                         ParametroRepository parametroRepository,
                         EnvioRepository envioRepository,
                         NotificacionService notificacionService) {
        this.carritoRepository = carritoRepository;
        this.pedidoRepository = pedidoRepository;
        this.parametroRepository = parametroRepository;
        this.envioRepository = envioRepository;
        this.notificacionService = notificacionService;
    }

    /** Todo o nada: si algo falla, no queda stock reservado ni pedido guardado. */
    @Transactional
    public PedidoResponseDTO crearPedido(CompraRequestDTO dto) {
        // 1.1.1 a 1.1.4: carrito del cliente (precondición: activo y con al menos un producto)
        Carrito carrito = carritoRepository.findByClienteId(dto.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "El cliente " + dto.getClienteId() + " no tiene un carrito activo"));
        if (carrito.estaVacio()) {
            throw new ReglaNegocioException("El carrito está vacío");
        }
        Cliente cliente = carrito.getCliente();

        // 1.1.5 y 1.1.6: plazo de vencimiento parametrizado (RN-04)
        int horas = buscarParametro(HORAS_VENCIMIENTO).getValorEntero();
        LocalDateTime vencimiento = LocalDateTime.now().plusHours(horas);

        // 1.1.7: «create» Pedido
        Pedido pedido = new Pedido(cliente, dto.getComprobante(), dto.isPersonalizado(),
                dto.getTipoEntrega(), vencimiento);

        // 1.1.8 a 1.1.13: loop sobre los ítems: arma el detalle y reserva stock
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            pedido.agregarDetalle(producto, cantidad);
            producto.reservarStock(cantidad);
            if (producto.bajoStockMinimo()) {
                notificacionService.notificarStockMinimo(producto);
            }
        }

        // 1.1.14 a 1.1.20: costos de entrega y personalización (paso 4 del CU)
        boolean aDomicilio = pedido.esADomicilio();
        BigDecimal costoEnvio = BigDecimal.ZERO;
        if (aDomicilio) {
            costoEnvio = buscarParametro(COSTO_ENVIO).getValorDecimal();
        }
        BigDecimal costoPersonalizacion = BigDecimal.ZERO;
        if (pedido.isPersonalizado()) {
            costoPersonalizacion = buscarParametro(COSTO_PERSONALIZACION).getValorDecimal();
        }
        pedido.aplicarCostos(costoEnvio, costoPersonalizacion);

        // 1.1.21 y 1.1.22: estado ACEPTADO con fecha y hora, y se guarda
        pedido.cambiarEstado(EstadoPedido.ACEPTADO);
        pedido = pedidoRepository.save(pedido);

        // 1.1.23 a 1.1.25: envío solo si es a domicilio (RN-06)
        Envio envio = null;
        if (aDomicilio) {
            String direccion = cliente.getDireccionEntrega();
            envio = envioRepository.save(new Envio(pedido, direccion));
        }

        // 1.1.26 y 1.1.27: se vacía el carrito
        carrito.vaciar();
        carritoRepository.save(carrito);

        // 1.1.28 a 1.1.30: notificaciones y respuesta
        notificacionService.notificarNuevoPedido(pedido);
        String enlace = notificacionService.generarEnlaceWhatsApp(pedido);
        return new PedidoResponseDTO(pedido, envio, enlace);
    }

    private ParametroSistema buscarParametro(String clave) {
        return parametroRepository.findByClave(clave)
                .orElseThrow(() -> new IllegalStateException("Falta el parámetro del sistema " + clave));
    }
}

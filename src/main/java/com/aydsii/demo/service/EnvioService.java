package com.aydsii.demo.service;

import com.aydsii.demo.dto.SeguimientoDTO;
import com.aydsii.demo.exception.RecursoNoEncontradoException;
import com.aydsii.demo.model.CambioEstado;
import com.aydsii.demo.model.Envio;
import com.aydsii.demo.model.Pedido;
import com.aydsii.demo.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CU-02 Consultar seguimiento de compra.
 * El cliente entra desde su propio historial (ya autenticado) y el id del envío viaja oculto,
 * así que no se contemplan "envío inexistente" ni "compra de otro cliente".
 */
@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    @Transactional(readOnly = true)
    public SeguimientoDTO obtenerSeguimiento(Long idEnvio) {
        // 1.1.1: buscar el envío.
        // findById devuelve Optional: el orElseThrow es solo técnico (por si se prueba un id
        // inventado desde Swagger), no es un flujo del caso de uso.
        Envio envio = envioRepository.findById(idEnvio)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envío no encontrado"));

        // 1.1.2 a 1.1.7: estado actual, datos del envío e historial
        Pedido pedido = envio.getPedido();
        List<CambioEstado> historial = pedido.getHistorial();
        return new SeguimientoDTO(envio, pedido, historial);
    }
}

package com.verdegreen.controller;

import com.verdegreen.dto.ApiResponse;
import com.verdegreen.dto.SeguimientoDTO;
import com.verdegreen.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "CU-02 Consultar seguimiento de compra",
        description = "Estado, historial y datos del envío de una compra a domicilio")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping("/{idEnvio}/seguimiento")
    @Operation(summary = "Consultar seguimiento de compra",
            description = "Se invoca desde la página de una compra a domicilio del historial del cliente, "
                    + "con el id (oculto) de su envío. Devuelve el estado actual, el historial de estados "
                    + "con fecha y hora, la dirección, la fecha programada y las observaciones.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Seguimiento de la compra")
    })
    public ResponseEntity<ApiResponse<SeguimientoDTO>> consultarSeguimiento(
            @Parameter(description = "Id del envío de la compra", example = "1") @PathVariable Long idEnvio) {
        SeguimientoDTO seguimiento = envioService.obtenerSeguimiento(idEnvio);
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), "Consulta realizada correctamente", seguimiento));
    }
}

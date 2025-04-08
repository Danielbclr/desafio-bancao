package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.StatsDTO;
import com.danbramos.desafio_bancao.service.StatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for retrieving transaction statistics.
 * This class provides endpoints to get statistical data for transactions
 * within a specified time interval or for all transactions.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Estatísticas", description = "Endpoint para obter estatísticas das transações")
public class StatsController {

    private final StatService statService;

    /**
     * Retrieves transaction statistics for a given time interval.
     *
     * @param intervalInS The time interval in seconds to consider for calculating statistics.
     *                    If not provided, defaults to 60 seconds. Must be a non-negative value.
     * @return ResponseEntity containing the Stats object with HTTP status OK.
     * Returns UNPROCESSABLE_ENTITY with error details if the interval is negative.
     */
    @GetMapping("/estatistica")
    @Operation(summary = "Obtém estatísticas das transações",
            description = "Retorna as estatísticas (soma, média, máximo, mínimo, contagem) das transações ocorridas no intervalo de tempo especificado (em segundos), terminando agora. O padrão é 60 segundos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatsDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: parâmetro não numérico)", content = @Content)
    })
    public ResponseEntity<StatsDTO> getStats(
            @Parameter(description = "Intervalo em segundos para calcular as estatísticas (padrão: 60). Valores negativos são tratados como 60.", example = "60")
            @RequestParam(value = "intervalInS", required = false, defaultValue = "60") Integer intervalInS) {
        return ResponseEntity.ok(statService.getStats(intervalInS));
    }
}

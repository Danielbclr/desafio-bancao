package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.StatsDTO;
import com.danbramos.desafio_bancao.service.StatService;
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
    public ResponseEntity<StatsDTO> getStats(@RequestParam(value = "intervalInS", required = false, defaultValue = "60") Integer intervalInS) {
        if (intervalInS < 0) {
            intervalInS = 60;
        }
        return ResponseEntity.ok(statService.getStats(intervalInS));
    }
}

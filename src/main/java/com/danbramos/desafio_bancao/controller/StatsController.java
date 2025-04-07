package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.model.Stats;
import com.danbramos.desafio_bancao.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private final StatService statService;

    @Autowired
    public StatsController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/estatistica")
    public ResponseEntity<Stats> getStats (@RequestParam(value = "intervalInS", required = false, defaultValue = "60") Integer intervalInS) {
        return ResponseEntity.ok(statService.getStats(intervalInS));
    }
}

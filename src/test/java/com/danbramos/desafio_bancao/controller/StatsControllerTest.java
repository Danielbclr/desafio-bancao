package com.danbramos.desafio_bancao.controller;

import com.danbramos.desafio_bancao.dtos.StatsDTO;
import com.danbramos.desafio_bancao.service.StatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatService statService;

    @Test
    void getStats_DefaultInterval_ShouldReturnStatsFromService() throws Exception {
        StatsDTO expectedStats = new StatsDTO(10L, 1000.0, 100.0, 50.0, 200.0);
        int defaultInterval = 60;

        when(statService.getStats(defaultInterval)).thenReturn(expectedStats);

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count", is(expectedStats.count()), Long.class))
                .andExpect(jsonPath("$.sum", is(expectedStats.sum())))
                .andExpect(jsonPath("$.avg", is(expectedStats.avg())))
                .andExpect(jsonPath("$.min", is(expectedStats.min())))
                .andExpect(jsonPath("$.max", is(expectedStats.max())));
    }

    @Test
    void getStats_WithValidIntervalParameter_ShouldReturnStatsFromService() throws Exception {
        StatsDTO expectedStats = new StatsDTO(5L, 500.0, 100.0, 80.0, 120.0);
        int interval = 30;

        when(statService.getStats(interval)).thenReturn(expectedStats);

        mockMvc.perform(get("/estatistica").param("intervalInS", String.valueOf(interval)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count", is(expectedStats.count()), Long.class))
                .andExpect(jsonPath("$.sum", is(expectedStats.sum())))
                .andExpect(jsonPath("$.avg", is(expectedStats.avg())))
                .andExpect(jsonPath("$.min", is(expectedStats.min())))
                .andExpect(jsonPath("$.max", is(expectedStats.max())));
    }

    @Test
    void getStats_WithNegativeIntervalParameter_ShouldUseDefaultInterval() throws Exception {
        StatsDTO expectedStats = new StatsDTO(8L, 800.0, 100.0, 90.0, 110.0);
        int negativeInterval = -10;
        int defaultInterval = 60;

        when(statService.getStats(defaultInterval)).thenReturn(expectedStats);

        mockMvc.perform(get("/estatistica").param("intervalInS", String.valueOf(negativeInterval)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getStats_WithInvalidIntervalParameter_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/estatistica").param("intervalInS", "not-a-number"))
                .andExpect(status().isBadRequest());
    }

}
package com.danbramos.desafio_bancao.dtos;

import java.time.OffsetDateTime;

public record TransactionDTO(Double value, OffsetDateTime dateTime) {
}

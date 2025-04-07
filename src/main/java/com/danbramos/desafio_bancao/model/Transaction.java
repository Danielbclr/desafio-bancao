package com.danbramos.desafio_bancao.model;

import java.time.OffsetDateTime;

public record Transaction(Double value, OffsetDateTime dateTime) {
}

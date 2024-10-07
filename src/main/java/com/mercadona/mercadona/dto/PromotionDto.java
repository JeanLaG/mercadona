package com.mercadona.mercadona.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromotionDto {
    private double promotion;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters et Setters
}

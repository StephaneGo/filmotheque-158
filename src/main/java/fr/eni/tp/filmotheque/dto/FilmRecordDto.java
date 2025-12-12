package fr.eni.tp.filmotheque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FilmRecordDto(
        @NotBlank         @Size(min = 1, max = 100)String titre,
        @Min(1895) int annee) {
}

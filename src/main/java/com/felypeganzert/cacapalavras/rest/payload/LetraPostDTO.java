package com.felypeganzert.cacapalavras.rest.payload;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LetraPostDTO {

    private char letra;

    @Min(value = 1, message = "Posição X não pode ser igual ou menor a 0")
    private int posicaoX;

    @Min(value = 1, message = "Posição Y não pode ser igual ou menor a 0")
    private int posicaoY;
    
}

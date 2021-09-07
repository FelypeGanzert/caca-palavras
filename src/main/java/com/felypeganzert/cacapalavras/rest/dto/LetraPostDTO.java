package com.felypeganzert.cacapalavras.rest.dto;

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
    private int posicaoX;
    private int posicaoY;
    
}

package com.felypeganzert.cacapalavras.entidades.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LetraDTO {

    private Integer id;
    private char letra;
    private int posicaoX;
    private int posicaoY;
    
}

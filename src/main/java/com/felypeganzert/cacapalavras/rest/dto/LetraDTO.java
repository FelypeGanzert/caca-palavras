package com.felypeganzert.cacapalavras.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LetraDTO {

    private Long id;
    private String letra;
    private int posicaoX;
    private int posicaoY;
    
}

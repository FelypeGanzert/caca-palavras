package com.felypeganzert.cacapalavras.rest.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TabuleiroPostDTO {

    @Min(value = Tabuleiro.LARGURA_MINIMA, message=("Largura precisa ser no mínimo " + Tabuleiro.LARGURA_MINIMA))
    private int largura;
    
    @Min(value = Tabuleiro.ALTURA_MINIMA, message=("Altura precisa ser no mínimo " + Tabuleiro.ALTURA_MINIMA))
    private int altura;

}

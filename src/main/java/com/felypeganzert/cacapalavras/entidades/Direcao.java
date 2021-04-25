package com.felypeganzert.cacapalavras.entidades;

import lombok.Getter;

@Getter
public enum Direcao {

    // NOROESTE NORTE NORDESTE
    // OESTE CENTRO LESTE
    // SUDOESTE SUL SUDESTE

    NOROESTE(-1,-1), NORTE(0,-1), NORDESTE(1,-1),
    OESTE(-1,0), LESTE(1,0),
    SUDOESTE(-1,1), SUL(0,1), SUDESTE(1,1);

    private int x;
    private int y;

    private Direcao(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}

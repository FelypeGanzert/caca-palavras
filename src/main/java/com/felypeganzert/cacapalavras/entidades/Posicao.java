package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Embeddable
@EqualsAndHashCode
public class Posicao {

    int x;
    int y;

    public Posicao(int x, int y) {
        validarPosicoes(x, y);
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x) {
        validarX(x);
        this.x = x;
    }

    public void setY(int y) {
        validarX(y);
        this.y = y;
    }

    private void validarPosicoes(int x, int y) {
        if (x <= 0 && y <= 0) {
            throw new IllegalArgumentException("Posição X e Y precisam ser positivas");
        }
        validarX(x);
        validarY(y);
    }

    private void validarX(int x) {
        if (x <= 0)
            throw new IllegalArgumentException("Posição X precisa ser positiva");
    }

    private void validarY(int y) {
        if (y <= 0)
            throw new IllegalArgumentException("Posição Y precisa ser positiva");
    }

}

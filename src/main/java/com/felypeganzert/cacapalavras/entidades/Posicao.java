package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Posicao {

    int posicaoX;
    int posicaoY;

    public Posicao(int posicaoX, int posicaoY) {
        validarPosicoes(posicaoX, posicaoY);
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
    }
    
    public void setPosicaoX(int posicaoX) {
        validarPosicaoX(posicaoX);
        this.posicaoX = posicaoX;
    }

    public void setPosicaoY(int posicaoY) {
        validarPosicaoX(posicaoY);
        this.posicaoY = posicaoY;
    }

    private void validarPosicoes(int x, int y) {
        if (x <= 0 && y <= 0) {
            throw new IllegalArgumentException("Posição X e Y precisam ser positivas");
        }
        validarPosicaoX(x);
        validarPosicaoY(y);
    }

    private void validarPosicaoX(int x) {
        if (x <= 0)
            throw new IllegalArgumentException("Posição X precisa ser positiva");
    }

    private void validarPosicaoY(int y) {
        if (y <= 0)
            throw new IllegalArgumentException("Posição Y precisa ser positiva");
    }

}

package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TabuleiroServiceImpl implements TabuleiroService {

    @Override
    public void inserirLetraEmCelula(Tabuleiro tabuleiro, Letra letra) {
        validaPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        limparPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        tabuleiro.getLetras().add(letra);
    }

    protected void validaPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        if(posicao.getPosicaoY() > tabuleiro.getAltura() || posicao.getPosicaoX() > tabuleiro.getLargura()){
            throw new IllegalStateException("Posição desejada para inserir não existe no tabuleiro");
        }
    }

    protected void limparPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        tabuleiro.getLetras().removeIf(l -> isLetraNaPosicao(l, posicao));
    }

    protected boolean isLetraNaPosicao(Letra letra, Posicao posicao) {
        Posicao posicaoLetra = letra.getPosicao();
        return posicaoLetra.getPosicaoX() == posicao.getPosicaoX()
                && posicaoLetra.getPosicaoY() == posicao.getPosicaoY();
    }

}

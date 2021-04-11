package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public interface CacaPalavrasService {

    void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras);
    void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras);
    void alterarDimensoesDoTabuleiro(CacaPalavras cacaPalavras);
    
}

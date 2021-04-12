package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;

public interface CacaPalavrasService {

    void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras);
    void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras);
    void alterarDimensoesDoTabuleiro(CacaPalavras cacaPalavras);
    
}

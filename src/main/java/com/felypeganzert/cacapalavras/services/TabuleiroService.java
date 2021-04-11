package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

public interface TabuleiroService {
    
    void inserirLetraEmCelula(Tabuleiro tabuleiro,  Letra letra);
    
}

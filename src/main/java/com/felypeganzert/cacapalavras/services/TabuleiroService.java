package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    
    void inserirLetra(Tabuleiro tabuleiro,  Letra letra);
    
}

package com.felypeganzert.cacapalavras.services;

import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    
    Optional<Tabuleiro> findById(Integer id);

    List<Letra> adicionarLetras(Integer idTabuleiro, List<Letra> letras);
    void inserirLetra(Tabuleiro tabuleiro,  Letra letra);
    
}

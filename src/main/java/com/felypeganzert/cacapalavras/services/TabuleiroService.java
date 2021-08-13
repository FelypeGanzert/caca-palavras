package com.felypeganzert.cacapalavras.services;

import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    
    Tabuleiro criarComBasico(TabuleiroPostDTO dto);
    Optional<Tabuleiro> findById(Integer id);

    void adicionarLetras(Integer idTabuleiro, List<Letra> letras);
    void inserirLetra(Tabuleiro tabuleiro,  Letra letra);
    
}

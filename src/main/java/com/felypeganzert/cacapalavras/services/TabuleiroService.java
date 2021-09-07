package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    Tabuleiro criarComBasico(TabuleiroPostDTO dto, Integer idCacaPalavras);

    Tabuleiro findById(Integer id, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);

    Tabuleiro save(Tabuleiro tabuleiro);
}

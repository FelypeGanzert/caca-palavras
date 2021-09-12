package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    Tabuleiro criarComBasico(TabuleiroDTO dto, Integer idCacaPalavras);

    Tabuleiro findById(Integer id, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);
}

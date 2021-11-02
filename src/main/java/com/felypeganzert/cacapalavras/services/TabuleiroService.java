package com.felypeganzert.cacapalavras.services;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;

import org.springframework.stereotype.Service;

@Service
public interface TabuleiroService {
    TabuleiroDTO criarComBasico(TabuleiroDTO dto, Integer idCacaPalavras);

    TabuleiroDTO findById(Integer id, Integer idCacaPalavras);

    Tabuleiro findByIdEntity(Integer id, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);
}

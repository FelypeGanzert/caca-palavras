package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;

import org.springframework.stereotype.Service;

@Service
public interface PalavraService {
    PalavraDTO adicionarPalavra(String palavra, Integer idCacaPalavras);

    List<PalavraDTO> findAll(Integer idCacaPalavras);

    PalavraDTO findById(Integer id, Integer idCacaPalavras);

    PalavraDTO atualizar(String palavra, Integer id, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);

    void deleteAll(Integer idCacaPalavras);
}

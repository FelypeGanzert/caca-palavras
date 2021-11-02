package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;

import org.springframework.stereotype.Service;

@Service
public interface LetraService {
    LetraDTO adicionarLetra(LetraDTO letra, Integer idTabuleiro, Integer idCacaPalavras);
    
    List<LetraDTO> adicionarLetras(List<LetraDTO> letras, Integer idTabuleiro, Integer idCacaPalavras);

    List<LetraDTO> findAll(Integer idTabuleiro, Integer idCacaPalavras);

    LetraDTO findById(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    LetraDTO atualizar(char letra, Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    void delete(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    void deleteAll(Integer idTabuleiro, Integer idCacaPalavras);
}

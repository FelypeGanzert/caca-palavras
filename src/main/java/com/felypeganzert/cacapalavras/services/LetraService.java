package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface LetraService {
    Letra adicionarLetra(LetraPostDTO letra, Integer idTabuleiro, Integer idCacaPalavras);
    
    List<Letra> adicionarLetras(List<LetraPostDTO> letras, Integer idTabuleiro, Integer idCacaPalavras);

    List<Letra> findAll(Integer idTabuleiro, Integer idCacaPalavras);

    Letra findById(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    Letra atualizar(LetraDTO dto, Integer idTabuleiro, Integer idCacaPalavras);

    void delete(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    void deleteAll(Integer idTabuleiro, Integer idCacaPalavras);
}

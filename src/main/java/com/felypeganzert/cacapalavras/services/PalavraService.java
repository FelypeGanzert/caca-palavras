package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPutDTO;

import org.springframework.stereotype.Service;

@Service
public interface PalavraService {
    Palavra adicionarPalavra(String palavra, Integer idCacaPalavras);

    List<Palavra> findAll(Integer idCacaPalavras);

    Palavra findById(Integer id, Integer idCacaPalavras);

    Palavra atualizar(PalavraPutDTO dto, Integer idCacaPalavras);

    void delete(Integer id, Integer idCacaPalavras);

    void deleteAll(Integer idCacaPalavras);

    void limparLocalizacoes(List<Palavra> palavras);

    void limparLocalizacoes(Palavra palavra);

    Palavra save(Palavra palavra);

    List<Palavra> saveAll(List<Palavra> palavras);
}

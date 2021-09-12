package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;

import org.springframework.stereotype.Service;

@Service
public interface LetraService {
    Letra adicionarLetra(Letra letra, Integer idTabuleiro, Integer idCacaPalavras);
    
    List<Letra> adicionarLetras(List<Letra> letras, Integer idTabuleiro, Integer idCacaPalavras);

    List<Letra> findAll(Integer idTabuleiro, Integer idCacaPalavras);

    Letra findById(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    Letra atualizar(char letra, Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    void delete(Integer id, Integer idTabuleiro, Integer idCacaPalavras);

    void deleteAll(Integer idTabuleiro, Integer idCacaPalavras);
}

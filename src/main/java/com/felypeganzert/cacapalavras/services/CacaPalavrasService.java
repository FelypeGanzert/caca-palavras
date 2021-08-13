package com.felypeganzert.cacapalavras.services;

import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasService {

    CacaPalavras criarComBasico(CacaPalavrasPostDTO dto);
    List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();
    Optional<CacaPalavras> findById(Integer id);

    void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras);
    void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras);
    
}

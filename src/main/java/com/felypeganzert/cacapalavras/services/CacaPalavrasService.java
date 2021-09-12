package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasService {
    CacaPalavras criarComBasico(CacaPalavrasPostDTO dto);

    List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();

    CacaPalavras findById(Integer id);

    void delete(Integer id);

    CacaPalavras resolverCacaPalavras(Integer id);
}

package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;

import org.springframework.stereotype.Service;

@Service
public interface CacaPalavrasService {
    CacaPalavrasDTO criarComBasico(CacaPalavrasDTO dto);

    List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas();

    CacaPalavrasDTO findById(Integer id);

    CacaPalavras findByIdEntity(Integer id);

    void delete(Integer id);

    CacaPalavrasDTO resolverCacaPalavras(Integer id);
}

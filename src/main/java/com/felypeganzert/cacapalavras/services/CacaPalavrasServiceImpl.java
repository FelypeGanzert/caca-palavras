package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacaPalavrasServiceImpl implements CacaPalavrasService{

    private final CacaPalavrasResolver resolver;

    @Override
    public void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras) {
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);   
    }

    @Override
    public void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras) {
        cacaPalavras.getTabuleiro().getLetras().clear();
        limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());
    }

    protected void limparLocalizacoesDasPalavrasNoTabuleiro(List<Palavra> palavras) {
        palavras.forEach(p -> p.getLocalizacoesNoTabuleiro().clear());
    }
    
}

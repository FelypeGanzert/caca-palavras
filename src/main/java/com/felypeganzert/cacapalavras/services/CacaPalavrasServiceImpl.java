package com.felypeganzert.cacapalavras.services;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;

import org.springframework.stereotype.Service;

@Service
public class CacaPalavrasServiceImpl implements CacaPalavrasService{

    @Override
    public void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras) {
        // TODO: Implementar utilizacao de CacaPalavraResolver
        
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

package com.felypeganzert.cacapalavras.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoLetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoPalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;

import org.springframework.stereotype.Component;

@Component
public class CacaPalavrasMaperImpl implements CacaPalavrasMaper{

    public CacaPalavrasDTO toCacaPalavrasDTO(CacaPalavras cacaPalavras){
        return CacaPalavrasDTO.builder()
                .id(cacaPalavras.getId())
                .dataCriacao(cacaPalavras.getDataCriacao())
                .criador(cacaPalavras.getCriador())
                .titulo(cacaPalavras.getTitulo())
                .tabuleiro(toTabuleiroDTO(cacaPalavras.getTabuleiro()))
                .palavras(toPalavrasDTO(cacaPalavras.getPalavras()))
                .build();
    }

    public TabuleiroDTO toTabuleiroDTO(Tabuleiro tabuleiro){
        return TabuleiroDTO.builder()
                .id(tabuleiro.getId())
                .largura(tabuleiro.getLargura())
                .altura(tabuleiro.getAltura())
                .letras(toLetrasDTO(tabuleiro.getLetras()))
                .build();
    }

    public List<LetraPutDTO> toLetrasDTO(List<Letra> letras){
        return letras.stream().map(l -> toLetraDTO(l)).collect(Collectors.toList());
    }

    public LetraPutDTO toLetraDTO(Letra letra){
        return LetraPutDTO.builder()
                .letra(letra.getLetra())
                .build();
    }

    public List<Letra> toLetras(List<LetraPostDTO> letras){
        return letras.stream().map(l -> toLetra(l)).collect(Collectors.toList());
    }

    public Letra toLetra(LetraPostDTO dto){
        return Letra.builder()
                .letra(dto.getLetra())
                .posicao(new Posicao(dto.getPosicaoX(), dto.getPosicaoY()))
                .build();
    }

    public List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras){
        return palavras.stream().map(p -> toPalavraDTO(p)).collect(Collectors.toList());
    }

    public PalavraDTO toPalavraDTO(Palavra palavra){
        return PalavraDTO.builder()
                .id(palavra.getId())
                .palavra(palavra.getPalavra())
                .localizacoes(toLocalizacoesPalavraDTO(palavra.getLocalizacoes()))
                .build();
    }

    private List<LocalizacaoPalavraDTO> toLocalizacoesPalavraDTO(List<LocalizacaoPalavra> localizacoesPalavra){
        return localizacoesPalavra.stream().map(l -> toLocalizacaoPalavraDTO(l)).collect(Collectors.toList());
    }

    private LocalizacaoPalavraDTO toLocalizacaoPalavraDTO(LocalizacaoPalavra localizacaoPalavra){
        return LocalizacaoPalavraDTO.builder()
                .id(localizacaoPalavra.getId())
                .localizacoesLetras(toLocalizacoesLetraDTO(localizacaoPalavra.getLocalizacoesLetras()))
                .build();
    }

    private List<LocalizacaoLetraDTO> toLocalizacoesLetraDTO(List<LocalizacaoLetra> localizacoesLetra){
        return localizacoesLetra.stream().map(l -> toLocalizacaoLetraDTO(l)).collect(Collectors.toList());
    }

    private LocalizacaoLetraDTO toLocalizacaoLetraDTO(LocalizacaoLetra localizacaoLetra){
        return LocalizacaoLetraDTO.builder()
                .id(localizacaoLetra.getId())
                .ordem(localizacaoLetra.getOrdem())
                .letraId(localizacaoLetra.getLetra().getId())
                .build();
    }

    
}

package com.felypeganzert.cacapalavras.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.LocalizacaoLetraNoTabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.dto.LocalizacaoPalavraNoTabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroDTO;

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

    public List<LetraDTO> toLetrasDTO(List<Letra> letras){
        return letras.stream().map(l -> toLetraDTO(l)).collect(Collectors.toList());
    }

    public LetraDTO toLetraDTO(Letra letra){
        return LetraDTO.builder()
                .id(letra.getId())
                .letra(letra.getLetra())
                .posicaoX(letra.getPosicao().getX())
                .posicaoY(letra.getPosicao().getY())
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
                .localizacoesNoTabuleiro(toLocalizacoesPalavraDTO(palavra.getLocalizacoesNoTabuleiro()))
                .build();
    }

    private List<LocalizacaoPalavraNoTabuleiroDTO> toLocalizacoesPalavraDTO(List<LocalizacaoPalavraNoTabuleiro> localizacoesPalavra){
        return localizacoesPalavra.stream().map(l -> toLocalizacaoPalavraDTO(l)).collect(Collectors.toList());
    }

    private LocalizacaoPalavraNoTabuleiroDTO toLocalizacaoPalavraDTO(LocalizacaoPalavraNoTabuleiro localizacaoPalavra){
        return LocalizacaoPalavraNoTabuleiroDTO.builder()
                .id(localizacaoPalavra.getId())
                .localizacoesLetrasNoTabuleiro(toLocalizacoesLetraDTO(localizacaoPalavra.getLocalizacoesLetrasNoTabuleiro()))
                .build();
    }

    private List<LocalizacaoLetraNoTabuleiroDTO> toLocalizacoesLetraDTO(List<LocalizacaoLetraNoTabuleiro> localizacoesLetra){
        return localizacoesLetra.stream().map(l -> toLocalizacaoLetraDTO(l)).collect(Collectors.toList());
    }

    private LocalizacaoLetraNoTabuleiroDTO toLocalizacaoLetraDTO(LocalizacaoLetraNoTabuleiro localizacaoLetra){
        return LocalizacaoLetraNoTabuleiroDTO.builder()
                .id(localizacaoLetra.getId())
                .ordem(localizacaoLetra.getOrdem())
                .letraId(localizacaoLetra.getLetra().getId())
                .build();
    }

    
}

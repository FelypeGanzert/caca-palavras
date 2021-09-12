package com.felypeganzert.cacapalavras.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoLetra;
import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoLetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LocalizacaoPalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;

import org.springframework.stereotype.Component;

@Component
public class CacaPalavrasMapperImpl implements CacaPalavrasMapper{

    @Override
    public CacaPalavrasDTO toCacaPalavrasDTO(CacaPalavras c){
        return CacaPalavrasDTO.builder()
                .id(c.getId())
                .dataCriacao(c.getDataCriacao())
                .criador(c.getCriador())
                .titulo(c.getTitulo())
                .tabuleiro(toTabuleiroDTO(c.getTabuleiro()))
                .palavras(toPalavrasDTO(c.getPalavras()))
                .build();
    }

    @Override
    public TabuleiroDTO toTabuleiroDTO(Tabuleiro t){
        return TabuleiroDTO.builder()
                .id(t.getId())
                .largura(t.getLargura())
                .altura(t.getAltura())
                .letras(toLetrasDTO(t.getLetras()))
                .build();
    }

    @Override
    public LetraDTO toLetraDTO(Letra l){
        return LetraDTO.builder()
                .id(l.getId())
                .letra(l.getLetra())
                .posicaoX(l.getPosicao().getX())
                .posicaoY(l.getPosicao().getY())
                .build();
    }

    @Override
    public List<LetraDTO> toLetrasDTO(List<Letra> letras){
        return letras.stream().map(l -> toLetraDTO(l)).collect(Collectors.toList());
    }

    @Override
    public PalavraDTO toPalavraDTO(Palavra p){
        return PalavraDTO.builder()
                .id(p.getId())
                .palavra(p.getPalavra())
                .localizacoes(toLocalizacoesPalavraDTO(p.getLocalizacoes()))
                .build();
    }

    @Override
    public List<PalavraDTO> toPalavrasDTO(List<Palavra> palavras){
        return palavras.stream().map(p -> toPalavraDTO(p)).collect(Collectors.toList());
    }

    private LocalizacaoPalavraDTO toLocalizacaoPalavraDTO(LocalizacaoPalavra lp){
        return LocalizacaoPalavraDTO.builder()
                .id(lp.getId())
                .localizacoesLetras(toLocalizacoesLetraDTO(lp.getLocalizacoesLetras()))
                .build();
    }

    private List<LocalizacaoPalavraDTO> toLocalizacoesPalavraDTO(List<LocalizacaoPalavra> localizacoesPalavra){
        return localizacoesPalavra.stream().map(l -> toLocalizacaoPalavraDTO(l)).collect(Collectors.toList());
    }

    private LocalizacaoLetraDTO toLocalizacaoLetraDTO(LocalizacaoLetra ll){
        return LocalizacaoLetraDTO.builder()
                .id(ll.getId())
                .ordem(ll.getOrdem())
                .letraId(ll.getLetra().getId())
                .build();
    }

    private List<LocalizacaoLetraDTO> toLocalizacoesLetraDTO(List<LocalizacaoLetra> localizacoesLetra){
        return localizacoesLetra.stream().map(l -> toLocalizacaoLetraDTO(l)).collect(Collectors.toList());
    }
    
}

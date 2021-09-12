package com.felypeganzert.cacapalavras.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;

import org.springframework.stereotype.Component;

@Component
public class CacaPalavrasPayloadMaperImpl implements CacaPalavrasPayloadMaper{

    @Override
    public CacaPalavrasDTO toCacaPalavrasDTO(CacaPalavrasPostDTO postDTO) {
        return CacaPalavrasDTO.builder()
                .criador(postDTO.getCriador())
                .titulo(postDTO.getTitulo())
                .build();
    }

    @Override
    public TabuleiroDTO toTabuleiroDTO(TabuleiroPostDTO postDTO) {
        return TabuleiroDTO.builder()
                .largura(postDTO.getLargura())
                .altura(postDTO.getAltura())
                .build();
    }

    @Override
    public Letra toLetra(LetraPostDTO postDTO) {
        return Letra.builder()
                .letra(postDTO.getLetra())
                .posicao(new Posicao(postDTO.getPosicaoX(), postDTO.getPosicaoY()))
                .build();
    }

    @Override
    public List<Letra> toLetras(List<LetraPostDTO> letrasPostDTO) {
        return letrasPostDTO.stream()
                .map(l -> toLetra(l))
                .collect(Collectors.toList());
    }

}

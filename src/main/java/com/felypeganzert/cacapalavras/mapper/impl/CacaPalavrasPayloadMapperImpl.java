package com.felypeganzert.cacapalavras.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;

import org.springframework.stereotype.Component;

@Component
public class CacaPalavrasPayloadMapperImpl implements CacaPalavrasPayloadMapper{

    @Override
    public CacaPalavrasDTO toCacaPalavrasDTO(CacaPalavrasPostDTO postDTO) {
        if(postDTO == null){
            return null;
        }
        return CacaPalavrasDTO.builder()
                .criador(postDTO.getCriador())
                .titulo(postDTO.getTitulo())
                .build();
    }

    @Override
    public TabuleiroDTO toTabuleiroDTO(TabuleiroPostDTO postDTO) {
        if(postDTO == null){
            return null;
        }
        return TabuleiroDTO.builder()
                .largura(postDTO.getLargura())
                .altura(postDTO.getAltura())
                .build();
    }

    @Override
    public LetraDTO toLetraDTO(LetraPostDTO postDTO) {
        if(postDTO == null){
            return null;
        }
        return LetraDTO.builder()
                .letra(postDTO.getLetra())
                .posicaoX(postDTO.getPosicaoX())
                .posicaoY(postDTO.getPosicaoY())
                .build();
    }

    @Override
    public List<LetraDTO> toLetrasDTO(List<LetraPostDTO> letrasPostDTO) {
        if(letrasPostDTO == null){
            return new ArrayList<LetraDTO>();
        }
        return letrasPostDTO.stream()
                .map(l -> toLetraDTO(l))
                .collect(Collectors.toList());
    }

}

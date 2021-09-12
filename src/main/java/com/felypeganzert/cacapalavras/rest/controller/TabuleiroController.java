package com.felypeganzert.cacapalavras.rest.controller;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMaper;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/tabuleiro")
@RequiredArgsConstructor
public class TabuleiroController {

    private final TabuleiroService service;
    private final CacaPalavrasMaper maper;
    private final CacaPalavrasPayloadMaper payloadMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer criarComBasico(@RequestBody TabuleiroPostDTO postDTO, @PathVariable Integer idCacaPalavras) {
        TabuleiroDTO dto = payloadMapper.toTabuleiroDTO(postDTO);
        Tabuleiro tabuleiro = service.criarComBasico(dto, idCacaPalavras);
        return tabuleiro.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TabuleiroDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        Tabuleiro tabuleiro = service.findById(id, idCacaPalavras);
        return maper.toTabuleiroDTO(tabuleiro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idCacaPalavras);
    }

}

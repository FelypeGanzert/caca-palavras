package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPutDTO;
import com.felypeganzert.cacapalavras.services.PalavraService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/caca-palavras/{idCacaPalavras}/palavras")
@RequiredArgsConstructor
public class PalavraController {

    private final PalavraService service;
    private final CacaPalavrasMaper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer adicionarPalavra(@RequestBody PalavraPostDTO palavraPostDTO, @PathVariable Integer idCacaPalavras) {
        Palavra palavraAdicionada = service.adicionarPalavra(palavraPostDTO.getPalavra(), idCacaPalavras);
        return palavraAdicionada.getId();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PalavraDTO> findAll(@PathVariable Integer idCacaPalavras) {
        List<Palavra> palavras = service.findAll(idCacaPalavras);
        return mapper.toPalavrasDTO(palavras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PalavraDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        Palavra palavra = service.findById(id, idCacaPalavras);
        return mapper.toPalavraDTO(palavra);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PalavraDTO atualizar(@RequestBody PalavraPutDTO dto, @PathVariable Integer idCacaPalavras) {
        Palavra palavra = service.atualizar(dto, idCacaPalavras);
        return mapper.toPalavraDTO(palavra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idCacaPalavras);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@PathVariable Integer idCacaPalavras) {
        service.deleteAll(idCacaPalavras);
    }

}
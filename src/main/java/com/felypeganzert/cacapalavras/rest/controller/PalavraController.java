package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.rest.payload.PalavraRequestDTO;
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
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/palavras")
@RequiredArgsConstructor
public class PalavraController {

    private final PalavraService service;
    private final CacaPalavrasMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer adicionarPalavra(@Valid @RequestBody PalavraRequestDTO palavraPostDTO,
            @PathVariable Integer idCacaPalavras) {

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
    public PalavraDTO atualizar(@RequestBody PalavraRequestDTO dto, @PathVariable Integer id,
            @PathVariable Integer idCacaPalavras) {
                
        Palavra palavra = service.atualizar(dto.getPalavra(), id, idCacaPalavras);
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

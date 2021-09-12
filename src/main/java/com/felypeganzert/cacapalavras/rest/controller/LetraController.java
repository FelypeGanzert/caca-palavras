package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraPostDTO;
import com.felypeganzert.cacapalavras.services.LetraService;

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
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/tabuleiro/{idTabuleiro}")
@RequiredArgsConstructor
public class LetraController {

    private final LetraService service;
    private final CacaPalavrasMaper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer adicionarLetra(@RequestBody LetraPostDTO dto,  @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        Letra letra = service.adicionarLetra(dto, idTabuleiro, idCacaPalavras);
        return letra.getId();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LetraDTO> findAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        List<Letra> letras = service.findAll(idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(letras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LetraDTO findById(@PathVariable Integer id, @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        Letra letra = service.findById(id, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LetraDTO atualizar(@RequestBody LetraDTO dto, @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        Letra letra = service.atualizar(dto, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idTabuleiro, idCacaPalavras);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        service.deleteAll(idTabuleiro, idCacaPalavras);
    }

}

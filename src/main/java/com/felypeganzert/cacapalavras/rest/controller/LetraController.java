package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;
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
    public Integer adicionarLetra(@Valid @RequestBody LetraPostDTO dto,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        Letra letra = new Letra(dto.getLetra(), new Posicao(dto.getPosicaoX(), dto.getPosicaoY()));
        letra = service.adicionarLetra(letra, idTabuleiro, idCacaPalavras);
        return letra.getId();
    }

    @PostMapping("adicionar-em-lote")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO: Refatorar para retornar um LetraDTO
    public List<LetraPutDTO> adicionarLetras(@Valid @RequestBody List<LetraPostDTO> letrasParaAdicionar,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        // TODO: transferir essa 'conversão' para um mapper
        List<Letra> letras = letrasParaAdicionar.stream()
                                .map(l -> Letra.builder()
                                            .letra(l.getLetra())
                                            .posicao(new Posicao(l.getPosicaoX(), l.getPosicaoY()))
                                            .build())
                                .collect(Collectors.toList());

        letras = service.adicionarLetras(letras, idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(letras);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // TODO: Refatorar para retornar um LetraDTO
    public List<LetraPutDTO> findAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        List<Letra> letras = service.findAll(idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(letras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LetraPutDTO findById(@PathVariable Integer id, @PathVariable Integer idTabuleiro,
            @PathVariable Integer idCacaPalavras) {
                
        Letra letra = service.findById(id, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LetraPutDTO atualizar(@RequestBody LetraPutDTO dto, @PathVariable Integer id,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        Letra letra = service.atualizar(dto.getLetra(), id, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, @PathVariable Integer idTabuleiro,
            @PathVariable Integer idCacaPalavras) {

        service.delete(id, idTabuleiro, idCacaPalavras);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        service.deleteAll(idTabuleiro, idCacaPalavras);
    }

}

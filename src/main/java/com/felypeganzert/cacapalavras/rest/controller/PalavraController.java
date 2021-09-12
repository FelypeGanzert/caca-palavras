package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.dto.PalavraDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.rest.payload.PalavraRequestDTO;
import com.felypeganzert.cacapalavras.services.PalavraService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/palavras")
@RequiredArgsConstructor
@Api(value = "API REST Palavra")
@CrossOrigin(origins = "*")
public class PalavraController {

    private final PalavraService service;
    private final CacaPalavrasMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adiciona uma Palavra ao Caça Palavras")
    public Integer adicionarPalavra(@Valid @RequestBody PalavraRequestDTO palavraPostDTO,
            @PathVariable Integer idCacaPalavras) {

        Palavra palavraAdicionada = service.adicionarPalavra(palavraPostDTO.getPalavra(), idCacaPalavras);
        return palavraAdicionada.getId();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna todas as Palavras de um Caça Palavras")
    public List<PalavraDTO> findAll(@PathVariable Integer idCacaPalavras) {
        List<Palavra> palavras = service.findAll(idCacaPalavras);
        return mapper.toPalavrasDTO(palavras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna uma Palavra única")
    public PalavraDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        Palavra palavra = service.findById(id, idCacaPalavras);
        return mapper.toPalavraDTO(palavra);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualiza uma Palavra")
    public PalavraDTO atualizar(@RequestBody PalavraRequestDTO dto, @PathVariable Integer id,
            @PathVariable Integer idCacaPalavras) {
                
        Palavra palavra = service.atualizar(dto.getPalavra(), id, idCacaPalavras);
        return mapper.toPalavraDTO(palavra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta uma Palavra")
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idCacaPalavras);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta todas as Palavras de um Caça Palavras")
    public void deleteAll(@PathVariable Integer idCacaPalavras) {
        service.deleteAll(idCacaPalavras);
    }

}

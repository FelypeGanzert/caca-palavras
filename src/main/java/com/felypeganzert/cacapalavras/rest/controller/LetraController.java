package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.LetraPostDTO;
import com.felypeganzert.cacapalavras.rest.payload.LetraPutDTO;
import com.felypeganzert.cacapalavras.services.LetraService;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/tabuleiro/{idTabuleiro}/letras")
@RequiredArgsConstructor
@Api(value = "API REST Letra")
@CrossOrigin(origins = "*")
public class LetraController {

    private final LetraService service;
    private final CacaPalavrasMapper mapper;
    private final CacaPalavrasPayloadMapper payloadMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adiciona uma Letra ao Tabuleiro")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Letra adicionada")
    })
    public Integer adicionarLetra(@Valid @RequestBody LetraPostDTO dto,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        Letra letra = payloadMapper.toLetra(dto);
        letra = service.adicionarLetra(letra, idTabuleiro, idCacaPalavras);
        return letra.getId();
    }

    @PostMapping("adicionar-em-lote")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adiciona Letras ao Tabuleiro")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Letras adicionadas")
    })
    public List<LetraDTO> adicionarLetras(@Valid @RequestBody List<LetraPostDTO> letrasParaAdicionar,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        List<Letra> letras = payloadMapper.toLetras(letrasParaAdicionar);

        letras = service.adicionarLetras(letras, idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(letras);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna todas as Letras de um Tabuleiro")
    public List<LetraDTO> findAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        List<Letra> letras = service.findAll(idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(letras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna uma Letra única")
    public LetraDTO findById(@PathVariable Integer id, @PathVariable Integer idTabuleiro,
            @PathVariable Integer idCacaPalavras) {
                
        Letra letra = service.findById(id, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Atualiza uma Letra")
    public LetraDTO atualizar(@RequestBody LetraPutDTO dto, @PathVariable Integer id,
            @PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {

        Letra letra = service.atualizar(dto.getLetra(), id, idTabuleiro, idCacaPalavras);
        return mapper.toLetraDTO(letra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta uma Letra")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Letra deletada")
    })
    public void delete(@PathVariable Integer id, @PathVariable Integer idTabuleiro,
            @PathVariable Integer idCacaPalavras) {

        service.delete(id, idTabuleiro, idCacaPalavras);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Todas as Letras foram deletadas")
    })
    @ApiOperation(value = "Deleta todas as Letras de um Tabuleiro")
    public void deleteAll(@PathVariable Integer idTabuleiro, @PathVariable Integer idCacaPalavras) {
        service.deleteAll(idTabuleiro, idCacaPalavras);
    }

}

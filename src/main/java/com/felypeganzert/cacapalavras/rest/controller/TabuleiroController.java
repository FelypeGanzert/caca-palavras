package com.felypeganzert.cacapalavras.rest.controller;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasPayloadMapper;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/tabuleiro")
@RequiredArgsConstructor
@Api(value = "API REST Tabuleiro")
@CrossOrigin(origins = "*")
public class TabuleiroController {

    private final TabuleiroService service;
    private final CacaPalavrasMapper mapper;
    private final CacaPalavrasPayloadMapper payloadMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um Tabuleiro com informações básicas")
    public Integer criarComBasico(@RequestBody TabuleiroPostDTO postDTO, @PathVariable Integer idCacaPalavras) {
        TabuleiroDTO dto = payloadMapper.toTabuleiroDTO(postDTO);
        Tabuleiro tabuleiro = service.criarComBasico(dto, idCacaPalavras);
        return tabuleiro.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna um Tabuleiro único")
    public TabuleiroDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        Tabuleiro tabuleiro = service.findById(id, idCacaPalavras);
        return mapper.toTabuleiroDTO(tabuleiro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta um Tabuleiro")
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idCacaPalavras);
    }

}

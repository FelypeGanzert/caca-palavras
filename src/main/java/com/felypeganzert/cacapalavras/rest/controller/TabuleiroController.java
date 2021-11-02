package com.felypeganzert.cacapalavras.rest.controller;

import javax.validation.Valid;

import com.felypeganzert.cacapalavras.entidades.dto.TabuleiroDTO;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/caca-palavras/{idCacaPalavras}/tabuleiro")
@RequiredArgsConstructor
@Api(value = "API REST Tabuleiro")
@CrossOrigin(origins = "*")
public class TabuleiroController {

    private final TabuleiroService service;
    private final CacaPalavrasPayloadMapper payloadMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um Tabuleiro com informações básicas")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Tabuleiro criado")
    })
    public TabuleiroDTO criarComBasico(@RequestBody @Valid TabuleiroPostDTO postDTO, @PathVariable Integer idCacaPalavras) {
        TabuleiroDTO dto = payloadMapper.toTabuleiroDTO(postDTO);
        return service.criarComBasico(dto, idCacaPalavras);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retorna um Tabuleiro único")
    public TabuleiroDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        return service.findById(id, idCacaPalavras);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta um Tabuleiro")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Tabuleiro deletado")
    })
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras) {
        service.delete(id, idCacaPalavras);
    }

}

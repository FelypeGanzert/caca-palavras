package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("api/caca-palavras")
@RequiredArgsConstructor
public class CacaPalavrasController {

    private final CacaPalavrasService service;

    private final CacaPalavrasMaper cacaPalavrasMapper;

    private final CacaPalavrasRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer salvar(@RequestBody CacaPalavrasPostDTO dto) {
         CacaPalavras cacaPalavras = service.salvar(dto);
         return cacaPalavras.getId();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas(){
        return service.findAllComInformacoesBasicas();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CacaPalavrasDTO findById(@PathVariable Integer id){
        CacaPalavras cacaPalavras =  repository.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        
        return cacaPalavrasMapper.toCacaPalavrasDTO(cacaPalavras);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id){
        CacaPalavras cacaPalavras =  repository.findById(id)
                                        .orElseThrow(() ->
                                                new ResponseStatusException (HttpStatus.NOT_FOUND,"Caça Palavras não encontrado"));
        repository.delete(cacaPalavras);
    }
    
}

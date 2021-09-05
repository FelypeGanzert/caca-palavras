package com.felypeganzert.cacapalavras.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
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
@RequestMapping("api/caca-palavras/{idCacaPalavras}/tabuleiro")
@RequiredArgsConstructor
public class TabuleiroController {

    private final TabuleiroService service;
    private final CacaPalavrasMaper maper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer criarComBasico(@RequestBody TabuleiroPostDTO dto, @PathVariable Integer idCacaPalavras) {
        Tabuleiro tabuleiro = service.criarComBasico(dto, idCacaPalavras);
        return tabuleiro.getId();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TabuleiroDTO findById(@PathVariable Integer id, @PathVariable Integer idCacaPalavras){
        Tabuleiro tabuleiro=  service.findById(id, idCacaPalavras);
        return maper.toTabuleiroDTO(tabuleiro);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id, @PathVariable Integer idCacaPalavras){
        service.delete(id, idCacaPalavras);
    }

    // TODO: mover para o controller de letras
    @PostMapping("/{id}/letras")
    @ResponseStatus(HttpStatus.CREATED)
    public List<LetraDTO> adicionarLetras(@PathVariable Integer id, @RequestBody List<LetraDTO> letrasDto){
        List<Letra> letras = letrasDto.stream()
                                .map(l -> {
                                        Posicao posicao = new Posicao(l.getPosicaoX(), l.getPosicaoY());
                                        return new Letra(l.getLetra(), posicao);
                                    }
                                ).collect(Collectors.toList());
        
        letras = service.adicionarLetras(id, letras);
        return maper.toLetrasDTO(letras);
    }

    //TODO: analisar possibilidade de criar métodos para:
    //- remover uma letra do tabuleiro
    //- limpar todas as letras do tabuleiro

}

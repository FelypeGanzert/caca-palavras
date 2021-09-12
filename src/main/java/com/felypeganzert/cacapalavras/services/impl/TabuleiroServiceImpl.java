package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.payload.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TabuleiroServiceImpl implements TabuleiroService {
    
    private final TabuleiroRepository repository;
    private final CacaPalavrasService serviceCacaPalavras;
    private final LocalizacaoPalavraService serviceLocalizacaoPalavra;

    @Override
    @Transactional
    public Tabuleiro criarComBasico(TabuleiroPostDTO dto, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = serviceCacaPalavras.findById(idCacaPalavras);

        Tabuleiro tabuleiro = new Tabuleiro(dto.getLargura(), dto.getAltura());
        tabuleiro.setCacaPalavras(cacaPalavras);

        tabuleiro = repository.save(tabuleiro);
        return tabuleiro;
    }

    @Override
    public Tabuleiro findById(Integer id, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = serviceCacaPalavras.findById(idCacaPalavras);
        Tabuleiro tabuleiro =  repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(TABULEIRO, ID, id));

        if(tabuleiro.getCacaPalavras().getId() != cacaPalavras.getId()){
            throw new RecursoNaoPertenceAException(TABULEIRO, CACA_PALAVRAS);
        }

        return tabuleiro;
    }

    @Override
    public void delete(Integer id, Integer idCacaPalavras){
        Tabuleiro tabuleiro = findById(id, idCacaPalavras);
        serviceLocalizacaoPalavra.deleteAllAssociadasAoTabuleiro(tabuleiro.getId());
        repository.delete(tabuleiro);
    }

}

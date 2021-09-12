package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;

import java.time.LocalDateTime;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.services.CacaPalavrasResolver;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacaPalavrasServiceImpl implements CacaPalavrasService {

    private final CacaPalavrasResolver resolver;
    private final CacaPalavrasRepository repository;

    @Override
    @Transactional
    public CacaPalavras criarComBasico(CacaPalavrasDTO dto) {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador(dto.getCriador());
        cacaPalavras.setTitulo(dto.getTitulo());

        cacaPalavras = repository.save(cacaPalavras);
        return cacaPalavras;
    }

    @Override
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas() {
        return repository.findAllComInformacoesBasicas();
    }

    @Override
    public CacaPalavras findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(CACA_PALAVRAS, ID, id));
    }

    @Override
    public void delete(Integer id) {
        CacaPalavras cacaPalavras = findById(id);
        repository.delete(cacaPalavras);
    }

    @Override
    @Transactional
    public CacaPalavras resolverCacaPalavras(Integer id) {
        CacaPalavras cacaPalavras = findById(id);

        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);

        cacaPalavras = repository.save(cacaPalavras);
        return cacaPalavras;
    }

}

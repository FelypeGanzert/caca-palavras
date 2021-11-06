package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;

import java.time.LocalDateTime;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.dto.CacaPalavrasDTO;
import com.felypeganzert.cacapalavras.entidades.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacaPalavrasServiceImpl implements CacaPalavrasService {

    private final CacaPalavrasResolverServiceImpl resolver;
    private final CacaPalavrasRepository repository;
    private final CacaPalavrasMapper mapper;

    private final LocalizacaoPalavraService localizacaoPalavraService;

    @Override
    @Transactional
    public CacaPalavrasDTO criarComBasico(CacaPalavrasDTO dto) {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador(dto.getCriador());
        cacaPalavras.setTitulo(dto.getTitulo());

        cacaPalavras = repository.save(cacaPalavras);
        return mapper.toCacaPalavrasDTO(cacaPalavras);
    }

    @Override
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas() {
        return repository.findAllComInformacoesBasicas();
    }

    @Override
    public CacaPalavras findByIdEntity(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(CACA_PALAVRAS, ID, id));
    }

    @Override
    public CacaPalavrasDTO findById(Integer id) {
        return mapper.toCacaPalavrasDTO(this.findByIdEntity(id));
    }

    @Override
    public void delete(Integer id) {
        CacaPalavras cacaPalavras = findByIdEntity(id);
        repository.delete(cacaPalavras);
    }

    @Override
    @Transactional
    public CacaPalavrasDTO resolverCacaPalavras(Integer id) {
        localizacaoPalavraService.deleteAllAssociadasAoCacaPalavras(id);

        CacaPalavras cacaPalavras = findByIdEntity(id);
        resolver.resolver(cacaPalavras);

        cacaPalavras = repository.save(cacaPalavras);
        return mapper.toCacaPalavrasDTO(cacaPalavras);
    }

}

package com.felypeganzert.cacapalavras.services.impl;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.exception.PalavraJaExisteNoCacaPalavrasException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPutDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.PalavraService;
import com.felypeganzert.cacapalavras.utils.AppConstantes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PalavraServiceImpl implements PalavraService {

    private final PalavraRepository repository;
    private final CacaPalavrasRepository repositoryCacaPalavras;
    private final CacaPalavrasService serviceCacaPalavras;

    @Override
    @Transactional
    public Palavra adicionarPalavra(String palavra, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        Palavra p = Palavra.builder().cacaPalavras(cacaPalavras).palavra(palavra).build();

        verificarPalavraJaAdicionaNoCacaPalavras(p, cacaPalavras);

        p = repository.save(p);
        return p;
    }

    private void verificarPalavraJaAdicionaNoCacaPalavras(Palavra palavra, CacaPalavras cacaPalavras) {
        if (isPalavraJaAdicionadaNoCacaPalavras(palavra, cacaPalavras)) {
            throw new PalavraJaExisteNoCacaPalavrasException(palavra.getPalavra());
        }
    }

    private boolean isPalavraJaAdicionadaNoCacaPalavras(Palavra palavra, CacaPalavras cacaPalavras) {
        for (Palavra p : cacaPalavras.getPalavras()) {
            if (isIdDiferente(p, palavra) && isPalavrasIguais(p, palavra)) {
                return true;
            }
        }
        return false;
    }

    private boolean isIdDiferente(Palavra p1, Palavra p2) {
        return p1.getId() != p2.getId();
    }

    private boolean isPalavrasIguais(Palavra p1, Palavra p2) {
        return p1.getPalavra().trim().equalsIgnoreCase(p2.getPalavra().trim());
    }

    @Override
    public List<Palavra> findAll(Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        return cacaPalavras.getPalavras();
    }

    @Override
    public Palavra findById(Integer id, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        return findById(id, cacaPalavras);
    }

    private Palavra findById(Integer id, CacaPalavras cacaPalavras) {
        Palavra palavra = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(AppConstantes.PALAVRA, AppConstantes.ID, id));

        if (palavra.getCacaPalavras().getId() != cacaPalavras.getId()) {
            throw new RecursoNaoPertenceAException(AppConstantes.PALAVRA, AppConstantes.CACA_PALAVRAS);
        }

        return palavra;
    }

    @Override
    @Transactional
    public Palavra atualizar(PalavraPutDTO dto, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        Palavra palavra = findById(dto.getId(), cacaPalavras);

        palavra.setPalavra(dto.getPalavra());
        limparLocalizacoesNoTabuleiro(palavra);

        verificarPalavraJaAdicionaNoCacaPalavras(palavra, cacaPalavras);

        palavra = repository.save(palavra);
        return palavra;
    }

    private void limparLocalizacoesNoTabuleiro(Palavra palavra) {
        palavra.getLocalizacoesNoTabuleiro().clear();
    }

    @Override
    public void delete(Integer id, Integer idCacaPalavras) {
        Palavra palavra = findById(id, idCacaPalavras);
        repository.delete(palavra);
    }

    @Override
    public void deleteAll(Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        cacaPalavras.getPalavras().clear();
        repositoryCacaPalavras.save(cacaPalavras);
    }

    private CacaPalavras findCacaPalavrasById(Integer idCacaPalavras) {
        return serviceCacaPalavras.findById(idCacaPalavras);
    }

}

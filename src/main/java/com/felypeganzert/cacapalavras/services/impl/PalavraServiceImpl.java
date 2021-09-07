package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.PALAVRA;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.exception.PalavraJaExisteNoCacaPalavrasException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.rest.dto.PalavraPutDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.PalavraService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PalavraServiceImpl implements PalavraService {

    private final PalavraRepository repository;
    private final CacaPalavrasService serviceCacaPalavras;

    @Override
    @Transactional
    public Palavra adicionarPalavra(String palavra, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        palavra = limparPalavra(palavra);
        Palavra p = Palavra.builder().cacaPalavras(cacaPalavras).palavra(palavra).build();

        // TODO: Adicionar método para gerar Exception com palavra composta (espaço no meio)
        verificarPalavraJaAdicionaNoCacaPalavras(p, cacaPalavras);

        p = repository.save(p);
        return p;
    }

    protected String limparPalavra(String palavra){
        return palavra.trim();
    }

    private void verificarPalavraJaAdicionaNoCacaPalavras(Palavra palavra, CacaPalavras cacaPalavras) {
        if (isPalavraJaAdicionadaNoCacaPalavras(palavra, cacaPalavras)) {
            throw new PalavraJaExisteNoCacaPalavrasException(palavra.getPalavra());
        }
    }

    private boolean isPalavraJaAdicionadaNoCacaPalavras(Palavra palavra, CacaPalavras cacaPalavras) {
        for (Palavra p : cacaPalavras.getPalavras()) {
            if (isIdDiferente(p, palavra) && isPalavrasIguais(p.getPalavra(), palavra.getPalavra())) {
                return true;
            }
        }
        return false;
    }

    private boolean isIdDiferente(Palavra p1, Palavra p2) {
        return p1.getId() != p2.getId();
    }

    private boolean isPalavrasIguais(String p1, String p2) {
        return p1.trim().equalsIgnoreCase(p2.trim());
    }

    private boolean isPalavrasDiferentes(String p1, String p2) {
        return !isPalavrasIguais(p1, p2);
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
        Palavra palavra = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(PALAVRA, ID, id));

        if (palavra.getCacaPalavras().getId() != cacaPalavras.getId()) {
            throw new RecursoNaoPertenceAException(PALAVRA, CACA_PALAVRAS);
        }

        return palavra;
    }

    @Override
    @Transactional
    public Palavra atualizar(PalavraPutDTO dto, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        Palavra palavra = findById(dto.getId(), cacaPalavras);
        
        String palavraLimpa = limparPalavra(dto.getPalavra());
        if(isPalavrasDiferentes(palavraLimpa, palavra.getPalavra())){
            // TODO: Para realmente ter a exclusão disso é preciso de um método de
            // delete no LocalizaoPalavraNoTabuleiroRepository 
            // TODO: substutir por uma chamada da classe LocalizacaoPalavraNoTabuleiro
            limparLocalizacoes(palavra);
        }
        palavra.setPalavra(palavraLimpa);

        // TODO: Adicionar método para gerar Exception com palavra composta (espaço no meio)
        verificarPalavraJaAdicionaNoCacaPalavras(palavra, cacaPalavras);

        palavra = repository.save(palavra);
        return palavra;
    }

    // TODO: mover os dois métodos para o service se LocalizaoPalavraNoTabuleiro 
    @Override
    public void limparLocalizacoes(List<Palavra> palavras) {
        palavras.forEach(p -> limparLocalizacoes(p));
    }

    @Override
    public void limparLocalizacoes(Palavra palavra) {
        palavra.getLocalizacoesNoTabuleiro().clear();
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer idCacaPalavras) {
        Palavra palavra = findById(id, idCacaPalavras);
        repository.delete(palavra);
    }

    @Override
    @Transactional
    public void deleteAll(Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        repository.deleteAllFromCacaPalavras(cacaPalavras.getId());
    }

    private CacaPalavras findCacaPalavrasById(Integer idCacaPalavras) {
        return serviceCacaPalavras.findById(idCacaPalavras);
    }

}

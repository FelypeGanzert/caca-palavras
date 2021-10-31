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
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.repository.PalavraRepository;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.PalavraService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PalavraServiceImpl implements PalavraService {

    private final PalavraRepository repository;
    private final CacaPalavrasService serviceCacaPalavras;
    private final LocalizacaoPalavraService serviceLocalizacaoPalavra;

    @Override
    @Transactional
    public Palavra adicionarPalavra(String palavra, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        palavra = limparPalavra(palavra);
        Palavra p = Palavra.builder().cacaPalavras(cacaPalavras).palavra(palavra).build();

        verificarSeExisteMaisDeUmaPalavra(palavra);
        verificarPalavraJaAdicionaNoCacaPalavras(p, cacaPalavras);

        p = repository.save(p);
        return p;
    }

    protected String limparPalavra(String palavra){
        return palavra.trim();
    }

    private void verificarSeExisteMaisDeUmaPalavra(String palavra){
        if(palavra.trim().contains(" ")){
            throw new RegraNegocioException("Não é possível adicionar palavras com espaço");
        }
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
        return repository.findAllByCacaPalavrasId(idCacaPalavras);
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
    public Palavra atualizar(String palavraParaAtualizar, Integer id, Integer idCacaPalavras) {
        CacaPalavras cacaPalavras = findCacaPalavrasById(idCacaPalavras);
        Palavra palavra = findById(id, cacaPalavras);
        
        String palavraLimpa = limparPalavra(palavraParaAtualizar);
        if(isPalavrasDiferentes(palavraLimpa, palavra.getPalavra())){
            limparLocalizacoes(palavra);
        }
        palavra.setPalavra(palavraLimpa);

        verificarSeExisteMaisDeUmaPalavra(palavra.getPalavra());
        verificarPalavraJaAdicionaNoCacaPalavras(palavra, cacaPalavras);

        palavra = repository.save(palavra);
        return palavra;
    }

    public void limparLocalizacoes(Palavra palavra) {
        palavra.getLocalizacoes().clear();
        serviceLocalizacaoPalavra.deleteAllFromPalavra(palavra.getId());
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
        repository.deleteAllFromCacaPalavras(idCacaPalavras);
    }

    private CacaPalavras findCacaPalavrasById(Integer idCacaPalavras) {
        return serviceCacaPalavras.findByIdEntity(idCacaPalavras);
    }

}

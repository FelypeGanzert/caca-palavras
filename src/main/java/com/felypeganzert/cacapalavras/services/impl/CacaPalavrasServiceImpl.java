package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.services.impl.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.services.impl.AppConstantes.ID;

import java.time.LocalDateTime;
import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasResolver;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CacaPalavrasServiceImpl implements CacaPalavrasService{

    private final CacaPalavrasResolver resolver;
    private final CacaPalavrasRepository repository;


    @Override
    @Transactional
    public CacaPalavras criarComBasico(CacaPalavrasPostDTO dto) {
        CacaPalavras cacaPalavras = new CacaPalavras();
        cacaPalavras.setDataCriacao(LocalDateTime.now());
        cacaPalavras.setCriador(dto.getCriador());
        cacaPalavras.setTitulo(dto.getTitulo());
        
        repository.save(cacaPalavras);
        return cacaPalavras;
    }

    @Override
    public List<InformacoesBasicasCacaPalavrasDTO> findAllComInformacoesBasicas() {
        return repository.findAllComInformacoesBasicas();
    }

    @Override
    public CacaPalavras findById(Integer id) {
        return repository.findById(id)
                            .orElseThrow(() -> new RecursoNaoEncontradoException (CACA_PALAVRAS, ID, id));
    }

    @Override
    @Transactional
    public void limparLetrasDoTabuleiro(Integer id) {
        CacaPalavras cacaPalavras = findById(id);
        cacaPalavras.getTabuleiro().getLetras().clear();
        limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());

        repository.save(cacaPalavras);
    }

    protected void limparLocalizacoesDasPalavrasNoTabuleiro(List<Palavra> palavras) {
        palavras.forEach(p -> p.getLocalizacoesNoTabuleiro().clear());
    }

    @Override
    @Transactional
    public void encontrarPalavrasNoTabuleiro(Integer id) {
        CacaPalavras cacaPalavras = findById(id);
        
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);
        
        repository.save(cacaPalavras);
    }

    // TODO: mover para o service de Tabuleiro
    @Override
    @Transactional
    public Tabuleiro criarTabuleiroComBasico(CacaPalavras cacaPalavras, TabuleiroPostDTO dto){
        Tabuleiro tabuleiro = new Tabuleiro(dto.getLargura(), dto.getAltura());
        cacaPalavras.setTabuleiro(tabuleiro);

        cacaPalavras = repository.save(cacaPalavras);
        return cacaPalavras.getTabuleiro();
    }

    // TODO: mover para o service de Palavra
    @Override
    public List<Palavra> adicionarPalavras(CacaPalavras cacaPalavras, List<String> palavras) {
        for(String p : palavras){
            if(!isPalavraPresente(cacaPalavras, p)){
                cacaPalavras.getPalavras().add(new Palavra(p));
            }
        }

        cacaPalavras = repository.save(cacaPalavras);
        return cacaPalavras.getPalavras();
    }

    private boolean isPalavraPresente(CacaPalavras cacaPalavras, String palavra){
        for(Palavra p : cacaPalavras.getPalavras()){
            if(p.getPalavra().equalsIgnoreCase(palavra)){
                return true;
            }
        }
        return false;
    }

    
}

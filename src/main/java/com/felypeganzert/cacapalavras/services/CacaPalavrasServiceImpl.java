package com.felypeganzert.cacapalavras.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.repository.CacaPalavrasRepository;
import com.felypeganzert.cacapalavras.rest.dto.CacaPalavrasPostDTO;
import com.felypeganzert.cacapalavras.rest.dto.InformacoesBasicasCacaPalavrasDTO;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;

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
        cacaPalavras.setDataCriacao(LocalDate.now());
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
    public Optional<CacaPalavras> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void encontrarPalavrasNoTabuleiro(CacaPalavras cacaPalavras) {
        resolver.encontrarPalavrasNoTabuleiro(cacaPalavras);   
    }

    @Override
    public void limparLetrasDoTabuleiro(CacaPalavras cacaPalavras) {
        cacaPalavras.getTabuleiro().getLetras().clear();
        limparLocalizacoesDasPalavrasNoTabuleiro(cacaPalavras.getPalavras());
    }

    protected void limparLocalizacoesDasPalavrasNoTabuleiro(List<Palavra> palavras) {
        palavras.forEach(p -> p.getLocalizacoesNoTabuleiro().clear());
    }

    @Override
    @Transactional
    public Tabuleiro criarTabuleiroComBasico(CacaPalavras cacaPalavras, TabuleiroPostDTO dto){
        Tabuleiro tabuleiro = new Tabuleiro(dto.getLargura(), dto.getAltura());
        cacaPalavras.setTabuleiro(tabuleiro);

        cacaPalavras = repository.save(cacaPalavras);
        return cacaPalavras.getTabuleiro();
    }

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

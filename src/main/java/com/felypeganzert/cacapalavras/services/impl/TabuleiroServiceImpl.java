package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.CACA_PALAVRAS;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;

import java.util.List;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;
import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;
import com.felypeganzert.cacapalavras.rest.dto.TabuleiroPostDTO;
import com.felypeganzert.cacapalavras.services.CacaPalavrasService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TabuleiroServiceImpl implements TabuleiroService {
    
    private final TabuleiroRepository repository;
    private final CacaPalavrasService serviceCacaPalavras;

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
        repository.delete(tabuleiro);
    }

     // TODO: mover para o service de letras
    @Override
    @Transactional
    public List<Letra> adicionarLetras(Integer idTabuleiro, List<Letra> letras) {
        Tabuleiro tabuleiro = repository.findById(idTabuleiro)
                                .orElseThrow(() -> 
                                    new IllegalArgumentException("Tabuleiro não encontrado"));

        letras.forEach(letra -> inserirLetra(tabuleiro, letra));
        repository.save(tabuleiro); 
        return tabuleiro.getLetras();       
    }

    // TODO: mover para o service de letras
    @Override
    public void inserirLetra(Tabuleiro tabuleiro, Letra letra) {
        validarPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        limparPosicaoNoTabuleiro(tabuleiro, letra.getPosicao());
        tabuleiro.getLetras().add(letra);
    }

    private void validarPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        if(posicao.getX() > tabuleiro.getLargura() || posicao.getY() > tabuleiro.getAltura()){
            throw new IllegalStateException("Posição desejada para inserir não existe no tabuleiro");
        }
    }

    private void limparPosicaoNoTabuleiro(Tabuleiro tabuleiro, Posicao posicao) {
        tabuleiro.getLetras().removeIf(l -> isLetraNaPosicao(l, posicao));
    }

    private boolean isLetraNaPosicao(Letra letra, Posicao posicao) {
        Posicao posicaoLetra = letra.getPosicao();
        return posicaoLetra.equals(posicao);
    }

}

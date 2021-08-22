package com.felypeganzert.cacapalavras.services;

import java.util.List;
import java.util.Optional;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.repository.TabuleiroRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TabuleiroServiceImpl implements TabuleiroService {
    
    private final TabuleiroRepository repository;

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

    @Override
    public Optional<Tabuleiro> findById(Integer id) {
        return repository.findById(id);
    }

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

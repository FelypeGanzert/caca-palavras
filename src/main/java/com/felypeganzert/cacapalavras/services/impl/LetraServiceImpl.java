package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.LETRA;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.entidades.dto.LetraDTO;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.exception.RegraNegocioException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMapper;
import com.felypeganzert.cacapalavras.repository.LetraRepository;
import com.felypeganzert.cacapalavras.services.LetraService;
import com.felypeganzert.cacapalavras.services.LocalizacaoPalavraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LetraServiceImpl implements LetraService {

    private final LetraRepository repository;
    private final TabuleiroService serviceTabuleiro;
    private final LocalizacaoPalavraService serviceLocalizacaoPalavra;
    private final CacaPalavrasMapper mapper;

    @Override
    @Transactional
    public LetraDTO adicionarLetra(LetraDTO letraDTO, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = toLetra(letraDTO);
        letra.setId(null);
        letra.setTabuleiro(tabuleiro);

        validarPosicaoNoTabuleiro(letra.getPosicao(), tabuleiro);
        
        Letra letraAntigaDaPosicao = tabuleiro.getLetraDaPosicaoOuRetorneNull(letra.getPosicao());
        if (letraAntigaDaPosicao != null) {
            serviceLocalizacaoPalavra.deleteAllUsandoLetra(letraAntigaDaPosicao.getId());
            tabuleiro.getLetras().removeIf(l -> l.getPosicao().equals(letraAntigaDaPosicao.getPosicao()));
            repository.delete(letraAntigaDaPosicao);
        }

        letra = repository.save(letra);
        return mapper.toLetraDTO(letra);
    }

    private void validarPosicaoNoTabuleiro(Posicao posicao, Tabuleiro tabuleiro) {
        if (tabuleiro.posicaoNaoExiste(posicao)) {
            throw new RegraNegocioException("Posição " + posicao.getPosicaoCartesiana() + " não existe no tabuleiro");
        }
    }

    @Override
    @Transactional
    public List<LetraDTO> adicionarLetras(List<LetraDTO> letrasDTO, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);

        List<Posicao> posicoesNaoExistentes = new ArrayList<Posicao>();
        List<Letra> letrasExistentesNasPosicoes = new ArrayList<Letra>();
        List<Letra> letrasParaAdicionar = new ArrayList<Letra>();

        for(LetraDTO dto : letrasDTO){
            Letra l = toLetra(dto);
            l.setId(null);
            l.setTabuleiro(tabuleiro);
            letrasParaAdicionar.add(l);

            if (tabuleiro.posicaoNaoExiste(l.getPosicao())) {
                posicoesNaoExistentes.add(l.getPosicao());
            }
            
            Letra letraExistente = tabuleiro.getLetraDaPosicaoOuRetorneNull(l.getPosicao());
            if (letraExistente != null) {
                letrasExistentesNasPosicoes.add(letraExistente);
            }
        };

        if (!posicoesNaoExistentes.isEmpty()) {
        	throw gerarExceptionDePosicoesNaoExistentesNoTabuleiro(posicoesNaoExistentes);
        }

        if (!letrasExistentesNasPosicoes.isEmpty()) {
            
            List<Integer> idLetrasExistentes = letrasExistentesNasPosicoes.stream()
                                                    .map(l -> l.getId()).collect(Collectors.toList());
            serviceLocalizacaoPalavra.deleteAllUsandoLetras(idLetrasExistentes);

            tabuleiro.getLetras().removeAll(letrasExistentesNasPosicoes);
            repository.deleteAll(letrasExistentesNasPosicoes);
        }

        letrasParaAdicionar = repository.saveAll(letrasParaAdicionar);
        return mapper.toLetrasDTO(letrasParaAdicionar);
    }

    private RegraNegocioException gerarExceptionDePosicoesNaoExistentesNoTabuleiro(List<Posicao> posicoes) {
        String pos = "";
        pos = posicoes.stream().map(p -> p.getPosicaoCartesiana()).collect(Collectors.joining(", "));
        String erro = "As posições [ " + pos + "] não existem no tabuleiro";
        return new RegraNegocioException(erro);
    }

    @Override
    public List<LetraDTO> findAll(Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        return mapper.toLetrasDTO(tabuleiro.getLetras());
    }

    @Override
    public LetraDTO findById(Integer id, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = findById(id, tabuleiro);
        return mapper.toLetraDTO(letra);
    }

    private Letra findById(Integer id, Tabuleiro tabuleiro) {
        Letra letra = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(LETRA, ID, id));
        if (letra.getTabuleiro().getId() != tabuleiro.getId()) {
            throw new RecursoNaoPertenceAException(LETRA, TABULEIRO);
        }
        return letra;
    }

    @Override
    @Transactional
    public LetraDTO atualizar(char letraParaAtualizar, Integer id, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = findById(id, tabuleiro);
        letra.setLetra(letraParaAtualizar);

        serviceLocalizacaoPalavra.deleteAllUsandoLetra(letra.getId());

        letra = repository.save(letra);
        return mapper.toLetraDTO(letra);
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = findById(id, tabuleiro);
        serviceLocalizacaoPalavra.deleteAllUsandoLetra(letra.getId());
        repository.delete(letra);
    }

    @Override
    @Transactional
    public void deleteAll(Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        serviceLocalizacaoPalavra.deleteAllAssociadasAoTabuleiro(tabuleiro.getId());
        repository.deleteAllFromTabuleiro(tabuleiro.getId());
    }

    private Tabuleiro findTabuleiroByIdAndIdCacaPalavras(Integer idTabuleiro, Integer idCacaPalavras) {
        return serviceTabuleiro.findByIdEntity(idTabuleiro, idCacaPalavras);
    }

    private Letra toLetra(LetraDTO dto){
        return Letra.builder()
                .id(dto.getId())
                .letra(dto.getLetra())
                .posicao(new Posicao(dto.getPosicaoX(), dto.getPosicaoY()))
                .build();
    }

}

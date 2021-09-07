package com.felypeganzert.cacapalavras.services.impl;

import static com.felypeganzert.cacapalavras.utils.AppConstantes.ID;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.LETRA;
import static com.felypeganzert.cacapalavras.utils.AppConstantes.TABULEIRO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.felypeganzert.cacapalavras.entidades.Letra;
import com.felypeganzert.cacapalavras.entidades.Palavra;
import com.felypeganzert.cacapalavras.entidades.Posicao;
import com.felypeganzert.cacapalavras.entidades.Tabuleiro;
import com.felypeganzert.cacapalavras.exception.RecursoNaoEncontradoException;
import com.felypeganzert.cacapalavras.exception.RecursoNaoPertenceAException;
import com.felypeganzert.cacapalavras.mapper.CacaPalavrasMaper;
import com.felypeganzert.cacapalavras.repository.LetraRepository;
import com.felypeganzert.cacapalavras.rest.dto.LetraDTO;
import com.felypeganzert.cacapalavras.rest.dto.LetraPostDTO;
import com.felypeganzert.cacapalavras.services.LetraService;
import com.felypeganzert.cacapalavras.services.TabuleiroService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LetraServiceImpl implements LetraService {

    private final LetraRepository repository;
    private final TabuleiroService serviceTabuleiro;
    private final CacaPalavrasMaper mapper;

    @Override
    @Transactional
    public Letra adicionarLetra(LetraPostDTO letraDto, Integer idTabuleiro, Integer idCacaPalavras) {
        Letra letra = mapper.toLetra(letraDto);
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);

        validarPosicaoNoTabuleiro(letra.getPosicao(), tabuleiro);
        deletarLetraAntigaSeExistir(tabuleiro, letra);
        // TODO: não precisa ser limpado as localizações de todas as palavras
        // o mais correto seria limpar somente as posições de palavras encontradas
        // que estavam vinculdas com a letra antiga, Na verdade talvez seja necessário fazer isso antes
        // do método de cima
        limparLocalizacoesDasPalavrasESalvar(tabuleiro);

        letra = repository.save(letra);
        return letra;
    }

    private void validarPosicaoNoTabuleiro(Posicao posicao, Tabuleiro tabuleiro) {
        if (tabuleiro.posicaoNaoExiste(posicao)) {
            throw new IllegalStateException("Posição " + posicao.getPosicaoCartesiana() + " não existe no tabuleiro");
        }
    }

    @Transactional
    private void deletarLetraAntigaSeExistir(Tabuleiro tabuleiro, Letra letra) {
        Letra letraAntiga = tabuleiro.getLetraDaPosicaoOuRetorneNull(letra.getPosicao());
        if (letraAntiga != null) {
            repository.delete(letraAntiga);
        }
    }

    @Override
    @Transactional
    public List<Letra> adicionarLetras(List<LetraPostDTO> letrasDto, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);

        List<Letra> letras = mapper.toLetras(letrasDto);
        List<Posicao> posicoesNaoExistentes = new ArrayList<Posicao>();
        List<Letra> letrasExistentesNasPosicoes = new ArrayList<Letra>();

        letras.forEach(l -> {
            if (tabuleiro.posicaoNaoExiste(l.getPosicao())) {
                posicoesNaoExistentes.add(l.getPosicao());
            }
            Letra letraExistente = tabuleiro.getLetraDaPosicaoOuRetorneNull(l.getPosicao());
            if (letraExistente != null) {
                letrasExistentesNasPosicoes.add(letraExistente);
            }
        });

        if (!posicoesNaoExistentes.isEmpty()) {
            gerarExceptionDePosicoesNaoExistentesNoTabuleiro(posicoesNaoExistentes);
        }

        // TODO: ter método para limpar todas as localizações que estão vinculadas com as letras letrasExistentesNasPosicoes
        limparLocalizacoesDasPalavrasESalvar(tabuleiro);

        if (!letrasExistentesNasPosicoes.isEmpty()) {
            repository.deleteAll(letrasExistentesNasPosicoes);
        }

        letras = repository.saveAll(letras);
        return letras;
    }

    private void gerarExceptionDePosicoesNaoExistentesNoTabuleiro(List<Posicao> posicoes) {
        String pos = "";
        pos = posicoes.stream().map(p -> p.getPosicaoCartesiana()).collect(Collectors.joining(", "));
        String erro = "As posições [ " + pos + "] não existem no tabuleiro";
        throw new IllegalStateException(erro);
    }

    @Override
    public List<Letra> findAll(Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        return tabuleiro.getLetras();
    }

    @Override
    public Letra findById(Integer id, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        return findById(id, tabuleiro);
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
    public Letra atualizar(LetraDTO dto, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = findById(dto.getId(), tabuleiro);

        verificarAlteracaoDaPosicaoParaAtualizar(letra, dto);
        letra.setLetra(dto.getLetra());

        // TODO: método para limpar somente as localizações das palavras que estejam vinculadas a essa letra
        limparLocalizacoesDasPalavrasESalvar(tabuleiro);

        letra = repository.save(letra);
        return letra;
    }

    private void verificarAlteracaoDaPosicaoParaAtualizar(Letra letra, LetraDTO dto) {
        Posicao posicaoDTO = new Posicao(dto.getPosicaoX(), dto.getPosicaoY());
        if (!letra.getPosicao().equals(posicaoDTO)) {
            throw new IllegalStateException("Não é possível atualizar a posição de uma letra.");
        }
    }

    @Transactional
    public void limparLocalizacoesDasPalavrasESalvar(Tabuleiro tabuleiro) {
        List<Palavra> palavras = tabuleiro.getCacaPalavras().getPalavras();
        // TODO: Criar método em LocalizacaoPalavraNoTabuleiro para limparTodas de determinada Palavra
        // servicePalavra.limparLocalizacoes(palavras);
        // palavras = servicePalavra.saveAll(palavras);
        tabuleiro.getCacaPalavras().setPalavras(palavras);
    }

    @Override
    @Transactional
    public void delete(Integer id, Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        Letra letra = findById(id, tabuleiro);
        // TODO: método para limpar somente as localizações vinculadas à essa letra
        limparLocalizacoesDasPalavrasESalvar(tabuleiro);
        repository.delete(letra);
    }

    @Override
    @Transactional
    public void deleteAll(Integer idTabuleiro, Integer idCacaPalavras) {
        Tabuleiro tabuleiro = findTabuleiroByIdAndIdCacaPalavras(idTabuleiro, idCacaPalavras);
        // TODO: método para limpar todas as localizações vinculadas ao tabuleiro.cacaPalavras.id
        limparLocalizacoesDasPalavrasESalvar(tabuleiro);
        repository.deleteAllFromTabuleiro(tabuleiro.getId());
    }

    private Tabuleiro findTabuleiroByIdAndIdCacaPalavras(Integer idTabuleiro, Integer idCacaPalavras) {
        return serviceTabuleiro.findById(idTabuleiro, idCacaPalavras);
    }

}

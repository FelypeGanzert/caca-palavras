package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocalizacaoPalavraNoTabuleiroRepository extends JpaRepository<LocalizacaoPalavraNoTabuleiro, Integer>{
    
    @Modifying
    @Query("DELETE FROM LocalizacaoPalavraNoTabuleiro l WHERE l.palavra.cacaPalavras.tabuleiro.id = :idTabuleiro")
    void deleteAllFromTabuleiroId(Integer idTabuleiro);

    @Modifying
    @Query(" DELETE FROM LocalizacaoPalavraNoTabuleiro locPalavra "
           + " WHERE locPalavra.id IN ( "
           + "      SELECT locPalavra2.id FROM LocalizacaoPalavraNoTabuleiro locPalavra2 "
           + "      INNER JOIN locPalavra2.localizacoesLetrasNoTabuleiro locLetra2 "
           + "      WHERE locLetra2.letra.id = :idLetra "
           + " )")
    void deleteAllUsingLetraId(Integer idLetra);

}

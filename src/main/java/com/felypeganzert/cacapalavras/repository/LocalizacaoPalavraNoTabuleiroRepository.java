package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.LocalizacaoPalavraNoTabuleiro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoPalavraNoTabuleiroRepository extends JpaRepository<LocalizacaoPalavraNoTabuleiro, Long>{
    
}

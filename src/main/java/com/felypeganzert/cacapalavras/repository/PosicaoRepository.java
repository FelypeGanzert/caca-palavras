package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Posicao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicaoRepository extends JpaRepository<Posicao, Long>{
    
}

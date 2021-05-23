package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Tabuleiro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TabuleiroRepository extends JpaRepository<Tabuleiro, Long>{
    
}

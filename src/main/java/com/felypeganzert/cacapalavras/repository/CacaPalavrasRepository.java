package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.CacaPalavras;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CacaPalavrasRepository extends JpaRepository<CacaPalavras, Long>{
    
}

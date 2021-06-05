package com.felypeganzert.cacapalavras.repository;

import com.felypeganzert.cacapalavras.entidades.Palavra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PalavraRepository extends JpaRepository<Palavra, Integer>{
    
}

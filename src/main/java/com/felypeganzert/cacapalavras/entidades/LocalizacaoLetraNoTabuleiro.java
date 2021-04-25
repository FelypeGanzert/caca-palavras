package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_localizacao_letra_tabuleiro")
public class LocalizacaoLetraNoTabuleiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private int ordem;

    @Embedded
    private Posicao posicao;

    public LocalizacaoLetraNoTabuleiro(int ordem, Posicao posicao){
        this.ordem = ordem;
        this.posicao = posicao;
    }

    public LocalizacaoLetraNoTabuleiro(LocalizacaoLetraNoTabuleiro outro){
        this.ordem = outro.getOrdem();
        this.posicao = outro.getPosicao();
    }

}

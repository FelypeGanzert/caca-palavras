package com.felypeganzert.cacapalavras.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_tabuleiro")
class Tabuleiro {

    public static final int ALTURA_MINIMA = 5;
    public static final int LARGURA_MINIMA = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Setter(value = AccessLevel.NONE)
    private int altura;

    @Setter(value = AccessLevel.NONE)
    private int largura;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tabuleiro")
    private List<Letra> letras;

    public Tabuleiro(int altura, int largura) {
        this(null, altura, largura);
    }

    public Tabuleiro(Long id, int altura, int largura) {
        validarDimensoes(altura, largura);
        this.id = id;
        this.altura = altura;
        this.largura = largura;
    }

    private void validarDimensoes(int altura, int largura) {
        if (isAlturaMenorQueAMinima(altura) && isLarguraMenorQueAMinima(largura)) {
            throw new IllegalArgumentException(
                    "Altura e Largura são menores que as mínimas (" + ALTURA_MINIMA + ", " + LARGURA_MINIMA + ")");
        }
        if (isAlturaMenorQueAMinima(altura))
            throw new IllegalArgumentException("A Altura é menor que a mínima (" + ALTURA_MINIMA + ")");
        if (isLarguraMenorQueAMinima(largura))
            throw new IllegalArgumentException("A Largura é menor que a mínima (" + LARGURA_MINIMA + ")");
    }

    private boolean isAlturaMenorQueAMinima(int altura) {
        return altura < ALTURA_MINIMA;
    }

    private boolean isLarguraMenorQueAMinima(int largura) {
        return largura < LARGURA_MINIMA;
    }

}

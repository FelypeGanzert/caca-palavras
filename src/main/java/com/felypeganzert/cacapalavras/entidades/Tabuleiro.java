package com.felypeganzert.cacapalavras.entidades;

import java.util.ArrayList;
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
public class Tabuleiro {

    public static final int LARGURA_MINIMA = 5;
    public static final int ALTURA_MINIMA = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Setter(value = AccessLevel.NONE)
    private int largura;

    @Setter(value = AccessLevel.NONE)
    private int altura;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tabuleiro")
    private List<Letra> letras = new ArrayList<Letra>();

    public Tabuleiro(int largura, int altura) {
        this(null, largura, altura);
    }

    public Tabuleiro(Long id, int largura, int altura) {
        validarDimensoes(largura, altura);
        this.id = id;
        this.largura = largura;
        this.altura = altura;
    }

    private void validarDimensoes(int largura, int altura) {
        if ( isLarguraMenorQueAMinima(largura) && isAlturaMenorQueAMinima(altura)) {
            throw new IllegalArgumentException(
                    "Largura e Altura são menores que as mínimas (" + LARGURA_MINIMA + ", " + ALTURA_MINIMA + ")");
        }
        if (isLarguraMenorQueAMinima(largura))
            throw new IllegalArgumentException("A Largura é menor que a mínima (" + LARGURA_MINIMA + ")");
        if (isAlturaMenorQueAMinima(altura))
            throw new IllegalArgumentException("A Altura é menor que a mínima (" + ALTURA_MINIMA + ")");
    }
    
    private boolean isLarguraMenorQueAMinima(int largura) {
        return largura < LARGURA_MINIMA;
    }

    private boolean isAlturaMenorQueAMinima(int altura) {
        return altura < ALTURA_MINIMA;
    }

    public Letra getLetraDaPosicao(Posicao posicao){
        return letras.stream().filter(l -> isLetraNaPosicao(l, posicao)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Posição (" + posicao.getX() + ", " + posicao.getY() + ") não encontrada no Tabuleiro"));
    }

    private boolean isLetraNaPosicao(Letra letra, Posicao posicao){
        Posicao posicaoLetra = letra.getPosicao();
        return posicaoLetra.getX() == posicao.getX() && posicaoLetra.getY() == posicao.getY();
    }

}

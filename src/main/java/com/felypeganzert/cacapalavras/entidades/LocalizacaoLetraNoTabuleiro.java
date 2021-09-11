package com.felypeganzert.cacapalavras.entidades;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Integer id;

    private int ordem;

    @ManyToOne
    @JoinColumn(name = "id_letra", nullable = false)
    private Letra letra;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_localizacao_palavra_tabuleiro", nullable = false,
                foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY(id_localizacao_palavra_tabuleiro) REFERENCES tb_localizacao_palavra_tabuleiro(ID) ON DELETE CASCADE",
                    value = ConstraintMode.CONSTRAINT
                ))
    LocalizacaoPalavraNoTabuleiro localizacaoPalavraNoTabuleiro;

    public LocalizacaoLetraNoTabuleiro(int ordem, Letra letra) {
        this.ordem = ordem;
        this.letra = letra;
    }

    public LocalizacaoLetraNoTabuleiro(LocalizacaoPalavraNoTabuleiro localizacaoPalavraNoTabuleiro,
            LocalizacaoLetraNoTabuleiro outro) {

        this.localizacaoPalavraNoTabuleiro = localizacaoPalavraNoTabuleiro;
        this.ordem = outro.getOrdem();
        this.letra = outro.getLetra();
    }

    public void removerVinculoComLocalizacaoPalavra() {
        localizacaoPalavraNoTabuleiro.removerVinculoComLocalizacaoLetra(this);
        localizacaoPalavraNoTabuleiro = null;
    }

    @PreRemove
    public void removerVinculos() {
        removerVinculoComLocalizacaoPalavra();
    }

}

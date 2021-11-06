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
@Table(name = "tb_localizacao_letra")
public class LocalizacaoLetra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    
    @EqualsAndHashCode.Include
    private int ordem;

    @ManyToOne
    @JoinColumn(name = "id_letra", nullable = false)
    @EqualsAndHashCode.Include
    private Letra letra;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "id_localizacao_palavra", nullable = false,
                foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY(id_localizacao_palavra) REFERENCES tb_localizacao_palavra(ID) ON DELETE CASCADE",
                    value = ConstraintMode.CONSTRAINT
                ))
    LocalizacaoPalavra localizacaoPalavra;

    public LocalizacaoLetra(int ordem, Letra letra) {
        this.ordem = ordem;
        this.letra = letra;
    }

    public LocalizacaoLetra(LocalizacaoPalavra localizacaoPalavra, LocalizacaoLetra outro) {
        this.localizacaoPalavra = localizacaoPalavra;
        this.ordem = outro.getOrdem();
        this.letra = outro.getLetra();
    }

    public void removerVinculoComLocalizacaoPalavra() {
        localizacaoPalavra.removerVinculoComLocalizacaoLetra(this);
        localizacaoPalavra = null;
    }

    @PreRemove
    public void removerVinculos() {
        removerVinculoComLocalizacaoPalavra();
    }

}

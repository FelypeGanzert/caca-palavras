package com.felypeganzert.cacapalavras.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecursoNaoEncontradoException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	private String nomeRecurso;
	private String nomeCampo;
	private Object valorCampo;

	public RecursoNaoEncontradoException(String nomeRecurso, String nomeCampo, Object valorCampo) {
		super();
		this.nomeRecurso = nomeRecurso;
		this.nomeCampo = nomeCampo;
		this.valorCampo = valorCampo;
	}

	public String getMessage(){
		return String.format("%s não encontrado com %s: '%s'", nomeRecurso, nomeCampo, valorCampo);
	}

}
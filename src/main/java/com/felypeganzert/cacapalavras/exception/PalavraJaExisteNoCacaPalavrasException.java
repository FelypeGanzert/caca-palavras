package com.felypeganzert.cacapalavras.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PalavraJaExisteNoCacaPalavrasException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	private String palavra;

	public PalavraJaExisteNoCacaPalavrasException(String palavra) {
		super();
		this.palavra = palavra;
	}

	public String getMessage(){
		return String.format("Já existe uma Palavra '%s' adicionada no Caça Palavras'", palavra);
	}

}
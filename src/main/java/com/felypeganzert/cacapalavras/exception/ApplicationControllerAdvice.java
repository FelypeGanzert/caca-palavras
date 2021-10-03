package com.felypeganzert.cacapalavras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleRegraNegocioException(RegraNegocioException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return apiError;
	}
    
    @ExceptionHandler(PalavraJaExisteNoCacaPalavrasException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handlePalavraJaExisteNoCacaPalavrasException(PalavraJaExisteNoCacaPalavrasException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return apiError;
	}

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleRecursoNaoEncontradoException(RecursoNaoEncontradoException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return apiError;
	}

    @ExceptionHandler(RecursoNaoPertenceAException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleRecursoNaoPertenceAException(RecursoNaoPertenceAException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return apiError;
	}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            apiError.getErros()
                .add(fieldError.getDefaultMessage());
        }
        return apiError;
    }
    
}

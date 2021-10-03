package com.felypeganzert.cacapalavras.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

// import static

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felypeganzert.cacapalavras.exception.ApiError;

import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseBodyMatchers {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher contemObjetoComoJson(Object expectedObject, Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(actualObject).usingRecursiveComparison().isEqualTo(expectedObject);
        };
    }

    public ResultMatcher contemErro(String erroEsperado) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            ApiError apiError = objectMapper.readValue(json, ApiError.class);
            List<String> erros = apiError.getErros().stream().filter(erro -> erro.equals(erroEsperado))
                    .collect(Collectors.toList());

            assertThat(erros).hasSize(1).withFailMessage("Era esperado o erro '%s'", erroEsperado);
        };
    }

    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }

}

package com.felypeganzert.cacapalavras.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        .select()
                .apis(RequestHandlerSelectors.basePackage("com.felypeganzert.cacapalavras"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(metaInfo())
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
                .globalResponseMessage(RequestMethod.POST, responseMessageForPOST())
                .globalResponseMessage(RequestMethod.PUT, responseMessageForPUT())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageForDELETE());
    }

    private ApiInfo metaInfo() {

        return new ApiInfoBuilder()
                    .title("Caça Palavras API REST")
                    .description("API REST para solucionar Caça Palavras")
                    .version("1.0.0")
                    .license("Apache License Version 2.0")
                    .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                    .contact(new Contact("Felype Ganzert", "https://www.felypeganzert.com", "felypeganzert@gmail.com"))
                    .build();
    }

    private List<ResponseMessage> responseMessageForGET(){
        return new ArrayList<ResponseMessage>() {{
                add(responseOK());
                add(responseNOT_FOUND());
                add(responseINTERNAL_SERVER_ERROR());
                add(responseBAD_REQUEST());
            }};
    }

    private List<ResponseMessage> responseMessageForPOST(){
        return new ArrayList<ResponseMessage>() {{
                add(responseOK());
                add(responseCREATED());
                add(responseINTERNAL_SERVER_ERROR());
                add(responseBAD_REQUEST());
            }};
    }

    private List<ResponseMessage> responseMessageForPUT(){
        return new ArrayList<ResponseMessage>() {{
                add(responseOK());
                add(responseNOT_FOUND());
                add(responseINTERNAL_SERVER_ERROR());
                add(responseBAD_REQUEST());
            }};
    }

    private List<ResponseMessage> responseMessageForDELETE(){
        return new ArrayList<ResponseMessage>() {{
                add(responseOK());
                add(responseNOT_FOUND());
                add(responseNO_CONTENT());
                add(responseINTERNAL_SERVER_ERROR());
                add(responseBAD_REQUEST());
            }};
    }

    private ResponseMessage responseOK(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.OK.value())
                    .message("OK")
                    .build();
    }

    private ResponseMessage responseCREATED(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.CREATED.value())
                    .message("Recurso criado")
                    .build();
    }
    private ResponseMessage responseNO_CONTENT(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.NO_CONTENT.value())
                    .message("Recurso deletado")
                    .build();
    }

    private ResponseMessage responseNOT_FOUND(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Recurso não encontrado")
                    .build();
    }

    private ResponseMessage responseINTERNAL_SERVER_ERROR(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Foi gerada uma exceção")
                    .build();
    }

    private ResponseMessage responseBAD_REQUEST(){
        return new ResponseMessageBuilder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Bad Request")
                    .build();
    }

}

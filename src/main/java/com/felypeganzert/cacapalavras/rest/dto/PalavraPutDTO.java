package com.felypeganzert.cacapalavras.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PalavraPutDTO {

    private int id;
    private String palavra;
   
}

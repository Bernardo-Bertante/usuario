package com.agendatarefa.usuario.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    private String numero;
    private String ddd;
}

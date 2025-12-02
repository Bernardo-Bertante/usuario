package com.agendatarefa.usuario.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;
}

package com.agendatarefa.usuario.business;

import com.agendatarefa.usuario.business.converter.UsuarioConverter;
import com.agendatarefa.usuario.dto.UsuarioDTO;
import com.agendatarefa.usuario.infrastructure.entity.Usuario;
import com.agendatarefa.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
}

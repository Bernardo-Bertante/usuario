package com.agendatarefa.usuario.business;

import com.agendatarefa.usuario.business.converter.UsuarioConverter;
import com.agendatarefa.usuario.dto.UsuarioDTO;
import com.agendatarefa.usuario.infrastructure.entity.Usuario;
import com.agendatarefa.usuario.infrastructure.exceptions.ConflictException;
import com.agendatarefa.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendatarefa.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificarEmailExiste(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificarEmailExiste(String email) {
        return  usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email)  {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

}

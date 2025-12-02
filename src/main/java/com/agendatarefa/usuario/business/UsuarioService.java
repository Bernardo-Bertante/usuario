package com.agendatarefa.usuario.business;

import com.agendatarefa.usuario.business.converter.UsuarioConverter;
import com.agendatarefa.usuario.dto.EnderecoDTO;
import com.agendatarefa.usuario.dto.TelefoneDTO;
import com.agendatarefa.usuario.dto.UsuarioDTO;
import com.agendatarefa.usuario.infrastructure.entity.Endereco;
import com.agendatarefa.usuario.infrastructure.entity.Telefone;
import com.agendatarefa.usuario.infrastructure.entity.Usuario;
import com.agendatarefa.usuario.infrastructure.exceptions.ConflictException;
import com.agendatarefa.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.agendatarefa.usuario.infrastructure.repository.EnderecoRepository;
import com.agendatarefa.usuario.infrastructure.repository.TelefoneRepository;
import com.agendatarefa.usuario.infrastructure.repository.UsuarioRepository;
import com.agendatarefa.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email)  {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuarioAtual = usuarioRepository.findByEmail(email).orElseThrow( () ->
                new ResourceNotFoundException("Email não encontrado")
        );

        Usuario usuario = usuarioConverter.updateUsuario(usuarioAtual, usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizarEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(entity, enderecoDTO);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(entity, telefoneDTO);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastrarEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));

        Endereco enderecoEntity = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(enderecoEntity));

    }

    public TelefoneDTO cadastrarTelefone(String token, TelefoneDTO telefoneDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado " + email));

        Telefone telefoneEntity = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefoneEntity));
    }

}

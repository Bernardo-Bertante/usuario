package com.agendatarefa.usuario.business.converter;

import com.agendatarefa.usuario.dto.EnderecoDTO;
import com.agendatarefa.usuario.dto.TelefoneDTO;
import com.agendatarefa.usuario.dto.UsuarioDTO;
import com.agendatarefa.usuario.infrastructure.entity.Endereco;
import com.agendatarefa.usuario.infrastructure.entity.Telefone;
import com.agendatarefa.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ? paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ? paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ? paraListaEnderecoDTO(usuario.getEnderecos()) : null)
                .telefones(usuario.getTelefones() != null ? paraListaTelefoneDTO(usuario.getTelefones()) : null)
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco) {
        return endereco.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario(Usuario entity, UsuarioDTO usuario) {
        return Usuario.builder()
                .nome(usuario.getNome() != null ? usuario.getNome() : entity.getNome())
                .email(usuario.getEmail() != null ? usuario.getEmail() : entity.getEmail())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : entity.getSenha())
                .telefones(entity.getTelefones())
                .enderecos(entity.getEnderecos())
                .build();
    }

    public Endereco updateEndereco(Endereco entity, EnderecoDTO endereco) {
        return Endereco.builder()
                .id(entity.getId())
                .numero(endereco.getNumero() != null ? endereco.getNumero() : entity.getNumero())
                .estado(endereco.getEstado() != null ? endereco.getEstado() : entity.getEstado())
                .complemento(endereco.getComplemento() != null ? endereco.getComplemento() : entity.getComplemento())
                .rua(endereco.getRua() != null ? endereco.getRua() : entity.getRua())
                .cidade(endereco.getCidade() != null ? endereco.getCidade() : entity.getCidade())
                .cep(endereco.getCep() != null ? endereco.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(Telefone entity, TelefoneDTO telefone) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(telefone.getDdd() != null ? telefone.getDdd() : entity.getDdd())
                .numero(telefone.getNumero() != null ? telefone.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario) {
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .idUsuario(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario) {
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .idUsuario(idUsuario)
                .build();
    }
}

package com.agendatarefa.usuario.controller;

import com.agendatarefa.usuario.business.UsuarioService;
import com.agendatarefa.usuario.dto.EnderecoDTO;
import com.agendatarefa.usuario.dto.TelefoneDTO;
import com.agendatarefa.usuario.dto.UsuarioDTO;
import com.agendatarefa.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                         @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                         @RequestParam Long id) {
        return ResponseEntity.ok(usuarioService.atualizarTelefone(id, telefoneDTO));
    }
}

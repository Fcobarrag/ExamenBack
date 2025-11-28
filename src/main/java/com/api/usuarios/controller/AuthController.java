package com.api.usuarios.controller;

import com.api.usuarios.security.JwtUtil;
import com.api.usuarios.entity.Usuario;
import com.api.usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "email y password son requeridos"));
        }

        if (usuarioService.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "email ya registrado"));
        }

        Usuario u = new Usuario(nombre == null ? "" : nombre, email, passwordEncoder.encode(password));
        usuarioService.guardar(u);
        return ResponseEntity.ok(Map.of("message", "usuario creado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || email.isBlank() || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email y password son requeridos"));
        }

        var opt = usuarioService.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "credenciales incorrectas"));
        }
        Usuario u = opt.get();
        if (!passwordEncoder.matches(password, u.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "credenciales incorrectas"));
        }

        String token = jwtUtil.generateToken(u.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
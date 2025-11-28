package com.api.usuarios.service;

import com.api.usuarios.entity.Usuario;
import com.api.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario guardar(Usuario u) {
        u.setFechaRegistro(LocalDateTime.now());
        return repo.save(u);
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Optional<Usuario> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Usuario actualizar(Long id, Usuario nuevo) {
        return repo.findById(id).map(u -> {
            u.setNombre(nuevo.getNombre());
            u.setEmail(nuevo.getEmail());
            return repo.save(u);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
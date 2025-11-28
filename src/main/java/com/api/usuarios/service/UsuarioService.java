package com.api.usuarios.service;

import com.api.usuarios.entity.Usuario;
import com.api.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Usuario crear(Usuario u) {
        u.setFechaRegistro(LocalDateTime.now());
        return repo.save(u);
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

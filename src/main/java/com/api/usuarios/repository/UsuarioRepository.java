package com.api.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.usuarios.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}

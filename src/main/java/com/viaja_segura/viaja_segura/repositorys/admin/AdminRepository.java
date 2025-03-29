package com.viaja_segura.viaja_segura.repositorys.admin;

import com.viaja_segura.viaja_segura.models.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findAdminById(Long id);
}

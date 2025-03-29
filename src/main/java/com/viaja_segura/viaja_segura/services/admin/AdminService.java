package com.viaja_segura.viaja_segura.services.admin;

import com.viaja_segura.viaja_segura.dtos.admin.AdminDto;
import com.viaja_segura.viaja_segura.exception.EmailAlreadyExistsException;
import com.viaja_segura.viaja_segura.models.admin.Admin;
import com.viaja_segura.viaja_segura.repositorys.admin.AdminRepository;
import com.viaja_segura.viaja_segura.utils.EmailService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public Admin register(AdminDto dto) {
        if (repo.existsByEmail(dto.email)) {
            throw new EmailAlreadyExistsException("Ya existe un usuario con el correo: " + dto.email);
        }

        Admin admin = new Admin();
        admin.setName(dto.name);
        admin.setLastName(dto.lastName);
        admin.setBirthDate(dto.birthDate);
        admin.setSex(dto.sex);
        admin.setCurp(dto.curp);
        admin.setMunicipality(dto.municipality);
        admin.setCity(dto.city);
        admin.setEmail(dto.email);
        admin.setPassword(passwordEncoder.encode(dto.password));
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());

        Admin newAdmin = repo.save(admin);


        emailService.sendUserRegistrationEmail(
                newAdmin.getEmail(),
                newAdmin.getName(),
                newAdmin.getLastName(),
                newAdmin.getEmail(),
                "ADMIN"
        );

        return newAdmin;
    }

    @Transactional(readOnly = true)
    public Response<List<Admin>> findAll() {
        List<Admin> data = repo.findAll();
        return new Response<>(data,
                false,
                200,
                "Ok");
    }
}

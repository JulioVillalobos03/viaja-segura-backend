package com.viaja_segura.viaja_segura.services.passenger;

import com.viaja_segura.viaja_segura.dtos.passenger.PassengerDto;
import com.viaja_segura.viaja_segura.models.passenger.Passenger;
import com.viaja_segura.viaja_segura.repositorys.passenger.PassengerRepository;
import com.viaja_segura.viaja_segura.utils.EmailService;
import com.viaja_segura.viaja_segura.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PassengerService {
    @Autowired
    private PassengerRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public Passenger register(PassengerDto dto) {
        if (repo.existsByEmail(dto.email)) {
            throw new RuntimeException("Email ya registrado");
        }

        Passenger passenger = new Passenger();
        passenger.setName(dto.name);
        passenger.setLastName(dto.lastName);
        passenger.setBirthDate(dto.birthDate);
        passenger.setSex(dto.sex);
        passenger.setCurp(dto.curp);
        passenger.setMunicipality(dto.municipality);
        passenger.setCity(dto.city);
        passenger.setEmail(dto.email);
        passenger.setPhone(dto.phone);
        passenger.setPassword(passwordEncoder.encode(dto.password));
        passenger.setCreatedAt(LocalDateTime.now());
        passenger.setUpdatedAt(LocalDateTime.now());

        Passenger saved = repo.save(passenger);

        emailService.sendUserRegistrationEmail(
                saved.getEmail(),
                saved.getName(),
                saved.getLastName(),
                saved.getEmail(),
                "PASSENGER"
        );

        return saved;
    }

    @Transactional(readOnly = true)
    public Response<List<Passenger>> findAll() {
        List<Passenger> data = repo.findAll();
        return new Response<>(data,
                false,
                200,
                "Ok");
    }
}

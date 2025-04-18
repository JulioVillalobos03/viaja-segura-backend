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
    @Transactional(readOnly = true)
    public Passenger findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado con ID: " + id));
    }

    @Transactional
    public Passenger updatePassengerInfo(Long id, PassengerDto dto) {
        Passenger passenger = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado con ID: " + id));

        if (dto.name != null) passenger.setName(dto.name);
        if (dto.lastName != null) passenger.setLastName(dto.lastName);
        if (dto.birthDate != null) passenger.setBirthDate(dto.birthDate);
        if (dto.sex != null) passenger.setSex(dto.sex);
        if (dto.curp != null) passenger.setCurp(dto.curp);
        if (dto.municipality != null) passenger.setMunicipality(dto.municipality);
        if (dto.city != null) passenger.setCity(dto.city);
        if (dto.phone != null) passenger.setPhone(dto.phone);
        passenger.setUpdatedAt(LocalDateTime.now());

        return repo.save(passenger);
    }

}

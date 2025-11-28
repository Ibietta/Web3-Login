package com.example.multipagos.service;

import com.example.multipagos.dto.AssignUserDto;
import com.example.multipagos.model.*;
import com.example.multipagos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private UserCompanyRepository userCompanyRepo;

    public void assignUser(AssignUserDto dto) {

        User user = userRepo.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Company company = companyRepo.findById(dto.companyId)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Role role;
        try {
            role = Role.valueOf(dto.role.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Rol inválido. Use: ADMIN, USER, PROVIDER");
        }

        // Verificar si ya existe
        if (userCompanyRepo.existsByUserIdAndCompanyId(user.getId(), company.getId())) {
            throw new RuntimeException("El usuario ya pertenece a esta empresa");
        }

        // Crear relación
        UserCompany uc = new UserCompany();
        uc.setUser(user);
        uc.setCompany(company);
        uc.setRole(role);

        userCompanyRepo.save(uc);
    }
}

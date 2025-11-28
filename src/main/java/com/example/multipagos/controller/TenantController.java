package com.example.multipagos.controller;

import com.example.multipagos.dto.AssignUserDto;
import com.example.multipagos.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping("/assign")
    public String assignUserToCompany(@RequestBody AssignUserDto dto) {
        tenantService.assignUser(dto);
        return "Usuario asignado correctamente";
    }
}

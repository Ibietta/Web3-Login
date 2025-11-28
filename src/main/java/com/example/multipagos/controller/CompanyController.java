package com.example.multipagos.controller;

import com.example.multipagos.tenant.TenantResolver;
import com.example.multipagos.util.RequestCompanyResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.example.multipagos.model.Company;
import com.example.multipagos.service.CompanyService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    private RequestCompanyResolver resolver;
    private TenantResolver tenantResolver;

    @GetMapping
    public List<Company> all() { return companyService.listAll(); }

    @PostMapping
    public Company create(@RequestBody Company c) { return companyService.create(c); }

    @GetMapping("/{id}")
    public Company getCompany(
            @PathVariable Long id,
            HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Long companyActive = tenantResolver.resolveCompany(request, auth);

        // Validar que la empresa solicitada ES la empresa activa
        if (!id.equals(companyActive)) {
            throw new RuntimeException("No tienes permiso para ver esta empresa.");
        }
        return companyService.findById(id);
    }

    @GetMapping("/companies/{id}")
    public Company getCompany(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long companyId = resolver.getActiveCompanyId(request, auth);

        return companyService.findById(companyId);
    }
}

package com.example.multipagos.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class RequestCompanyResolver {

    public Long getActiveCompanyId(HttpServletRequest request, Authentication auth) {
        String header = request.getHeader("X-Company-ID");

        if (header == null)
            throw new RuntimeException("Debe enviar X-Company-ID en la cabecera.");

        Long companyHeader = Long.parseLong(header);

        List<Long> allowedCompanies =
                (List<Long>) ((Map<String,Object>) auth.getDetails()).get("companyIds");

        if (!allowedCompanies.contains(companyHeader)) {
            throw new RuntimeException("No tienes acceso a esta empresa.");
        }

        return companyHeader;
    }
}


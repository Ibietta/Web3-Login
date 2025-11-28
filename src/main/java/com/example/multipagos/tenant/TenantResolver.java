package com.example.multipagos.tenant;

import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import java.util.*;

@Component
public class TenantResolver {

    public Long resolveCompany(HttpServletRequest request, Authentication auth) {

        String header = request.getHeader("X-Company-ID");

        if (header == null)
            throw new RuntimeException("Debe enviar X-Company-ID en la cabecera.");

        Long companyId = Long.parseLong(header);

        // Recuperar companyIds del token
        List<Long> allowedCompanies =
                (List<Long>) ((Map<String, Object>) auth.getDetails()).get("companyIds");

        if (!allowedCompanies.contains(companyId)) {
            throw new RuntimeException("No tienes acceso a esta empresa.");
        }

        return companyId;
    }
}

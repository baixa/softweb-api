package com.softweb.api.store.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SoftWeb Store API",
                description = "The API provides access to and control of the SoftWeb application " +
                        "store module. Convenient functionality will allow developers to promote " +
                        "their software product, and ordinary users to get up-to-date software without " +
                        "any hassle. This API is intended for developers. If you want to use the " +
                        "application - use the actual client (see information on the " +
                        "website https://github.com/baixa/softweb-api)",
                contact = @Contact(
                        name = "Author",
                        url = "https://github.com/baixa",
                        email = "baixa2003@gmail.com"
                ),
                license = @License(
                        name = "MPL 2.0",
                        url = "https://github.com/baixa/softweb-api/blob/prod/LICENSE.md")),
        servers = @Server(url = "http://localhost:8072/store")
)
@SecurityScheme(
        name = "api",
        scheme = "basic",
        type = SecuritySchemeType.HTTP)
public class OpenApiConfig {
}

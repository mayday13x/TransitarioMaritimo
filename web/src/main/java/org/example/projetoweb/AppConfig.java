package org.example.projetoweb;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "pt.ipvc.database.repository")
@ComponentScan(basePackages = "pt.ipvc.database")

public class AppConfig {
}

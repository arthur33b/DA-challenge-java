package com.alura.dashboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuração do MongoDB para o projeto
 * Habilita auditoria e repositórios MongoDB
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.alura.dashboard.repository.mongodb")
@EnableMongoAuditing
public class MongoConfig {
    
    // Configurações adicionais podem ser adicionadas aqui
    // Como conversores customizados, validators, etc.
}

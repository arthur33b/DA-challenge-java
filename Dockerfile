# Etapa 1: Build da aplicação (caso queira compilar dentro do container)

FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime - imagem final leve
FROM eclipse-temurin:17-jdk-alpine

# Cria um usuário sem privilégios administrativos
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copia o JAR para dentro da imagem
COPY target/alura-dashboard-1.0.0.jar app.jar

# Permite acesso do usuário sem root
USER appuser

# Expõe a porta da aplicação
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
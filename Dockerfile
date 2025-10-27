# BUILD
# Começa com uma imagem que tem o JDK 21 e o Maven
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache do Docker
COPY pom.xml .

# Baixa todas as dependências do projeto (sem compilar nada ainda)
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

# PROD
# Começa com uma imagem base bem menor, apenas com o JRE
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho para a aplicação final
WORKDIR /app

# Copia APENAS o arquivo .jar que foi gerado no estágio 'builder'
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que sua aplicação Spring Boot usa (padrão 8080)
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
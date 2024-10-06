# Usar la imagen de Maven con OpenJDK 17
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar el archivo pom.xml
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean install -DskipTests

# Usar nuevamente la imagen de Maven con OpenJDK 17 para el entorno de ejecución
FROM maven:3.8.4-openjdk-17-slim
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación Spring Boot
CMD ["mvn", "spring-boot:run"]

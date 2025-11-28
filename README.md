# Spring Boot API - Usuarios

Proyecto ejemplo con:
- Spring Boot 3
- Spring Data JPA
- MySQL
- Validaciones
- JWT básico (demo)

## Pasos para ejecutar

1. Asegúrate de tener Java 17 y Maven instalados.
2. Crea la base de datos MySQL:
   ```sql
   CREATE DATABASE usuariosdb;
   ```
3. Ajusta `src/main/resources/application.properties` con tu usuario/clave MySQL.
4. Desde la carpeta del proyecto:
   ```
   mvn clean package
   mvn spring-boot:run
   ```
5. Endpoints:
   - `POST /auth/login` -> body: { "email": "juan@example.com" }  (devuelve token)
   - `GET /api/usuarios` (protegido)
   - `POST /api/usuarios` (protegido)

Nota: el login demo no valida contraseña; para producción agrega usuarios y contraseñas seguras.

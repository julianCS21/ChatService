# Prueba Técnica: Desarrollo de un Chat Punto a Punto en Java



sistema de chat punto a punto en Java que permita a dos usuarios comunicarse entre sí en tiempo real


## Instalación 

clone el repositorio

---

    git clone https://github.com/julianCS21/ChatService.git

---


dirigase a la carpeta raiz del proyecto


---

    cd ChatService


---

## Compilar


ejecute el comando

---

    mvn clean install

---


## Despliegue


ejecute el comando en Windows

---

    mvn spring-boot:run

  
---


## Pruebas

Para ejecutar las pruebas, puedes usar el siguiente comando:

---

    mvn test

  
---

## Documentación de la API: Servicio de Chat
URL Base

https://chat-service-demo.azurewebsites.net/api/v1

# Endpoints


1. Crear Usuario

   
URL: /user

Método: POST

Descripción: Este endpoint permite crear un nuevo usuario en el sistema.

Cuerpo de la solicitud (Request Body):

```
{
    "username": "nicolas",
    "email" : "nicolas@example.com",
    "password" : "123456789",
    "sentMessages" : [],
    "recievedMessages" : []
}
```
Respuesta (Response):

En caso de éxito, devuelve la información del usuario creado.


2. ver lista de usuarios
   
URL : /user

Método : GET



3. Iniciar Sesión (Autenticación)
URL: /auth/login

Método: POST

Descripción: Autentica al usuario utilizando su correo electrónico y contraseña, devolviendo un JWT (Token de autenticación) necesario para las solicitudes protegidas.

Cuerpo de la solicitud (Request Body):

```
{
    "email" :"nicolas@example.com",
    "password" : "123456789"
}
```


Respuesta (Response):

En caso de éxito, devuelve un JWT en el header que se usará para autenticación en solicitudes futuras.

4. Enviar Mensaje
URL: /messages/send

Método: POST

Descripción: Envía un mensaje a otro usuario autenticado utilizando el JWT del destinatario.

Cuerpo de la solicitud (Request Body):

```
{
    "recipientJwt" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJuaWNvbGFzQGV4YW1wbGUuY29tIiwiaXNzIjoidG9saXN0bzIwMjQiLCJpYXQiOjE3MjgxODY4MzQsImV4cCI6MTcyOTQ4MjgzNH0.kBe2bFZQ_9uE-jUoP1-Hd-esPikBYHmj5AKbqcJZ2r8",
    "content" : "holaa"
}
```


Respuesta (Response):

Devuelve una confirmación de que el mensaje fue enviado exitosamente.

# Autenticación.

Es fundamental utilizar el token JWT proporcionado al iniciar sesión para acceder a los endpoints que requieran autenticación. El token debe ser incluido en el encabezado de autorización:


Consideraciones
Es importante gestionar los errores de autenticación, como respuestas no autorizadas (401), cuando el JWT es inválido o ha expirado.



## Autor

Julian David Castillo Soto

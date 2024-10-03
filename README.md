# Prueba Técnica: Desarrollo de un Chat Punto a Punto en Java



sistema de chat punto a punto en Java que permita a dos usuarios comunicarse entre sí en tiempo real


## Instalacion 

clone el repositorio

---

    git clone https://github.com/julianCS21/ChatService.git

---


dirigase a la carpeta raiz del proyecto


---

    cd AREPParcial02


---

## Compilar


ejecute el comando

---

    mvn clean install

---


## Despliegue


ejecute el comando en Windows

---

    java -cp "target/classes;target/dependency/*" org.example.CollaztService  
  
---

ejecute el comando en Linux o Mac

---

    java -cp "target/classes:target/dependency/*" org.example.CollaztService  

---



![image](https://github.com/julianCS21/AREPParcial02/assets/96396177/b9cafe0d-dce0-4835-a20b-b32168839763)


## Correrlo en EC2


una vez creada la instancia en EC2 ejecute el los comandos 


---

    sudo yum update -y
    sudo yum install docker

---

Inicie el servicio de docker

---

    sudo service docker start
    
---

Configure su usuario en el grupo de docker para no tener que ingresar “sudo” cada vez que invoca un comando
---

    sudo usermod -a -G docker ec2-user
    
---

Desconectes de la máquina virtual e ingrese nuevamente para que la configuración de grupos de usuarios tenga efecto.
A partir de la imagen creada en Dockerhub cree una instancia de un contenedor docker independiente de la consola (opción “-d”) y con el puerto 6000 enlazado a un puerto físico de su máquina (opción -p):


---

    docker run -d -p 42000:6000 --name firstdockerimageaws juliancs21/arep2

---


Ahora verifique con el dns de su maquina


---

    http://DNSdesumaquina:42000/index.html

---





![image](https://github.com/julianCS21/AREPParcial02/assets/96396177/a5a89646-5d26-4f72-a0b4-d7ddc55729c2)


## video prueba


https://www.youtube.com/watch?v=ybKG5lV1xnI


## Autor

Julian David Castillo Soto

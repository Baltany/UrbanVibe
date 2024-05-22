# Introducción

## Datos del proyecto

<!-- How to do a table in md? -->
| Nombre | Apellidos | Título | Ciclo | Año | Centro educativo |
| --- | --- | --- | --- | --- | --- |
| Balbino | Moyano López | Desarrollo de Aplicaciones Multimedia | Superior | 2024 | IES Virgen del Carmen |


## Planificación

Esta sería aproximadamente la planificación del proyecto:

* 1º Semana 
  * En la primera semana de trabajo he esquematizado todo el proyecto,he buscado otras ideas de proyecto,además de hacer un diagrama UML de lo que a mi me gustaría que fuera finalmente este proyecto.
  * He dedicidido hacer el proyecto en spring debido a que he notado un importante interés en esta tecnología por las empresas que actualmente buscan desarrolladores de este estilo.
* 2º Semana 
  * Título
  * Objetivos iniciales (convertimos los deseos o características en requisitos)
  * Breve resumen del mismo (esta semana debe estar subido al repositorio en nuestro GitLab)
  * Investigación de estudios y proyectos similares (lo que llamamos “estado del arte”)
* 3º Semana
  * Investigación de estudios y proyectos similares  (plasmarlo en la documentación)
  * Introducción (generar este apartado en la documentación)
  * Objetivos definitivos (generar este apartado en la documentación), exactamente qué estamos haciendo
  * Material y recursos a utilizar (recoger detalladamente todos los recursos que se disponen y/o necesitarán en la documentación)
* 4º Semana 
  * Métodos seguidos en el proceso, metodologías, tecnologías (ej. porqué usar un lenguaje o framework concreto y no otro)
* 5º Semana 
  * Resultados iniciales (primeros “bocetos” del programa)
  * Analizar si hace falta cambiar algo de los requisitos o tecnologías inicialmente planificadas y explicar si hay algún cambio porqué se ha hecho. Esto se plasma en la documentación
* 6º Semana 
  * Resultados intermedios (demo funcional)
  * Primera revisión de la documentación para ver que estén todos los puntos necesarios 
* 7º Semana
  * Resultados finales (proyecto terminado: tutorial, aplicación…) 
  * Segunda revisión del documento donde ya estén todos los apartados necesarios 
  * Preparación de la presentación 
* 8º Semana 
  * Pulimos los posibles “bugs”
  * Entrega del documento final 
* 9º Semana   
  * Organización de la presentación
  * Entrega de la presentación para la exposición 
* 10º Semana  
  * Presentación del proyecto

## Ejecución del proyecto
Para ejecutar el proyecto realizado en spring primeramente necesitaremos haber montado previamente docker y haberlo ejecutado,para ello deberiamos de irnos a la carpeta donde se aloja nuestro docker-compose.yml y deberemos de ejecutar el siguiente comando:
```docker
docker-compose -f nombre_del_archivo.yml up -d
```
Con este comando se nos ejecutará en los puertos que nosotros tengamos predefinidos en el archivo.yml el adminer,es decir, nuestra gestor de la base datos,para ver nuestra base de datos en ejecución entonces necesitaremos ejecutar spring que para ello podemos hacerlo de varias maneras,o desde visual studio con la extensión de spring que hay un botón de ejecutar o directamente con el comando:
```spring
mvn spring-boot:run
```
Ejecutaremos el proyecto en el puerto por defecto que usa spring el 8080,si quisieramos cambiar el puerto por defecto de spring,lo deberemos de hacer en el archivo aplication.properties y poner algo talque así:
```application.properties
server.port=7070
```
He puesto el puerto 7070,pero se podría poner otro sin problemas siempre y cuando no esté ocupado


## Organización del proyecto
La organización del proyecto está compuesta por las siguientes carpetas:
- docs: aquí se alojan todas las fotos o documentos de interés que necesitemos.
- src:
  - main:
    - conf:aquí se alojan todo tipo de configuraciones relacionadas con la seguridad,login,register....
    - controller:aquí se alojan todo tipo de controladores con el que manejamos su crud completo de nuestras clases modelo
    - model: nuestras clases entidad y las que le dan sentido al proyecto
    - repo: se alojan todas las interfaces necesarias 
    - service: aquí pondremos si fuera necesario los servicios de nuestras clases modelo
    - ShopApplication.java: Es el archivo principal de nuestro proyecto, sin él,no ejecutaría
  - resources: se encuentra el codigo de basicamente todo el frontend que uso:
    - static: aquí se encuentra todas las imagenes,etc.
    - application.properties: se encuentran las contraseñas necesarias para que se conecte con la base de datos,no se debería de subir a git por motivos obvios.
    - import.sql: se encuentra todos los insert si queremos añadir algun dato en alguna tabla directamente en la base de datos.
    - templates: aquí se encuentran todas las plantillas.
      - users: en el encontramos los archivos:
        - users.html: muestra todos los usuarios,si tienes los requisitos suficientes para verlos(Admin),además de poder borrar y editar los usuarios.
        - add.html: muestra el formulario para añadir un usuario,si tienes los requisitos suficientes para verlos(Admin).
        - edit.html: muestra el formulario para editar un usuario,si tienes los requisitos suficientes para verlos(Admin).
      - help.html: al igual que acerca,es totalmente estético.
      - denegado.html: es un archivo el cual indica a un usuario sino tiene permisos pues lo redirigimos a la siguiente página.
      - error.html: en caso de que exista algún tipo de error en nuestra página lo redirigimos a está que resulta más cómodo para el cliente ver que hay un error y que tiene que contactar con soporte.
      - index.html: es la página a la que redirigimos por defecto una vez hecho el login.
      - login.html: es la página por defecto de nuestro login.
      - signup.html: es la página para hacer un registro.
- stack: aquí se encuentran los archivos:
  - docker-compose.yml: este archivo es nuestro contenedor y dentro montamos las imagenes que necesitamos y el motor de base de datos que usamos
  - .env: aquí encontramos las contraseñas de nuestro docker-compose
  - setup.sql: es un archivo que sirve para inicializar la base de datos con el nombre que nosotros queramos ponerle a nuestra base de datos.
- pom.xml: es nuestro archivo de dependecias,sin él, el proyecto sería inservible



## Esquema de la base de datos
Aquí podemos ver un breve esquema de los que sería nuestras clases modelo una vez ya creadas en mysql:  
![Esquema de la base de datos](docs\image.png)
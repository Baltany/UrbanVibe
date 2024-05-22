# Diario de trabajo 2º semana
En esta semana me he lanzado de lleno a organizar el proyecto por carpetas y he intentado hacerme una vista final de como quedará el proyecto,también ahora mismo estoy documentando en español pero en un futuro,todo el readme completo y terminado lo haré en inglés.
Además ya he empezado un poco programando y ya estoy usando gitlab para tener un backup del proyecto

He creado la base de datos a partir de mis clases modelo,de manera automática gracias a spring,para ellos he usado en mi archivo properties;
```
spring.datasource.url=jdbc:mysql://localhost:33306/tienda
spring.jpa.hibernate.ddl-auto=create-drop
```

y añadir en el pom.xml la siguiente linea:
```xml
<dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.28</version>
</dependency>
```
Con esto y un correcto uso de nuestros modelos con **@JoinColumn(name = "something_id"),@ManyToOne,@OneToMany,@NoArgsContructor,@Data,@Entity...**
podremos generar una base datos de manera automática.Como esta:
```sql
-- Adminer 4.8.1 MySQL 8.0.32 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE `tienda` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tienda`;

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `clothes` (
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5htjf3gb0c7wmpwapjd6y5je1` (`order_id`),
  CONSTRAINT `FK5htjf3gb0c7wmpwapjd6y5je1` FOREIGN KEY (`order_id`) REFERENCES `purchase_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `clothes_category_list` (
  `category_list_id` bigint NOT NULL,
  `clothes_id` bigint NOT NULL,
  KEY `FKoil6lha2g64nqh26875d47305` (`category_list_id`),
  KEY `FKs0blo9tv55gm7duur3wd0k069` (`clothes_id`),
  CONSTRAINT `FKoil6lha2g64nqh26875d47305` FOREIGN KEY (`category_list_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKs0blo9tv55gm7duur3wd0k069` FOREIGN KEY (`clothes_id`) REFERENCES `clothes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `clothes_size_list` (
  `clothes_id` bigint NOT NULL,
  `size_list_id` bigint NOT NULL,
  KEY `FKktms7kds63n3o0d6afkvymf2f` (`size_list_id`),
  KEY `FKnq50jvnfbdogjwn8e085olslo` (`clothes_id`),
  CONSTRAINT `FKktms7kds63n3o0d6afkvymf2f` FOREIGN KEY (`size_list_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FKnq50jvnfbdogjwn8e085olslo` FOREIGN KEY (`clothes_id`) REFERENCES `clothes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `purchase_order` (
  `total_price` double NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `user_order_id` bigint DEFAULT NULL,
  `order_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3va304qsr99gqm7fpce6vb4l9` (`user_order_id`),
  KEY `FK5j4jnq1w6e2ged6evsvmgmx8b` (`user_id`),
  CONSTRAINT `FK3va304qsr99gqm7fpce6vb4l9` FOREIGN KEY (`user_order_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK5j4jnq1w6e2ged6evsvmgmx8b` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `size` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `size` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rol` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_rol_list` (
  `rol_list_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FK2dl4m03dr096h0ikfysvm4aeo` (`rol_list_id`),
  KEY `FKi3hsw8ybr7sowam60r7cdwm0n` (`user_id`),
  CONSTRAINT `FK2dl4m03dr096h0ikfysvm4aeo` FOREIGN KEY (`rol_list_id`) REFERENCES `user_rol` (`id`),
  CONSTRAINT `FKi3hsw8ybr7sowam60r7cdwm0n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2024-03-28 17:36:31

```
Puede ser que finalmente,se cambie pero por ahora y de manera provisional mi base de datos es así.
-- Insertar datos en la tabla 'size' (no tiene dependencias)
INSERT INTO `size` (`size`) VALUES ('XXS');
INSERT INTO `size` (`size`) VALUES ('XS');
INSERT INTO `size` (`size`) VALUES ('S');
INSERT INTO `size` (`size`) VALUES ('M');
INSERT INTO `size` (`size`) VALUES ('L');
INSERT INTO `size` (`size`) VALUES ('XL');
INSERT INTO `size` (`size`) VALUES ('XXL');
INSERT INTO `size` (`size`) VALUES ('TALLA UNICA');

--Insertar ropa por sexo
INSERT INTO `sex` (`sex`) VALUES ('UNISEX');
INSERT INTO `sex` (`sex`) VALUES ('MEN');
INSERT INTO `sex` (`sex`) VALUES ('WOMEN');



-- Insertar datos en la tabla 'user' (no tiene dependencias)
INSERT INTO `user` (`name`, `username`, `surname`, `mail`, `password`, `address`, `dni`) VALUES 
('John', 'john123', 'Doe', 'john@example.com', 'password123', '123 Street, City', '12345678A');
INSERT INTO `user` (`name`, `username`, `surname`, `mail`, `password`, `address`, `dni`) VALUES 
('Alice', 'alice456', 'Smith', 'alice@example.com', 'password456', '456 Avenue, Town', '87654321B');

-- Insertar datos en la tabla 'user_rol_list' (depende de 'user_rol' y 'user')
-- Insertar datos en la tabla 'user_rol_list'
INSERT INTO `user_rol_list` (`role_list_id`, `user_id`) VALUES ((SELECT `id` FROM `user_rol` WHERE `rol` = 'Admin'), (SELECT `id` FROM `user` WHERE `username` = 'john123'));
INSERT INTO `user_rol_list` (`role_list_id`, `user_id`) VALUES ((SELECT `id` FROM `user_rol` WHERE `rol` = 'Customer'), (SELECT `id` FROM `user` WHERE `username` = 'alice456'));

-- Insertar datos en la tabla 'category' (no tiene dependencias)
INSERT INTO `category` (`category`) VALUES ('Shirts');
INSERT INTO `category` (`category`) VALUES ('Pants');
INSERT INTO `category` (`category`) VALUES ('Dresses');

-- Insertar datos en la tabla 'clothes' (depende de 'purchase_order')
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (19.99, 50, 'Winter Hoddie', 'Multicolor', '/img/sudaderaSolo.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (29.99, 30, 'Jumper', 'Blue', '/img/sudaderaMasVibe.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (39.99, 20, 'Summer Dress', 'Black&White', '/img/conjuntoFemVibe.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (40.95, 20, 'UrbanVibe T-shirt', 'Black', '/img/T-shirtVibe.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (45.00, 20, 'Trouser', 'Black', '/img/PantalonVibe.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (39.95, 10, 'Hoddie', 'Black', '/img/sudaderaFemVibe.jpg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (10, 10, 'Iphone XR Phonecase', 'Black', '/img/PhoneCaseVibe.jpeg');
INSERT INTO `clothes` (`price`, `stock`, `description`, `color`, `image`) VALUES (39.95, 10, 'Jeans', 'Black', '/img/PantalonBlancoVibe.jpeg');

-- Insertar lista de ropa por sexo
INSERT INTO `clothes_sex_list` (`clothes_id`, `sex_list_id`) VALUES ('1', '1');
INSERT INTO `clothes_sex_list` (`clothes_id`, `sex_list_id`) VALUES ('2', '2');
INSERT INTO `clothes_sex_list` (`clothes_id`, `sex_list_id`) VALUES ('3', '3');


-- Insertar datos en la tabla 'purchase_order' (depende de 'user')
INSERT INTO `purchase_order` (`total_price`, `user_purchase_id`, `order_date`) VALUES (59.98, 1, '2024-04-02');
INSERT INTO `purchase_order` (`total_price`, `user_purchase_id`, `order_date`) VALUES (39.99, 2, '2024-04-02');

-- Insertar datos en la tabla 'clothes_category_list' (depende de 'category' y 'clothes')
INSERT INTO `clothes_category_list` (`category_list_id`, `clothes_id`) VALUES (1, 1);
INSERT INTO `clothes_category_list` (`category_list_id`, `clothes_id`) VALUES (2, 2);
INSERT INTO `clothes_category_list` (`category_list_id`, `clothes_id`) VALUES (3, 3);

-- Insertar datos en la tabla 'clothes_size_list' (depende de 'size' y 'clothes')
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 1);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 2);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 3);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 4);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 5);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (1, 6);

INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (2, 1);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (2, 2);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (2, 3);

INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (3, 1);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (3, 2);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (3, 3);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (4, 3);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (5, 2);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (6, 7);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (7, 8);
INSERT INTO `clothes_size_list` (`clothes_id`, `size_list_id`) VALUES (8, 5);


-- -- Insertar datos en la tabla 'user'
INSERT INTO `user` (`name`, `username`, `surname`, `mail`, `password`, `address`, `dni`,`enable`) VALUES ('John', 'john123', 'Doe', 'john@example.com', '$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', '123 Street, City', '12345678A',1);

INSERT INTO `user` (`name`, `username`, `surname`, `mail`, `password`, `address`, `dni`,`enable`) VALUES ('Alice', 'alice456', 'Smith', 'alice@example.com', '$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', '456 Avenue, Town', '87654321B',1);

INSERT INTO `user` (`name`, `username`, `surname`, `mail`, `password`, `address`, `dni`,`enable`) VALUES ('Balbino', 'balbino', 'Moyano', 'bmoylop0903@g.educaand.es', '$2a$10$PMDCjYqXJxGsVlnve1t9Jug2DkDDckvUDl8.vF4Dc6yg0FMjovsXO', 'Calle montoro 6', '50624970s',1);


-- Insertar datos en la tabla 'purchase_order'
INSERT INTO `purchase_order` (`user_id`, `order_date`, `total_price`) VALUES ((SELECT `id` FROM `user` WHERE `username` = 'john123'), '2024-04-02', 59.98);
INSERT INTO `purchase_order` (`user_id`, `order_date`, `total_price`) VALUES ((SELECT `id` FROM `user` WHERE `username` = 'alice456'), '2024-04-02', 39.99);

-- Insertar datos en la tabla 'user_rol'
INSERT INTO `user_rol` (`rol`) VALUES ('Admin');
INSERT INTO `user_rol` (`rol`) VALUES ('Customer');

-- Insertar datos en la tabla 'user_rol_list'
-- Insertar datos en la tabla 'user_rol_list' (depende de 'user_rol' y 'user')
INSERT INTO `user_rol_list` (`rol_list_id`, `user_id`) VALUES ((SELECT `id` FROM `user_rol` WHERE `rol` = 'Admin'), (SELECT `id` FROM `user` WHERE `username` = 'john123'));
INSERT INTO `user_rol_list` (`rol_list_id`, `user_id`) VALUES ((SELECT `id` FROM `user_rol` WHERE `rol` = 'Customer'), (SELECT `id` FROM `user` WHERE `username` = 'alice456'));

-- SQL INSERT USER
INSERT INTO TB_USERS (id, first_name, last_name, email, birthday, login, password, phone, created_at, last_login) VALUES
(101, 'João', 'Silva', 'joao.silva@email.com', '1985-08-15', 'joao123', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '1234567890', '2024-11-24', NULL),
(202, 'Maria', 'Oliveira', 'maria.oliveira@email.com', '1990-04-22', 'maria456', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '9876543210', '2024-11-24', NULL),
(303, 'Carlos', 'Pereira', 'carlos.pereira@email.com', '1982-11-12', 'carlos789', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '1122334455', '2024-11-24', NULL),
(404, 'Ana', 'Souza', 'ana.souza@email.com', '1995-03-30', 'anasouza', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '9988776655', '2024-11-24', NULL),
(505, 'Lucas', 'Mendes', 'lucas.mendes@email.com', '1988-07-15', 'lucas88', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '5566778899', '2024-11-24', NULL),
(606, 'Fernanda', 'Costa', 'fernanda.costa@email.com', '1993-01-18', 'fernandac', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '6677889900', '2024-11-24', NULL),
(707, 'Pedro', 'Lima', 'pedro.lima@email.com', '1980-06-10', 'pedrol', '$2a$10$PsaleAJ1Te3II0SdlJZ6UedcBcWD4L6w7Epgjz/AT9cOXuutbUqyK', '7788990011', '2024-11-24', NULL);

-- SQL INSERT CAR
INSERT INTO TB_CARS (id, created_at, year_manufacture, color, license_plate, model, user_id) VALUES
(101, '2024-11-24', 1999, 'Prata', 'ABC-1222', 'Monza', 101),
(202, '2024-11-24', 1988, 'Azul', 'ABC-6354', 'Fusca', 202),
(303, '2024-11-24', 2000, 'Branco', 'ABC-0546', 'Focus', 303),
(404, '2024-11-24', 1990, 'Vermelho', 'ABC-3900', 'Kadett', 404),
(505, '2024-11-24', 2001, 'Preto', 'ABC-4012', 'Corsa', 505);

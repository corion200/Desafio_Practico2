-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- 2. Crear tabla CATEGORIA
CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL
);

-- 3. Crear tabla AUTOR
CREATE TABLE autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    nacionalidad VARCHAR(100)
);

-- 4. Crear tabla LIBRO
CREATE TABLE libro (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    año_publicacion INT,
    id_autor INT,
    id_categoria INT,
    FOREIGN KEY (id_autor) REFERENCES autor(id_autor),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- 5. Insertar 5 Categorías
INSERT INTO categoria (nombre_categoria) VALUES 
('Novela'), ('Ciencia Ficción'), ('Historia'), ('Terror'), ('Fantasía');

-- 6. Insertar 10 Autores
INSERT INTO autor (nombre, nacionalidad) VALUES 
('Gabriel García Márquez', 'Colombiana'),
('Isaac Asimov', 'Rusa'),
('Stephen King', 'Estadounidense'),
('J.R.R. Tolkien', 'Británica'),
('Isabel Allende', 'Chilena'),
('George Orwell', 'Británica'),
('Julio Verne', 'Francesa'),
('Agatha Christie', 'Británica'),
('H.P. Lovecraft', 'Estadounidense'),
('Frank Herbert', 'Estadounidense');

-- 7. Insertar 30 Libros
INSERT INTO libro (titulo, año_publicacion, id_autor, id_categoria) VALUES 
('Cien años de soledad', 1967, 1, 1),
('El amor en los tiempos del cólera', 1985, 1, 1),
('Crónica de una muerte anunciada', 1981, 1, 1),
('Fundación', 1951, 2, 2),
('Fundación e Imperio', 1952, 2, 2),
('Yo, Robot', 1950, 2, 2),
('El resplandor', 1977, 3, 4),
('It (Eso)', 1986, 3, 4),
('Carrie', 1974, 3, 4),
('Misery', 1987, 3, 4),
('El Hobbit', 1937, 4, 5),
('La Comunidad del Anillo', 1954, 4, 5),
('Las dos torres', 1954, 4, 5),
('El retorno del Rey', 1955, 4, 5),
('La casa de los espíritus', 1982, 5, 1),
('Eva Luna', 1987, 5, 1),
('1984', 1949, 6, 2),
('Rebelión en la granja', 1945, 6, 1),
('Viaje al centro de la Tierra', 1864, 7, 2),
('Veinte mil leguas de viaje submarino', 1870, 7, 2),
('Asesinato en el Orient Express', 1934, 8, 1),
('Muerte en el Nilo', 1937, 8, 1),
('Diez negritos', 1939, 8, 1),
('La llamada de Cthulhu', 1928, 9, 4),
('En las montañas de la locura', 1936, 9, 4),
('Dune', 1965, 10, 2),
('Mesías de Dune', 1969, 10, 2),
('Hijos de Dune', 1976, 10, 2),
('El coronel no tiene quien le escriba', 1961, 1, 1),
('Doctor Sueño', 2013, 3, 4);

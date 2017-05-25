-- phpMyAdmin SQL Dump
-- version 4.6.5.1deb3+deb.cihar.com~precise.1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Май 25 2017 г., 13:34
-- Версия сервера: 5.5.40-0ubuntu0.12.04.1
-- Версия PHP: 7.1.4-1+deb.sury.org~precise+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `mapyou`
--

-- --------------------------------------------------------

--
-- Структура таблицы `buildings`
--

CREATE TABLE `buildings` (
  `id` varchar(64) NOT NULL,
  `current_load` int(11) NOT NULL,
  `max_load` int(11) NOT NULL,
  `points` polygon NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `buildings`
--

INSERT INTO `buildings` (`id`, `current_load`, `max_load`, `points`) VALUES
('6e3f3587d4e8ac929fd7559e27867eec17f306ff', 9, 50, '\\0\\0\\0\\0\\0\\0\\0\\0\\0\\0\\r\\0\\0\\0�M��M@9�j�3�=@\\Z����M@CF�7�=@2;�ީ�M@:u�<�=@����M@ByG�=@a5���M@IIC�=@?n�|��M@#��fF�=@�c���M@�@-�=@ND���M@�뉮�=@C�=��M@y�|�=@���_��M@�9z��=@�ܘ���M@�7��=@�S����M@������=@�M��M@9�j�3�=@'),
('950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', 3, 10, '\\0\\0\\0\\0\\0\\0\\0\\0\\0\\0[\\0\\0\\0�\\na5��M@�[[%�=@��:���M@���2��=@,-#���M@�	���=@�L���M@�F�q��=@�C�ͩ�M@�?3��=@+�)��M@#���=@h͏���M@\\�-��=@E+���M@@�z���=@�]M���M@��9��=@�0����M@c`��=@G��1��M@<�ן��=@6\\䞮�M@>	l���=@[�[!��M@����=@��Ye��M@EKO��=@VE�ɨ�M@䠄���=@���M@ER���=@�� v��M@���w��=@�e3���M@�����=@Gv�e��M@^�Y-��=@�6���M@B$C���=@l�f��M@y;�i��=@�<,Ԛ�M@�U�0��=@����M@k�w���=@���{��M@+~���=@��r0��M@f�?��=@��鲘�M@PQ�+��=@��>��M@DkE��=@����M@S!���=@�-���M@�K�\'�=@s�蜟�M@�:���=@���ם�M@c�0��=@Y�&��M@͏����=@�ߢ���M@�x@ٔ�=@Cr2q��M@�#����=@���`��M@9�⪲�=@)�*���M@o)狽�=@R\\r�=��M@8h���=@����M@��_��=@��,��M@�Ԗ:��=@`���M@�f���=@�*l��M@�����=@8�0��M@������=@��C��M@n�HJz�=@p	�?��M@��ڦx�=@/N|���M@�h��=@���ާ�M@@KW���=@��z���M@7�A`��=@�+����M@/�����=@#��E��M@�N���=@4/��w�M@�e���=@K#f�y�M@�э���=@��唀�M@��:���=@\"�{��M@nP����=@HS=��M@��\\n���=@0e���M@�f�v��=@B��M@�mQf��=@w�����M@zVҊo�=@�\\n�+��M@fO�s�=@�����M@��\\4d�=@�d�z��M@�	h\"l�=@��×��M@�#)�a�=@r7�֊�M@�x�@e�=@kF���M@���Z�=@:ZՒ��M@Rf`�=@��,���M@wi�ai�=@j�q���M@�3��k�=@p���M@�%��s�=@�~K��M@�+��y�=@(��я�M@�0C��=@ߦ?���M@�B���=@W?6ɏ�M@�j{��=@/�e���M@�l#��=@���6��M@@�ի�=@�<dʇ�M@��\\7��=@�wD��M@�fd���=@l�6��M@%����=@MJA���M@��>���=@E�*k��M@�1���=@����M@��l���=@)@̘�M@����=@����M@����=@ZI+���M@��Đ��=@w.����M@��3���=@�?8��M@�Z`���=@{�/L��M@�)H�=@�¼Ǚ�M@�٭e2�=@�#���M@�`TR\'�=@4��ؙ�M@üǙ&�=@�Z(��M@��L�*�=@˟o��M@�lu9%�=@�\\na5��M@�[[%�=@');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `last_building` varchar(64) NOT NULL,
  `position` point NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `last_building`, `position`) VALUES
('103657285', '', '\\0\\0\\0\\0\\0\\0\\0H�9���M@�Dׅ�=@'),
('11191', '', '\\0\\0\\0\\0\\0\\0\\0���R��M@�����=@'),
('31745215', '950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', '\\0\\0\\0\\0\\0\\0\\0FE�N��M@Iڍ>��=@'),
('333349672', '950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', '\\0\\0\\0\\0\\0\\0\\0ۤ���M@�����=@'),
('a16d723geqf', '', '\\0\\0\\0\\0\\0\\0\\0�.���M@�����=@');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `buildings`
--
ALTER TABLE `buildings`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

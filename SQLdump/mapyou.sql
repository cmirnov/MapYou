-- phpMyAdmin SQL Dump
-- version 4.6.5.1deb3+deb.cihar.com~precise.1
-- https://www.phpmyadmin.net/
--
-- Ð¥Ð¾ÑÑ‚: localhost
-- Ð’Ñ€ÐµÐ¼Ñ ÑÐ¾Ð·Ð´Ð°Ð½Ð¸Ñ: ÐœÐ°Ð¹ 25 2017 Ð³., 13:34
-- Ð’ÐµÑ€ÑÐ¸Ñ ÑÐµÑ€Ð²ÐµÑ€Ð°: 5.5.40-0ubuntu0.12.04.1
-- Ð’ÐµÑ€ÑÐ¸Ñ PHP: 7.1.4-1+deb.sury.org~precise+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Ð‘Ð°Ð·Ð° Ð´Ð°Ð½Ð½Ñ‹Ñ…: `mapyou`
--

-- --------------------------------------------------------

--
-- Ð¡Ñ‚Ñ€ÑƒÐºÑ‚ÑƒÑ€Ð° Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `buildings`
--

CREATE TABLE `buildings` (
  `id` varchar(64) NOT NULL,
  `current_load` int(11) NOT NULL,
  `max_load` int(11) NOT NULL,
  `points` polygon NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Ð”Ð°Ð¼Ð¿ Ð´Ð°Ð½Ð½Ñ‹Ñ… Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `buildings`
--

INSERT INTO `buildings` (`id`, `current_load`, `max_load`, `points`) VALUES
('6e3f3587d4e8ac929fd7559e27867eec17f306ff', 9, 50, '\\0\\0\\0\\0\\0\\0\\0\\0\\0\\0\\r\\0\\0\\0¶Mñ¸¨ðM@9ðj¹3Ó=@\\Zß—ªðM@CF—7Ó=@2;‹Þ©ðM@:uå³<Ó=@¤ý°ðM@ByGÓ=@a5–°ðM@IICÓ=@?n¿|²ðM@#žìfFÓ=@¢c•¸ðM@«@-Ó=@ND¿¶ðM@§ë‰®Ó=@C=·ðM@yÌ|Ó=@“üˆ_±ðM@‚9züÒ=@ÛÜ˜ž°ðM@ô7¡Ó=@ÖS«¯®ðM@ïâý¸ýÒ=@¶Mñ¸¨ðM@9ðj¹3Ó=@'),
('950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', 3, 10, '\\0\\0\\0\\0\\0\\0\\0\\0\\0\\0[\\0\\0\\0Ý\\na5–ðM@Õ[[%Ô=@Ñ:ªšðM@—åë2üÓ=@,-#õžðM@«	¢îÔ=@äL¶ŸðM@„F°qýÓ=@‘CÄÍ©ðM@È?3ˆÔ=@+¿)¬ðM@#¯ëÔ=@hÍ¿´ðM@\\-ËÓ=@E+÷³ðM@@öz÷ÇÓ=@]Mž²ðM@Öÿ9ÌÓ=@”0Óö¯ðM@c`ÇÓ=@G“‹1°ðM@<¡×ŸÄÓ=@6\\äž®ðM@>	lÎÁÓ=@[š[!¬ðM@²ºÕÓ=@ìöYe¦ðM@EKOËÓ=@VE¸É¨ðM@ä „™¶Ó=@ò³‘ë¦ðM@ER·³Ó=@î v¦ðM@£±öw¶Ó=@ˆe3‡¤ðM@™Ÿš²Ó=@Gv¥e¤ðM@^ÕY-°Ó=@Ö6Åã¢ðM@B$CŽ­Ó=@lèf ðM@y;ÂiÁÓ=@Ÿ<,ÔšðM@ŠUƒ0·Ó=@÷™ðM@kšwœ¢Ó=@æèñ{›ðM@+~©ŸÓ=@“Žr0›ðM@fÝ?¢Ó=@¸Ìé²˜ðM@PQõ+Ó=@š³>å˜ðM@DkE›Ó=@Öß—ðM@S!‰—Ó=@¥-®ñ™ðM@ËKþ\'Ó=@sÙèœŸðM@›:ŠÓ=@€»ì×ðM@cð0í›Ó=@Yá&£ðM@Í¿´¨Ó=@“ß¢“¥ðM@–x@Ù”Ó=@Cr2q«ðM@ê#ð‡ŸÓ=@ýù¶`©ðM@9—âª²Ó=@)®*û®ðM@o)ç‹½Ó=@R\\rû=±ðM@8h°©Ó=@ Š·ðM@Œ½_´Ó=@ý,µðM@ÃÔ–:ÈÓ=@`ºðM@ùf›ÓÓ=@Ì*l¸ðM@²ô¡êÓ=@8©0¶ðM@®Ÿþ³æÓ=@«ÌC¦ðM@nùHJzÔ=@p	À?¥ðM@¼ÊÚ¦xÔ=@/N|µ£ðM@Íh†Ô=@ŒºÖÞ§ðM@@KW°Ô=@ÁªzùðM@7‰A`åÔ=@à+ºõšðM@/ßú°ÞÔ=@#ƒÜE˜ðM@ôNÜóÔ=@4/‡ÝwðM@²eùºÔ=@K#föyðM@åÑ°¨Ô=@²Õå”€ðM@†æ:´Ô=@\"á{ƒðM@nPû­Ô=@HS=™ðM@¡Ø\\nš–Ô=@0eà€ðM@fv‡Ô=@B–ðM@±mQfƒÔ=@w£ù€ðM@zVÒŠoÔ=@þ\\n™+ƒðM@fO›sÔ=@žÎ¥„ðM@ßþ\\4dÔ=@ØdzˆðM@Õ	h\"lÔ=@„ÖÃ—‰ðM@æ#)éaÔ=@r7ˆÖŠðM@êxÌ@eÔ=@kF¹‹ðM@–Í’ZÔ=@:ZÕ’ŽðM@Rf`Ô=@Ï×,—ðM@wiÃaiÔ=@jÞqŠŽðM@±3…ÎkÔ=@pÏó§ðM@‰%åîsÔ=@È~K‘ðM@ª+ŸåyÔ=@(»™ÑðM@×0Cã‰Ô=@ß¦?û‘ðM@áBÁÔ=@W?6ÉðM@×j{¡Ô=@/àe†ðM@Ól#žÔ=@ ‡Ú6ŒðM@@ÜÕ«Ô=@À<dÊ‡ðM@ \\7¥Ô=@ÿwD…ðM@²fd»Ô=@l—6–ðM@%ÍÓÚÔ=@MJA·—ðM@‹Ý>«ÌÔ=@Eñ*k›ðM@ì1‘ÒÔ=@÷™ðM@õÚl¬ÄÔ=@)@Ì˜ðM@éŒ¼Ô=@÷™ðM@Æ‚”Ô=@ZI+¾¡ðM@ÂÞÄœÔ=@w.Œô¢ðM@¤¨3÷Ô=@¯?8ŸðM@´Z`‰Ô=@{ƒ/L¦ðM@í)HÔ=@”Â¼Ç™ðM@ÂÙ­e2Ô=@#›ðM@ª`TR\'Ô=@4ºƒØ™ðM@Ã¼Ç™&Ô=@’Z(™ðM@‘œLÜ*Ô=@ËŸo–ðM@”lu9%Ô=@Ý\\na5–ðM@Õ[[%Ô=@');

-- --------------------------------------------------------

--
-- Ð¡Ñ‚Ñ€ÑƒÐºÑ‚ÑƒÑ€Ð° Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `users`
--

CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `last_building` varchar(64) NOT NULL,
  `position` point NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Ð”Ð°Ð¼Ð¿ Ð´Ð°Ð½Ð½Ñ‹Ñ… Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `users`
--

INSERT INTO `users` (`id`, `last_building`, `position`) VALUES
('103657285', '', '\\0\\0\\0\\0\\0\\0\\0HÞ9”¡ðM@öD×…Ô=@'),
('11191', '', '\\0\\0\\0\\0\\0\\0\\0ÛÂóR±íM@ºïäß=@'),
('31745215', '950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', '\\0\\0\\0\\0\\0\\0\\0FEœN²íM@IÚ>æß=@'),
('333349672', '950513d9b6bb814940fdcdbad68cf1c11f1bbf2c', '\\0\\0\\0\\0\\0\\0\\0Û¤¢±ðM@ µ‘¡Ô=@'),
('a16d723geqf', '', '\\0\\0\\0\\0\\0\\0\\0”.«°íM@ºïäß=@');

--
-- Ð˜Ð½Ð´ÐµÐºÑÑ‹ ÑÐ¾Ñ…Ñ€Ð°Ð½Ñ‘Ð½Ð½Ñ‹Ñ… Ñ‚Ð°Ð±Ð»Ð¸Ñ†
--

--
-- Ð˜Ð½Ð´ÐµÐºÑÑ‹ Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `buildings`
--
ALTER TABLE `buildings`
  ADD PRIMARY KEY (`id`);

--
-- Ð˜Ð½Ð´ÐµÐºÑÑ‹ Ñ‚Ð°Ð±Ð»Ð¸Ñ†Ñ‹ `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

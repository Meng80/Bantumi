# **Juego de Bantumi**
Bantumi es un juego de mesa tradicional africano con reglas sencillas pero estratégicas. En esta aplicación, hemos implementado el juego de Bantumi junto con algunas características adicionales.

## **Reglas del juego**
Las reglas del juego de Bantumi son las siguientes:

* El tablero de juego consta de 14 hoyos, divididos en dos jugadores: Jugador 1 y Jugador 2.
* Cada jugador tiene 6 hoyos y un almacén.
* Al comienzo, se colocan 4 semillas en cada hoyo, y los almacenes están vacíos.
* Los jugadores se turnan para jugar. En cada turno, un jugador elige un hoyo no vacío y distribuye las semillas de ese hoyo en los hoyos sucesivos en sentido antihorario.
* Si la última semilla cae en el almacén del jugador, el jugador obtiene un turno adicional.
* Si la última semilla cae en un hoyo propio vacío, el jugador recoge las semillas en el hoyo opuesto del oponente.
* El juego termina cuando todos los hoyos de un jugador están vacíos. En ese momento, el jugador contrario recoge todas las semillas de los hoyos del oponente.
* El objetivo del juego es acumular la mayor cantidad de semillas en su almacén.

## **Funcionalidades**

Nuestra aplicación Bantumi incluye las siguientes funcionalidades:

* Reiniciar el juego, con una confirmación para reiniciar la partida actual. 
* Guardar el juego, lo que permite guardar el estado actual del juego en el sistema de archivos del dispositivo.
* Recuperar el juego, si existe un estado de juego guardado, se puede cargar y continuar jugando.
* Registrar las puntuaciones de los juegos, donde se guardan el nombre del jugador, la hora del juego y la cantidad de semillas en el almacén de cada jugador después de cada partida.
* Ver las mejores puntuaciones, que muestra las diez mejores puntuaciones de los jugadores ordenadas por la cantidad de semillas en el almacén.

## **Mejoras Adicionales**

Puede seguir mejorando la aplicación Bantumi, por ejemplo:

* Permitir marcar la casilla elegida por cada jugador.Cuando un jugador toca o selecciona una de sus casillas, esa casilla se destacará visualmente de manera "marca_jugador_background" Y proporciona una retroalimentación clara al jugador. Luego restablecerla al finalizar su turno o ReiniciarPartida con manera "resetBackgrounds()" para el siguiente partido.
* permitir modificar el número inicial de semillas, permitir elegir qué jugador hace el primer movimiento, add NewGameActivity, pero todavia no completa y no deja funcionar.

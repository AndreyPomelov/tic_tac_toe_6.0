package game.interfaces;

import game.enums.PlayerSymbol;

/**
 * Игрок
 */
public interface Player {

    /**
     * Геттер
     *
     * @return символ игрока
     */
    PlayerSymbol getSymbol();

    /**
     * Геттер
     *
     * @return имя игрока
     */
    String getName();

    /**
     * Сделать ход
     *
     * @return координаты в виде строки с разделителем-пробелом, например - "2 3"
     */
    String makeMove();
}
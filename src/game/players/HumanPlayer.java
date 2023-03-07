package game.players;

import game.enums.PlayerSymbol;

import java.util.Scanner;

/**
 * Игрок-человек.
 */
public class HumanPlayer extends AbstractPlayer {

    /**
     * Конструктор.
     *
     * @param name      имя игрока.
     * @param symbol    символ игрока.
     */
    public HumanPlayer(String name, PlayerSymbol symbol) {
        super(name, symbol);
    }

    /**
     * Сделать ход.
     *
     * @return координаты в виде строки с разделителем-пробелом, например - "2 3".
     */
    @Override
    public String makeMove() {
        System.out.printf("%s, введите номер строки и столбца через пробел.\n", NAME);
        return new Scanner(System.in).nextLine();
    }
}
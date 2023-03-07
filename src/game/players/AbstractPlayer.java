package game.players;

import game.enums.PlayerSymbol;
import game.interfaces.Player;

/**
 * Суперкласс для классов игроков.
 */
public abstract class AbstractPlayer implements Player {

    /**
     * Имя игрока.
     */
    protected final String NAME;

    /**
     * Символ игрока, Х или О.
     */
    protected final PlayerSymbol SYMBOL;

    /**
     * Конструктор.
     *
     * @param name      имя игрока.
     * @param symbol    символ игрока.
     */
    public AbstractPlayer(String name, PlayerSymbol symbol) {
        this.NAME = name;
        this.SYMBOL = symbol;
    }

    /**
     * Геттер.
     *
     * @return символ игрока.
     */
    @Override
    public PlayerSymbol getSymbol() {
        return SYMBOL;
    }

    /**
     * Геттер.
     *
     * @return имя игрока.
     */
    @Override
    public String getName() {
        return NAME;
    }
}
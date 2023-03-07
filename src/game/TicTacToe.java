package game;

import game.enums.PlayerSymbol;
import game.interfaces.Player;
import game.players.ComputerPlayer;
import game.players.HumanPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Игра "Крестики-нолики"
 *
 * @version 5.0
 * @author Andrey Pomelov
 */
public class TicTacToe {

    /**
     * Список игроков.
     */
    private static final List<Player> PLAYERS = new ArrayList<>();

    /**
     * Флаг окончания игры, true - если игра окончена.
     */
    private static boolean isGameOver;

    /**
     * Экземпляр игрового поля.
     */
    private static GameField field;

    /**
     * Минимальный размер игрового поля
     * (минимальная длина выигрышной комбинации
     * также будет равна этому значению).
     */
    private static final int MIN_FIELD_SIZE = 3;

    /**
     * Максимальный размер игрового поля.
     */
    private static final int MAX_FIELD_SIZE = 8;

    /**
     * Точка старта приложения.
     */
    public static void main(String[] args) {

        // Создаём игровое поле.
        init();

        // Игровой цикл продолжается до тех пор, пока isGameOver == false.
        while (!isGameOver) {
            for (Player player : PLAYERS) {
                PlayerSymbol symbol = player.getSymbol();
                String coordinates;
                boolean isMoveSuccess;

                // Цикл выбора координат.
                // Продолжает работу до тех пор, пока игрок не введёт корректные координаты.
                do {
                    coordinates = player.makeMove();

                    // Проверяем, успешно ли сделан ход.
                    isMoveSuccess = field.setSymbol(symbol, coordinates);
                } while (!isMoveSuccess);

                // Отрисовываем игровое поле.
                field.repaint();

                // Проверяем, не выиграл ли игрок в результате своего хода.
                if (field.isWin(symbol.getValue())) {
                    isGameOver = true;
                    System.out.printf("Конец игры. Побеждает %s.\n", player.getName());
                    break;
                }

                // Проверяем, не заполнено ли игровое поле после хода игрока.
                if (field.isFieldFull()) {
                    isGameOver = true;
                    System.out.println("Конец игры. Ничья.");
                    break;
                }
            }
        }
    }

    /**
     * Первоначальная инициализация игры, создание игрового поля.
     */
    private static void init() {
        System.out.println("Игра \"Крестики-нолики\".");

        // Создаём поле для игры.
        int fieldSize = getFieldSize();
        field = new GameField(fieldSize, getWinLength(fieldSize));

        // Создаём и добавляем в игру двоих игроков.
        createPlayers();

        // Отрисовываем игровое поле.
        field.repaint();
    }

    /**
     * Метод создаёт игроков в зависимости от выбранного режима игры.
     */
    private static void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        int gameMode = 0;

        do {
            System.out.println("Выберите режим игры:\n1 - друг против друга.\n2 - против компьютера.");
            try {
                gameMode = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Игнорируем ошибку парсинга введённой строки в число.
            }
        } while (gameMode < 1 || gameMode > 2);

        PLAYERS.add(new HumanPlayer("Игрок 1", PlayerSymbol.X));
        PLAYERS.add(gameMode == 1 ? new HumanPlayer("Игрок 2", PlayerSymbol.O)
                : new ComputerPlayer(PlayerSymbol.O, field));
    }

    /**
     * Выбор игроком длины выигрышной комбинации.
     *
     * @param fieldSize размер игрового поля.
     * @return          длина выигрышной комбинации.
     */
    private static int getWinLength(int fieldSize) {
        int winLength = 0;
        Scanner scanner = new Scanner(System.in);

        // Если размер игрового поля равен минимально возможному,
        // то выбор длины выигрышной комбинации не имеет смысла,
        // принимаем её равной размеру поля.
        if (fieldSize == MIN_FIELD_SIZE) {
            return fieldSize;
        }

        do {
            System.out.printf("Введите длину выигрышной комбинации (от %d до %d).\n", MIN_FIELD_SIZE, fieldSize);

            try {
                winLength = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Игнорируем ошибку парсинга введённой строки в число.
            }
        } while (winLength < MIN_FIELD_SIZE || winLength > fieldSize);

        return winLength;
    }

    /**
     * Выбор игроком размера игрового поля.
     *
     * @return размер игрового поля.
     */
    private static int getFieldSize() {
        int fieldSize = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.printf("Введите размер игрового поля (от %d до %d).\n", MIN_FIELD_SIZE, MAX_FIELD_SIZE);

            try {
                fieldSize = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Игнорируем ошибку парсинга введённой строки в число.
            }
        } while (fieldSize < MIN_FIELD_SIZE || fieldSize > MAX_FIELD_SIZE);

        return fieldSize;
    }
}
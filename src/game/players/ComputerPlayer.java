package game.players;

import game.GameField;
import game.enums.PlayerSymbol;

import java.util.Random;

/**
 * Игрок-компьютер.
 */
public class ComputerPlayer extends AbstractPlayer {

    /**
     * Экземпляр игрового поля, нужен для реализации логики хода.
     */
    private final GameField FIELD;

    /**
     * Конструктор.
     *
     * @param symbol    символ игрока.
     * @param field     экземпляр игрового поля.
     */
    public ComputerPlayer(PlayerSymbol symbol, GameField field) {
        super("SkyNet", symbol);
        this.FIELD = field;
    }

    /**
     * Сделать ход. Компьютер принимает решение по следующему алгоритму:
     * 1. Есть ли возможность выигрыша на данном ходу? Если да - делаем такой ход.
     * 2. Есть ли возможность выигрыша противника в следующем ходу? Если да - блокируем его ход.
     * 3. Пытаемся поставить символ рядом с уже существующим символом в том направлении, где ещё возможна победа.
     * 4. Если не выполнились предыдущие три условия, ставим символ рандомно.
     *
     * @return координаты в виде строки с разделителем-пробелом, например - "2 3".
     */
    @Override
    public String makeMove() {
        System.out.printf("Ходит %s...\n", NAME);

        // Искусственная пауза для удобства восприятия процесса хода компьютера.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 1. Пытаемся выиграть.
        String coordinates = tryToWin();
        if (coordinates != null) {
            return coordinates;
        }

        // 2. Пытаемся заблокировать ход соперника.
        coordinates = tryToBlock();
        if (coordinates != null) {
            return coordinates;
        }

        // 3. Пытаемся продолжить текущую комбинацию.
        coordinates = tryToContinueCombination();
        if (coordinates != null) {
            return coordinates;
        }

        // 4. Ходим рандомно.
        return getRandomCoordinates();
    }

    /**
     * Попытка развить выигрышную комбинацию от уже существующего символа.
     *
     * @return координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String tryToContinueCombination() {
        char[][] field = FIELD.getField();

        for (int row = 1; row <= field.length; row++) {
            for (int column = 1; column <= field.length; column++) {

                // Проверяем возможность продолжения комбинации только от тех ячеек, где уже стоит наш символ.
                if (FIELD.getSymbolByCoordinates(row, column) == SYMBOL.getValue()) {
                    return checkAllDirectionsFromCurrentCell(row, column);
                }
            }
        }
        return null;
    }

    /**
     * Проверяем по очереди все направления от выбранной ячейки.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String checkAllDirectionsFromCurrentCell(int row, int column) {
        if (checkUpRightDiagonal(row, column)) {
            return getUpRightDiagonalCoordinates(row, column);
        }

        if (checkDownRightDiagonal(row, column)) {
            return getDownRightDiagonalCoordinates(row, column);
        }

        if (checkDownLeftDiagonal(row, column)) {
            return getDownLeftDiagonalCoordinates(row, column);
        }

        if (checkUpLeftDiagonal(row, column)) {
            return getUpLeftDiagonalCoordinates(row, column);
        }

        if (checkUpDirection(row, column)) {
            return getUpDirectionCoordinates(row, column);
        }

        if (checkDownDirection(row, column)) {
            return getDownDirectionCoordinates(row, column);
        }

        if (checkLeftDirection(row, column)) {
            return getLeftDirectionCoordinates(row, column);
        }

        if (checkRightDirection(row, column)) {
            return getRightDirectionCoordinates(row, column);
        }

        return null;
    }

    /**
     * Получить координаты для хода по направлению вверх и вправо.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getUpRightDiagonalCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(--row, ++column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вниз и вправо.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getDownRightDiagonalCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(++row, ++column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вниз и влево.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getDownLeftDiagonalCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(++row, --column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вверх и влево.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getUpLeftDiagonalCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(--row, --column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вверх.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getUpDirectionCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(--row, column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вниз.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getDownDirectionCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(++row, column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению влево.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getLeftDirectionCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(row, --column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Получить координаты для хода по направлению вправо.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String getRightDirectionCoordinates(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            if (FIELD.getSymbolByCoordinates(row, ++column) == FIELD.getEmptyCell()) {
                return coordinatesToString(row, column);
            }
        }
        return null;
    }

    /**
     * Проверить направление вверх и вправо на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkUpRightDiagonal(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(--row, ++column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вниз и вправо на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkDownRightDiagonal(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(++row, ++column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вниз и влево на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkDownLeftDiagonal(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(++row, --column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вверх и влево на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkUpLeftDiagonal(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(--row, --column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вверх на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkUpDirection(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(--row, column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вниз на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkDownDirection(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(++row, column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление влево на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkLeftDirection(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(row, --column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить направление вправо на возможность построения выигрышной комбинации.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          true, если возможно построить выигрышную комбинацию.
     */
    private boolean checkRightDirection(int row, int column) {
        for (int i = 1; i < FIELD.getWinLength(); i++) {
            try {
                if (FIELD.getSymbolByCoordinates(row, ++column) == PlayerSymbol.getOpponentSymbol(SYMBOL).getValue()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Попытка заблокировать ход противника.
     *
     * @return координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String tryToBlock() {
        char[][] field = FIELD.getField();

        // Получаем значение символа противника.
        PlayerSymbol opponentSymbol = PlayerSymbol.getOpponentSymbol(SYMBOL);

        for (int row = 1; row <= field.length; row++) {
            for (int column = 1; column <= field.length; column++) {

                // Проверяем только незанятые ячейки.
                if (!FIELD.isCellOccupied(row, column)) {

                    // Ставим в ячейку временный символ противника.
                    FIELD.setTempSymbol(row, column, opponentSymbol);

                    // Проверяем, достигнет ли противник таким образом победы.
                    boolean isWin = FIELD.isWin(opponentSymbol.getValue());

                    // Удаляем временный символ противника.
                    FIELD.removeTempSymbol(row, column);

                    // Если противник может выиграть таким ходом, возвращаем эти координаты для хода.
                    if (isWin) {
                        return coordinatesToString(row, column);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Попытка закончить выигрышную комбинацию.
     *
     * @return координаты для хода, либо null, если нет вариантов для такого хода.
     */
    private String tryToWin() {
        char[][] field = FIELD.getField();

        for (int row = 1; row <= field.length; row++) {
            for (int column = 1; column <= field.length; column++) {

                // Проверяем только незанятые ячейки.
                if (!FIELD.isCellOccupied(row, column)) {

                    // Ставим в ячейку временный свой символ.
                    FIELD.setTempSymbol(row, column, SYMBOL);

                    // Проверяем, достигнем ли мы таким образом победы.
                    boolean isWin = FIELD.isWin(SYMBOL.getValue());

                    // Удаляем временный.
                    FIELD.removeTempSymbol(row, column);

                    // Если можем выиграть таким ходом, возвращаем эти координаты для хода.
                    if (isWin) {
                        return coordinatesToString(row, column);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Получить случайные координаты для хода.
     *
     * @return случайные координаты.
     */
    private String getRandomCoordinates() {
        Random random = new Random();
        int fieldSize = FIELD.getField().length;
        int row, column;

        // Случайно выбираем координаты до тех пор, пока не будет выбрана свободная ячейка.
        do {
            row = random.nextInt(fieldSize) + 1;
            column = random.nextInt(fieldSize) + 1;
        } while (FIELD.isCellOccupied(row, column));

        return coordinatesToString(row, column);
    }

    /**
     * Преобразование координат в строковое выражение.
     *
     * @param row       номер строки.
     * @param column    номер столбца.
     * @return          запись координат в виде строки.
     */
    private String coordinatesToString(int row, int column) {
        return String.format("%d %d", row, column);
    }
}
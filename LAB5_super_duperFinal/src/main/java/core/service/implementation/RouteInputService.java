package core.service.implementation;

import core.contract.Consts;
import core.entity.Coordinates;
import core.entity.Location;
import core.service.IConsoleService;
import core.service.IRouteInputService;

//Сервис для ввода значения Route
public class RouteInputService implements IRouteInputService {

    private final IConsoleService consoleService;

    //Если скриптовый режим, то данные из файла выводятся на консоль как будто бы их ввел пользователь
    private boolean isScriptMode;

    public RouteInputService(IConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    //region public methods

    //Переключение режима
    @Override
    public void setInteractiveMode() {
        isScriptMode = false;
    }

    //Переключение режима
    @Override
    public void setScriptMode() {
        isScriptMode = true;
    }

    //Ввод имени
    @Override
    public String getName() {
        return getString("имя", false);
    }

    //Ввод координат
    @Override
    public Coordinates getCoordinates() {
        consoleService.printLn("Ввод coordinates");

        Double x = getDouble("x");
        Double y = getDouble("y");

        return new Coordinates(x, y);
    }

    //Ввод from
    @Override
    public Location getFrom() {
        consoleService.print(String.format("Ввести from?:%s", Consts.PS2));
        String line = consoleService.readLine();

        if (isScriptMode) {
            consoleService.printLn(line);
        }

        if (line.isEmpty()) {
            return null;
        } else {
            return getLocation();
        }
    }

    //Ввод to
    @Override
    public Location getTo() {
        consoleService.print(String.format("Ввести to?:%s", Consts.PS2));
        String line = consoleService.readLine();

        if (isScriptMode) {
            consoleService.printLn(line);
        }

        if (line.isEmpty()) {
            return null;
        } else {
            return getLocation();
        }
    }

    //Ввод distance
    @Override
    public int getDistance() {
        while (true) {
            try {
                int distance = getInteger("дистанцию");

                if (distance <= 1) {
                    throw new IllegalArgumentException();
                }
                return distance;
            } catch (IllegalArgumentException ex) {
                consoleService.printLnError("Значение должно быть больше 1");
            }
        }
    }

    //endregion

    //region private methods

    //Метод для ввода числа с разным именем, чтобы не плодить много копий одного и того же кода
    private Double getDouble(String fieldName) {
        String line;

        while (true) {
            try {
                consoleService.print(String.format("Введите %s:%s", fieldName, Consts.PS2));
                line = consoleService.readLine().trim();

                if (isScriptMode) {
                    consoleService.printLn(line);
                }

                return Double.parseDouble(line);
            } catch (IllegalArgumentException ex) {
                consoleService.printLnError("Неверное значение.");
            }
        }
    }

    //Метод для ввода числа с разным именем, чтобы не плодить много копий одного и того же кода
    private Integer getInteger(String fieldName) {
        String line;

        while (true) {
            try {
                consoleService.print(String.format("Введите %s:%s", fieldName, Consts.PS2));
                line = consoleService.readLine().trim();

                if (isScriptMode) {
                    consoleService.printLn(line);
                }

                return Integer.parseInt(line);
            } catch (IllegalArgumentException ex) {
                consoleService.printLnError("Неверное значение.");
            }
        }
    }

    //Метод для ввода строки с разным именем, чтобы не плодить много копий одного и того же кода
    private String getString(String fieldName, boolean canBeNull) {
        String line;

        while (true) {
            consoleService.print(String.format("Введите %s:%s", fieldName, Consts.PS2));
            line = consoleService.readLine().trim();

            if (isScriptMode) {
                consoleService.printLn(line);
            }

            if (canBeNull) {
                return line;
            } else {
                if (line.isEmpty()) {
                    consoleService.printLnError("Имя не может быть пустым.");
                } else {
                    return line;
                }
            }
        }
    }

    //Ввод location
    private Location getLocation() {
        Integer x = getInteger("x");
        Double y = getDouble("y");
        String name = getString("имя", true);

        return new Location(x, y, name);
    }

    //endregion
}

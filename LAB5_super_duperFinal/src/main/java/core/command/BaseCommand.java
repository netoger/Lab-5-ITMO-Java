package core.command;

import core.service.IConsoleService;
import lombok.Getter;

//Базовый класс команды. От него наследуются все другие
@Getter
public abstract class BaseCommand  {

    //Имя. Используется дла поиска команды при вводе пользователя.
    private final String name;

    //Аргумент. Используется только для вывод. Например, insert null {element}. Здесь argument - [null {element}]
    private final String argument;

    //Подробнее о команде. Только для вывода.
    private final String description;

    //Сервис ввода/вывода текста
    protected final IConsoleService consoleService;

    //Конструктор для простых команд без аргумента
    public BaseCommand(IConsoleService consoleService, String name, String description) {
        this(consoleService, name, "", description);
    }

    //Конструктор для более сложных команд с аргументом
    public BaseCommand(IConsoleService consoleService, String name, String argument, String description) {
        this.consoleService = consoleService;
        this.name = name;
        this.argument = argument;
        this.description = description;
    }

    //Вывод информации о команде
    @Override
    public String toString() {
        if (argument.isEmpty()) {
            return String.format("%s - %s", name, description);
        } else {
            return String.format("%s %s - %s", name, argument, description);
        }

    }

    //Каждая команда переопределяет этот метод, который всегда запускается в зависимости от валидации(ниже)
    public abstract void execute(String argument);

    protected boolean validate(String argument) {
        return true;
    }
}

package core.service.implementation;

import core.command.BaseCommand;
import core.service.ICommandService;
import core.service.IConsoleService;

import java.util.ArrayList;
import java.util.List;

//Сервис работы с коммандами
public class CommandService implements ICommandService {

    private final IConsoleService consoleService;

    //Список комманд
    private final List<BaseCommand> commands = new ArrayList<>();

    public CommandService(IConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    //Добавление команды
    @Override
    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    //Выполнение команды
    @Override
    public void executeCommand(String name, String argument) {
        // Вызываем help
        if (name.equals("help")) {
            helpCommand(argument);
        } else {
            //Пытаемся найти команду пользователся из списка команд
            var command = commands
                    .stream()
                    .filter(x -> x.getName().equals(name))
                    .findFirst();

            //Если нашли команду, то выполняем ее
            if (command.isPresent()) {
                command.get().execute(argument);
            } else {
                consoleService.printLn(String.format("Комманда '%s' не найдена. Введите 'help' для помощи.\n", name));
            }
        }
    }

    //Единственная команда, которая уже создана заранее. Сделано это для того, чтобы удобно показывать список всех команд.
    private void helpCommand(String argument) {
        if (argument.isEmpty()) {
            commands.forEach(consoleService::printLn);
        } else {
            consoleService.printLnError("Не требуется аргумент.\n");
        }
    }
}

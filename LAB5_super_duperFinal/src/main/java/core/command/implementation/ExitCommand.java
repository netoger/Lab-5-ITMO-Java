package core.command.implementation;

import core.command.BaseCommand;
import core.exception.NotEmptyArgumentException;
import core.service.IConsoleService;

//Команда для выхода из программы
public class ExitCommand extends BaseCommand {

    public ExitCommand(IConsoleService consoleService) {
        super(consoleService, "exit", "завершить программу (без сохранения в файл)");
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            consoleService.printLn("Выход...");
            System.exit(0);
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new NotEmptyArgumentException();
            }

            return true;
        } catch (NotEmptyArgumentException ex) {
            consoleService.printLnError("Не требуется аргумент.\n");
        }

        return false;
    }
}

package core.command.implementation;

import core.command.BaseCommand;
import core.exception.NotEmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда очищения коллекции
public class ClearCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public ClearCommand(IConsoleService consoleService,
                        ICollectionService collectionService) {
        super(consoleService, "clear", "очистить коллекцию");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Если есть данные, то их очищаем
            if (collectionService.getSize() > 0) {
                collectionService.clearItems();
                consoleService.printLn("Коллекция успешно очищена.");
            } else {
                consoleService.printLn("Нечего очищать, ведь в коллекции нет элементов.");
            }
        }
        consoleService.printLn("");
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new NotEmptyArgumentException();
            }

            return true;
        } catch (NotEmptyArgumentException ex) {
            consoleService.printLnError("Не требуется аргумент.");
        }

        return false;
    }
}

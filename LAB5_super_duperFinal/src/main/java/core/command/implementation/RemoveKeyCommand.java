package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.exception.KeyNotFoundException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда удаления элемента по его ключу
public class RemoveKeyCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public RemoveKeyCommand(IConsoleService consoleService,
                            ICollectionService collectionService) {
        super(consoleService, "remove_key", "null", "удалить элемент из коллекции по его ключу");
        this.collectionService = collectionService;
    }


    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            var key = Integer.parseInt(argument);

            try {
                //Пытаемся удалить элемент
                collectionService.removeItem(key);
                consoleService.printLn(String.format("Элемент с ключом %d успешно удален.", key));
            } catch (KeyNotFoundException e) {
                consoleService.printLnError(String.format("Элемент с ключом %d не найден.", key));
            }
        }
        consoleService.printLn("");
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (argument.isEmpty()) {
                throw new EmptyArgumentException();
            }
            Integer.parseInt(argument);

            return true;
        } catch (EmptyArgumentException ex) {
            consoleService.printLnError("Требуется аргумент key.");
        } catch (NumberFormatException ex) {
            consoleService.printLnError("Key должен быть числом.");
        }

        return false;
    }
}

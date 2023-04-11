package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда удаления элементов, у которых ключ больше нашего аргумента
public class RemoveGreaterKeyCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public RemoveGreaterKeyCommand(IConsoleService consoleService,
                                   ICollectionService collectionService) {
        super(consoleService, "remove_greater_key", "null", "удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Исходный размер коллекции
            int sourceSize = collectionService.getSize();

            if (sourceSize > 0) {
                int key = Integer.parseInt(argument);
                //Удаляем элементы
                collectionService.removeItemsByKeyGreaterThan(key);
                //Получаем текущий размер
                int currentSize = collectionService.getSize();
                consoleService.printLn(String.format("Удалено элементов: %d.", sourceSize - currentSize));
            } else {
                consoleService.printLn("Нечего удалять, ведь в коллекции нет элементов.");
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
        }
        catch (NumberFormatException ex) {
            consoleService.printLnError("Key должен быть числом.");
        }

        return false;
    }
}

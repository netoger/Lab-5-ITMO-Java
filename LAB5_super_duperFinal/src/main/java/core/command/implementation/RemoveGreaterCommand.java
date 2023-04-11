package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда удаления всех элементов, у которых id больше нашего аргумента
public class RemoveGreaterCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public RemoveGreaterCommand(IConsoleService consoleService,
                                ICollectionService collectionService) {
        super(consoleService, "remove_greater", "{element}", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Исходный размер коллекции
            int sourceSize = collectionService.getSize();

            if (sourceSize > 0) {
                //Удаляем элементы
                collectionService.removeItemsByIdGreaterThan(Integer.parseInt(argument));
                //Текущий размер после удаления
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
            consoleService.printLnError("Требуется аргумент id.");
        }
        catch (NumberFormatException ex) {
            consoleService.printLnError("Id должен быть числом.");
        }

        return false;
    }
}

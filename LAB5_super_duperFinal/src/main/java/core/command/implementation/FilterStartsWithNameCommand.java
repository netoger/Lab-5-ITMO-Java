package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Вывод элементов, у которых name начинается с нашего аргумента
public class FilterStartsWithNameCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public FilterStartsWithNameCommand(IConsoleService consoleService,
                                       ICollectionService collectionService) {
        super(consoleService, "filter_starts_with_name", "name", "вывести элементы, значения поля name которых начинается с заданной подстроки");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Если в коллекции есть элементы
            if (collectionService.getSize() > 0) {
                //Наша выборка
                var items = collectionService.getItemsByNameStarts(argument);

                //Если мы что-то нашли, то выводим
                if (items.size() > 0) {
                    items.forEach((k, v) -> {
                        consoleService.printLn(String.format("Key: %d", k));
                        consoleService.printLn(String.format("%s\n", v.toString()));
                    });
                } else {
                    consoleService.printLn(String.format("В коллекции нет элементов с name, значение которого начинается с %s\n", argument));
                }
            } else {
                consoleService.printLn("Нечего выводить, ведь в коллекции нет элементов.\n");
            }
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (argument.isEmpty()) {
                throw new EmptyArgumentException();
            }

            return true;
        } catch (EmptyArgumentException ex) {
            consoleService.printLnError("Требуется аргумент name.\n");
        }

        return false;
    }
}

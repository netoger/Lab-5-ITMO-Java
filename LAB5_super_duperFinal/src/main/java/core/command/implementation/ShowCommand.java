package core.command.implementation;

import core.command.BaseCommand;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда вывода всех элементов коллекции
public class ShowCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public ShowCommand(IConsoleService consoleService,
                       ICollectionService collectionService) {
        super(consoleService, "show", "вывести в стандартный поток все элементы коллекции в строковом представлении");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Получаем коллекцию
            var routes = collectionService.getCollection();
            //Если есть данные в коллекции
            if (routes.isEmpty()) {
                consoleService.printLn("Нечего показывать, ведь в коллекции нет элементов.\n");
            } else {
                //Выводим каждый элемент
                routes.forEach((k, v) -> {
                    consoleService.printLn(String.format("Key: %d", k));
                    consoleService.printLn(String.format("%s\n", v.toString()));
                });
            }
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new IllegalArgumentException();
            }

            return true;
        } catch (IllegalArgumentException ex) {
            consoleService.printLnError("Не требуется аргумент.\n");
        }

        return false;
    }
}

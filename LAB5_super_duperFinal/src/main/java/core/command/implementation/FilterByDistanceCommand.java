package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда вывода элементов с фильтром по дистанции
public class FilterByDistanceCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public FilterByDistanceCommand(IConsoleService consoleService,
                                   ICollectionService collectionService) {
        super(consoleService, "filter_by_distance", "distance", "вывести элементы, значения поля distance которых равно заданному");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Если есть элементы
            if (collectionService.getSize() > 0) {
                int distance = Integer.parseInt(argument);
                //Находим все элементы, у которых дистанция равна нашему аргументы
                var items = collectionService.getItemsByDistance(distance);

                //Если такие элементы есть
                if (items.size() > 0) {
                    //Выводим каждый элемент
                    items.forEach((k, v) -> {
                        consoleService.printLn(String.format("Key: %d", k));
                        consoleService.printLn(String.format("%s\n", v.toString()));
                    });
                } else {
                    consoleService.printLn(String.format("В коллекции нет элементов с distance %d\n", distance));
                }
            } else {
                consoleService.printLn("Нечего выводить, ведь в коллекции нет элементов.\n");
            }
        } else {
            consoleService.printLn("");
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (argument.isEmpty()) {
                throw new EmptyArgumentException();
            }
            //Проверяем является ли аргумент числом
            Integer.parseInt(argument);

            return true;
        } catch (EmptyArgumentException ex) {
            consoleService.printLnError("Требуется аргумент distance.");
        }
        catch (NumberFormatException ex) {
            consoleService.printLnError("Distance должен быть числом.");
        }

        return false;
    }
}

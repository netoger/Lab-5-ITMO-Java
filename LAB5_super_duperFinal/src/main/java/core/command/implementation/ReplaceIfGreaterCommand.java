package core.command.implementation;

import core.command.BaseCommand;
import core.entity.Coordinates;
import core.entity.Location;
import core.entity.Route;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;
import core.service.IRouteInputService;

//Команда замены значения по ключу, если новое значение(я выбрал distance) больше старого
public class ReplaceIfGreaterCommand extends BaseCommand {

    private final ICollectionService collectionService;

    //Сервис работы с заполнением Route
    private final IRouteInputService inputService;

    public ReplaceIfGreaterCommand(IConsoleService consoleService,
                                   ICollectionService collectionService,
                                   IRouteInputService inputService) {
        super(consoleService, "replace_if_greater", "null {element}", "заменить значение по ключу, если новое значение больше старого");
        this.collectionService = collectionService;
        this.inputService = inputService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            int key = Integer.parseInt(argument);

            //Пытаемся найти элемент с ключом
            var item = collectionService.getByKey(key);

            //Если в коллекции есть такой элемент
            if (item.isPresent()) {
                Route route = item.get();

                //Вводим обязательные и необязательные данные
                String name = inputService.getName();
                Coordinates coordinates = inputService.getCoordinates();
                Location from = inputService.getFrom();
                Location to = inputService.getTo();
                int distance = inputService.getDistance();

                //Если новая дистанция больше старой дистанции, то заменяем элемент
                if (distance > item.get().getDistance()) {
                    route.setName(name);
                    route.setCoordinates(coordinates);
                    route.setFrom(from);
                    route.setTo(to);
                    route.setDistance(distance);
                    collectionService.updateItem(route);
                    consoleService.printLn("Новое значение distance больше старого. Изменения сохранены.");
                } else {
                    consoleService.printLn("Новое значение distance меньше старого. Изменения отклонены.");
                }
            } else {
                consoleService.printLnError(String.format("Элемент с ключом %s не найден.", key));
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

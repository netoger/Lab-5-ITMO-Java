package core.command.implementation;

import core.command.BaseCommand;
import core.entity.Coordinates;
import core.entity.Location;
import core.entity.Route;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;
import core.service.IRouteInputService;

import java.time.LocalDateTime;

//Команда добавления нового элемента
public class InsertCommand extends BaseCommand {

    private final ICollectionService collectionService;

    //Используем сервис для ввода данных элемента. В данноом случае Route
    private final IRouteInputService inputService;

    public InsertCommand(IConsoleService consoleService,
                         ICollectionService collectionService,
                         IRouteInputService inputService) {
        super(consoleService,"insert", "null {element}", "добавить новый элемент с заданным ключом");
        this.collectionService = collectionService;
        this.inputService = inputService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Вводим все обязательные и необязательные данные
            int key = Integer.parseInt(argument);
            String name = inputService.getName();
            Coordinates coordinates = inputService.getCoordinates();
            Location from = inputService.getFrom();
            Location to = inputService.getTo();
            int distance = inputService.getDistance();

            Route route = new Route();
            route.setName(name);
            route.setCoordinates(coordinates);
            //Автоматически назначаем дату создания
            route.setCreationDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            route.setFrom(from);
            route.setTo(to);
            route.setDistance(distance);
            //Добавляем элемент в коллекцию
            collectionService.addItem(key, route);
            consoleService.printLn(String.format("Элемент с ключом %d успешно добавлен.", key));
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

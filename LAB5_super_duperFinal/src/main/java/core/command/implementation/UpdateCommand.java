package core.command.implementation;

import core.command.BaseCommand;
import core.entity.Coordinates;
import core.entity.Location;
import core.entity.Route;
import core.exception.EmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;
import core.service.IRouteInputService;

//Команда обновления значения элемента по id
public class UpdateCommand extends BaseCommand {

    private final ICollectionService collectionService;
    //Сервис работы с заполнением Route
    private final IRouteInputService inputService;

    public UpdateCommand(IConsoleService consoleService,
                         ICollectionService collectionService,
                         IRouteInputService inputService) {
        super(consoleService, "update", "id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionService = collectionService;
        this.inputService = inputService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            int id = Integer.parseInt(argument);
            var item = collectionService.getById(id);

            //Если нашли старый элемент с id аргумента
            if (item.isPresent()) {
                //Выводим старое значение
                Route route = item.get();
                consoleService.printLn("Старое значение:");
                consoleService.printLn(route.toString());

                //Вводим новое значение
                consoleService.printLn("\nНовое значение:");
                String name = inputService.getName();
                Coordinates coordinates = inputService.getCoordinates();
                Location from = inputService.getFrom();
                Location to = inputService.getTo();
                int distance = inputService.getDistance();

                route.setName(name);
                route.setCoordinates(coordinates);
                route.setFrom(from);
                route.setTo(to);
                route.setDistance(distance);
                //Сохраняем изменения
                collectionService.updateItem(route);
                consoleService.printLn(String.format("Элемент с id %d успешно изменен.", id));
            } else {
                consoleService.printLnError(String.format("Элемент с id %d не найден.", id));
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
        } catch (NumberFormatException ex) {
            consoleService.printLnError("Id должен быть числом.");
        }

        return false;
    }
}

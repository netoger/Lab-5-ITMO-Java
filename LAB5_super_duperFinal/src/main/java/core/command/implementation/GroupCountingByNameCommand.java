package core.command.implementation;

import core.command.BaseCommand;
import core.exception.NotEmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Вывод групп по имени
public class GroupCountingByNameCommand extends BaseCommand {

    private ICollectionService collectionService;

    public GroupCountingByNameCommand(IConsoleService consoleService,
                                      ICollectionService collectionService) {
        super(consoleService, "group_counting_by_name", "сгруппировать элементы коллекции по значению поля name, вывесли количество элементов в каждой группе");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Если в коллекции что-то есть
            if (collectionService.getSize() > 0) {
                //Получаем группы и каждую выводим
                collectionService
                        .getGroupsInfoByName()
                        .forEach((group, size) -> {
                            consoleService.printLn(String.format("Группа: {Имя: %s, Количество элементов: %d}", group, size));
                        });
                consoleService.printLn("");
            } else {
                consoleService.printLn("Нечего группировать, ведь в коллекции нет элементов.\n");
            }
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new NotEmptyArgumentException();
            }

            return true;
        } catch (NotEmptyArgumentException ex) {
            consoleService.printLnError("Не требуется аргумент.\n");
        }

        return false;
    }
}

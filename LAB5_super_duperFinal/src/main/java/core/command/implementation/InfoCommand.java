package core.command.implementation;

import core.command.BaseCommand;
import core.exception.NotEmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;

//Команда вывода информации о коллекции(тип, кол-во элементов и.т.п)
public class InfoCommand extends BaseCommand {

    private final ICollectionService collectionService;

    public InfoCommand(IConsoleService consoleService,
                       ICollectionService collectionService) {
        super(consoleService, "info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionService = collectionService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            consoleService.printLn(String.format("%s", collectionService.getCollectionInfo()));
        }
        consoleService.printLn("");
    }

    @Override
    protected boolean validate(String argument) {
        try {
            if (!argument.isEmpty()) {
                throw new NotEmptyArgumentException();
            }

            return true;
        } catch (NotEmptyArgumentException ex) {
            consoleService.printLnError("Не требуется аргумент.");
        }

        return false;
    }
}

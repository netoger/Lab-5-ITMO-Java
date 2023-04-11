package core.command.implementation;

import core.command.BaseCommand;
import core.exception.NotEmptyArgumentException;
import core.service.ICollectionService;
import core.service.IConsoleService;
import core.service.IFileService;

import java.io.IOException;

//Команда сохранения коллекции в файл
public class SaveCommand extends BaseCommand {

    private final ICollectionService collectionService;

    //Сервис по работе с файлами
    private final IFileService fileService;

    public SaveCommand(IConsoleService consoleService,
                       ICollectionService collectionService,
                       IFileService fileService) {
        super(consoleService, "save", "сохранить словарь в файл");
        this.collectionService = collectionService;
        this.fileService = fileService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Получаем коллекцию
            var collection = collectionService.getCollection();
            try {
                //Пытаемся ее записать в файл
                fileService.writeMap(collection);
                consoleService.printLn("Словарь успешно записан в файл.");
            } catch (IOException ex) {
                consoleService.printLnError("Невозможно записать словарь в файл.");
            }
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

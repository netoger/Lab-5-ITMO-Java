package core.service.implementation;

import core.contract.Consts;
import core.service.*;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Сервис для запуска режимов
public class ModeService implements IModeService {

    private final ICommandService commandService;
    private final IConsoleService consoleService;
    private final IFileService fileService;
    private final ICollectionService collectionService;
    private final IRouteInputService inputService;

    //Текущие состояние
    private boolean isInteractiveMode = true;

    //Файл скрипта
    private String scriptFileName;

    public ModeService(ICommandService commandService,
                       IConsoleService consoleService,
                       IFileService fileService,
                       ICollectionService collectionService,
                       IRouteInputService inputService) {
        this.commandService = commandService;
        this.consoleService = consoleService;
        this.fileService = fileService;
        this.collectionService = collectionService;
        this.inputService = inputService;
    }

    //region public methods

    //Бесконечный запуск модов
    @Override
    public void launch() {
        loadMap();

        while (true) {
            launchInteractiveMode();
            launchScriptMode();
        }
    }

    //Закрываем текущий интерактивный режим
    @Override
    public void closeInteractiveMode(String scriptFileName) {
        this.scriptFileName = scriptFileName;
        isInteractiveMode = false;
    }

    //endregion

    //region private methods

    //Запускаем интерактивный режим
    private void launchInteractiveMode() {
        //Команда пользователя и ее аргументы
        String[] userCommand;

        //Устанавливаем консольный сканнер
        consoleService.setScanner(Consts.consoleScanner);

        //Запускаем интерактивный режим для установки значений Route
        inputService.setInteractiveMode();

        while (isInteractiveMode) {
            consoleService.print(Consts.PS1);

            try {
                //Команда пользователя
                userCommand = consoleService
                        .readLine()
                        .trim()
                        .split(" ", 2);

                //Пытаемся ее запустить
                commandService.executeCommand(userCommand[0], userCommand.length > 1 ? userCommand[1] : "");
            } catch (NoSuchElementException ex) {
                consoleService.printLn("Ctrl-D выход из программы");
                System.exit(0);
            }
        }
    }

    //Запуск скриптового мода
    private void launchScriptMode() {
        String[] scriptCommand;

        try {
            //Создаем и устанавливаем файловый сканнер
            Scanner scanner = new Scanner(new File(scriptFileName));
            consoleService.setScanner(scanner);
            inputService.setScriptMode();

            //Если не конец файла
            if (scanner.hasNextLine()) {
                do {
                    //Скриптовая команда
                    scriptCommand = consoleService
                            .readLine()
                            .trim()
                            .split(" ", 2);

                    //Выводим ее для наглядности
                    consoleService.printLn(String.format("%s %s", scriptCommand[0], scriptCommand.length > 1 ? scriptCommand[1] : ""));

                    //Пытаемся ее запустить
                    commandService.executeCommand(scriptCommand[0], scriptCommand.length > 1 ? scriptCommand[1] : "");
                } while (scanner.hasNextLine());
            } else {
                throw new NoSuchElementException();
            }

        } catch (FileNotFoundException ex) {
            consoleService.printLnError(String.format("Файл скрипта %s не найден!", scriptFileName));
        } catch (NoSuchElementException ex) {
            consoleService.printLnError("Файл скрипта пуст!");
        }

        //По окончанию работы или ошибке, запускаем интерактивный режим
        isInteractiveMode = true;
    }

    //Первоначальная загрузка файла
    private void loadMap() {
        try {
            //Считываем данные из файла
            var items = fileService.readMap();

            //Пытаемся их добавить
            collectionService.addItems(items);
            consoleService.printLn(String.format("В словарь успешно загружено элементов: %d. ", collectionService.getSize()));
        } catch (FileNotFoundException ex) {
            consoleService.printLnError(String.format("Файл со словарем %s не найден.", fileService.getFileName()));
        } catch (YAMLException ex) {
            consoleService.printLnError("Невозможно распарсить словарь.");
        }
    }

    //endregion
}

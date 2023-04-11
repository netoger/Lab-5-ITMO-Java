package core.service.configuration;

import core.command.implementation.*;
import core.contract.Consts;
import core.service.*;
import core.service.implementation.*;

import java.util.Objects;

//Сервис конфигурации приложения. Здесь происходит настройка инициализации всех сервисов. DI контейнер на минималках
public class ServiceConfiguration {

    private static final IConsoleService consoleService = new ConsoleService(Consts.consoleScanner);

    private static ICollectionService collectionService;
    private static IFileService fileService;
    private static IRouteInputService inputService;
    private static ICommandService commandService;
    private static IModeService modeService;

    public static IModeService build() {
        String fileName = getEnv();

        collectionService = new CollectionService();
        fileService = new YamlFileService(fileName);
        inputService = new RouteInputService(consoleService);
        commandService = new CommandService(consoleService);
        modeService = new ModeService(commandService, consoleService,
                fileService, collectionService, inputService);
        addCommands();

        return modeService;
    }

    //Добавляем все команды
    private static void addCommands() {
        commandService.addCommand(new InfoCommand(consoleService, collectionService));
        commandService.addCommand(new ShowCommand(consoleService, collectionService));
        commandService.addCommand(new InsertCommand(consoleService, collectionService, inputService));
        commandService.addCommand(new UpdateCommand(consoleService, collectionService, inputService));
        commandService.addCommand(new RemoveKeyCommand(consoleService, collectionService));
        commandService.addCommand(new ClearCommand(consoleService, collectionService));
        commandService.addCommand(new SaveCommand(consoleService, collectionService, fileService));
        commandService.addCommand(new ExecuteScriptCommand(consoleService, modeService));
        commandService.addCommand(new ExitCommand(consoleService));
        commandService.addCommand(new RemoveGreaterCommand(consoleService, collectionService));
        commandService.addCommand(new ReplaceIfGreaterCommand(consoleService, collectionService, inputService));
        commandService.addCommand(new RemoveGreaterKeyCommand(consoleService, collectionService));
        commandService.addCommand(new GroupCountingByNameCommand(consoleService, collectionService));
        commandService.addCommand(new FilterByDistanceCommand(consoleService, collectionService));
        commandService.addCommand(new FilterStartsWithNameCommand(consoleService, collectionService));
    }

    //Получение значения из переменной окружения
    private static String getEnv() {
        //Пытаемся получить значение
        String env = System.getenv(Consts.envName);

        //Если есть значение
        if (Objects.nonNull(env)) {
            consoleService.printLn(String.format("Получено значение системной переменной %s", env));
            consoleService.printLn(String.format("Будет использовано имя файла: %s.", env));
            return env;
        } else {
            consoleService.printLn(String.format("Будет использовано стандартное имя файла: %s.", Consts.defaulFileName));
            return Consts.defaulFileName;
        }
    }
}

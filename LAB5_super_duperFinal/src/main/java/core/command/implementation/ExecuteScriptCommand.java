package core.command.implementation;

import core.command.BaseCommand;
import core.exception.EmptyArgumentException;
import core.service.IConsoleService;
import core.service.IModeService;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

//Команда запуска скрипта
public class ExecuteScriptCommand extends BaseCommand {

    //Здесь используется сервис для переключени между режимами: интерактивный и скриптовый
    private final IModeService modeService;

    public ExecuteScriptCommand(IConsoleService consoleService,
                                IModeService modeService) {
        super(consoleService, "execute_script", "считать и выполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.modeService = modeService;
    }

    @Override
    public void execute(String argument) {
        if (validate(argument)) {
            //Выходим из интерактивного режима и автоматически запустится скриптовый
            modeService.closeInteractiveMode(argument);
        } else {
            consoleService.printLn("");
        }
    }

    @Override
    protected boolean validate(String argument) {
        try {
            //Если есть аргумент - имя файла
            if (argument.isEmpty()) {
                throw new EmptyArgumentException();
            }

            //Если файл существует и это не директория
            if (!Paths.get(argument).toFile().isFile()){
                throw new FileNotFoundException();
            }

            return true;
        } catch (EmptyArgumentException ex) {
            consoleService.printLnError("Требуется аргумент file_name.");
        }
        catch (NumberFormatException ex) {
            consoleService.printLnError("Key должен быть числом.");
        } catch (FileNotFoundException ex) {
            consoleService.printLnError("Файл не найден.");
        }

        return false;
    }
}

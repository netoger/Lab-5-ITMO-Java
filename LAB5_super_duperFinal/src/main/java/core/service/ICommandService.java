package core.service;

import core.command.BaseCommand;

public interface ICommandService {

    void addCommand(BaseCommand command);
    void executeCommand(String name, String argument);
}

package core.service.implementation;

import core.service.IConsoleService;

import java.util.Scanner;

//Сервис для работы с вводом/вывода
public class ConsoleService implements IConsoleService {

    //Наш сканнер. В работе его значение может меняться на консольный ввод и файловый
    Scanner scanner;

    public ConsoleService(Scanner scanner) {
        this.scanner = scanner;
    }

    //Измение значение сканера
    @Override
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void print(Object object) {
        System.out.print(object);
    }

    @Override
    public void printLn(Object object) {
        System.out.println(object);
    }

    @Override
    public void printLnError(Object object) {
        System.out.println(String.format("Ошибка: %s", object));
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}

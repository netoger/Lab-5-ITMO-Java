package core.contract;

import java.util.Scanner;

public class Consts {

    //Сканер для работы с вводом из консоли
    public static final Scanner consoleScanner = new Scanner(System.in);

    //Стандартное имя ПЕРЕМЕННОЙ_ОКРУЖЕНИЯ
    public static final String envName = "LAB5_ITEMS_PATH";

    //Стандартное имя файла с коллекцией(сохранеие и загрузка)
    public static final String defaulFileName = "items.yml";

    //Ввод команды
    public static final String PS1 = "$ ";

    //Ввод значения внутри команд
    public static final String PS2 = "> ";
}

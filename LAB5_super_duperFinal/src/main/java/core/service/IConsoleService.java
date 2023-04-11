package core.service;

import java.util.Scanner;

public interface IConsoleService {

    void setScanner(Scanner scanner);
    void print(Object object);
    void printLn(Object object);
    void printLnError(Object object);
    String readLine();
}

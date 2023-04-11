package core.service.implementation;

import core.entity.Route;
import core.service.IFileService;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

//Сервис для работы с файлом
public class YamlFileService implements IFileService {

    private final String fileName;

    public YamlFileService(String fileName) {
        this.fileName = fileName;
    }

    //Чтение данных из файла
    @Override
    public HashMap<Integer, Route> readMap() throws FileNotFoundException, YAMLException {
        //Открываем файл
        FileReader reader = new FileReader(fileName);
        Scanner scanner = new Scanner(reader);
        StringBuilder s = new StringBuilder();

        //Пока есть строка в файла - добавляем ее
        while (scanner.hasNextLine()) {
            s.append(String.format("%s\n", scanner.nextLine()));
        }

        //Пытаемся преобразовать строку в Route
        return new Yaml().load(s.toString());
    }

    //Записть данных в файл
    @Override
    public void writeMap(HashMap<Integer, Route> data) throws IOException {
        Yaml yaml = new Yaml();
        FileOutputStream file = new FileOutputStream(fileName);
        OutputStreamWriter output = new OutputStreamWriter(file);
        yaml.dump(data, output);
        output.close();
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}

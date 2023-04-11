package core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
//Класс для хранения данных о коллекции. Используется в InfoCommand
public class CollectionInfo {

    //Тип коллекции
    private final Object type;

    //Дата инициализации
    private final LocalDateTime initializationDate;

    //Количество элементов
    private final Integer countElements;

    @Override
    public String toString() {
        String result = "";

        //Вывод даты в таком формате
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        result += String.format("Тип: %s\n", type);
        result += String.format("Дата инициализации: %s\n", initializationDate.format(format));
        result += String.format("Количество элементов: %d", countElements);

        return result;
    }
}

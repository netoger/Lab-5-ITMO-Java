package core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    private int id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Location from;
    private Location to;
    private int distance;

    //Конструктор для глубокого копирования данных
    public Route(Route route) {
        if (Objects.nonNull(route)) {
            this.id = route.id;
            this.name = route.name;
            this.coordinates = new Coordinates(route.coordinates);

            //Если есть creationDate, то запускаем глубокое копирование
            if (Objects.nonNull(route.creationDate)) {
                this.creationDate = new Date(route.creationDate.getTime());
            }

            //Если есть from, то запускаем глубокое копирование
            if (Objects.nonNull(route.from)) {
                this.from = new Location(route.from);
            }

            //Если есть to, то запускаем глубокое копирование
            if (Objects.nonNull(route.to)) {
                this.to = new Location(route.to);
            }

            this.distance = route.distance;
        }
    }

    //Вывод информации о Route
    @Override
    public String toString() {
        String result = "";
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        result += String.format("Id: %d\n", getId());
        result += String.format("Name: %s\n", getName());
        result += String.format("Coordinates: {X: %4.3f, Y: %4.3f}\n", coordinates.getX(), coordinates.getY());
        result += String.format("CreationDate: %s\n", dateFormat.format(creationDate));

        if (Objects.isNull(from)) {
            result += String.format("From: null\n");
        } else {
            result += String.format("%-5s {X: %d, Y: %4.3f, Name: %s}\n", "From:", from.getX(), from.getY(), from.getName());
        }

        if (Objects.isNull(to)) {
            result += String.format("To: null\n");
        } else {
            result += String.format("%-5s {X: %d, Y: %4.3f, Name: %s}\n", "To:", to.getX(), to.getY(), to.getName());
        }

        result += String.format("Distance: %d", distance);

        return result;
    }
}

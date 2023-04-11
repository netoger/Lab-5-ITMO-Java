package core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    private Double x;
    private Double y;

    //Конструктор для глубокого копирования данных
    public Coordinates(Coordinates coordinates) {
        if (Objects.nonNull(coordinates)) {
            this.x = coordinates.x;
            this.y = coordinates.y;
        }
    }
}

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
public class Location {

    private Integer x;
    private Double y;
    private String name;

    //Конструктор для глубокого копирования данных
    public Location(Location location) {
        if (Objects.nonNull(location)) {
            this.x = location.x;
            this.y = location.y;
            this.name = location.name;
        }
    }
}

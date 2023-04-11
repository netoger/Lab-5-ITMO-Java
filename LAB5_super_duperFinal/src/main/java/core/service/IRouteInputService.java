package core.service;

import core.entity.Coordinates;
import core.entity.Location;

public interface IRouteInputService {

    void setInteractiveMode();
    void setScriptMode();
    String getName();
    Coordinates getCoordinates();
    Location getFrom();
    Location getTo();
    int getDistance();
}

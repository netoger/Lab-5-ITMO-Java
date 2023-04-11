package core.service;

import core.entity.Route;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface IFileService {

    HashMap<Integer, Route> readMap() throws FileNotFoundException;
    void writeMap(HashMap<Integer, Route> data) throws IOException;
    String getFileName();
}

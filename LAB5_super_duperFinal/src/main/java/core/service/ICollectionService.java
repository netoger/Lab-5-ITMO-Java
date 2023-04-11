package core.service;

import core.entity.CollectionInfo;
import core.entity.Route;
import core.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Optional;

public interface ICollectionService {

    int getSize();
    CollectionInfo getCollectionInfo();
    HashMap<Integer, Route> getCollection();
    Optional<Route> getByKey(int key);
    Optional<Route> getById(int id);
    HashMap<String, Integer> getGroupsInfoByName();
    HashMap<Integer, Route> getItemsByDistance(int distance);
    HashMap<Integer, Route> getItemsByNameStarts(String name);

    void addItem(int key, Route item);
    void addItems(HashMap<Integer, Route> items);

    void updateItem(Route route);

    void removeItem(int key) throws KeyNotFoundException;
    void removeItemsByIdGreaterThan(int id);
    void removeItemsByKeyGreaterThan(int key);
    void clearItems();
}

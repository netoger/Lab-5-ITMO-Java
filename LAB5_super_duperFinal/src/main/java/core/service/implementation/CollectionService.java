package core.service.implementation;

import core.entity.CollectionInfo;
import core.entity.Route;
import core.exception.KeyNotFoundException;
import core.service.ICollectionService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

//Сервис по работе с коллекцией.
public final class CollectionService implements ICollectionService {

    private final HashMap<Integer, Route> routes = new HashMap<>();
    private final LocalDateTime creationDate = LocalDateTime.now();

    //region public methods

    //Возвращает размер коллекции
    @Override
    public int getSize() {
        return routes.size();
    }

    //Возвращает информацию о коллекции
    @Override
    public CollectionInfo getCollectionInfo() {
        return new CollectionInfo(routes.getClass(), creationDate, routes.size());
    }

    //Возвращает всю коллекци. !!ВАЖНО!! Элементы в новой коллекции полностью скопированы через глубокое копирование
    @Override
    public HashMap<Integer, Route> getCollection() {
        HashMap<Integer, Route> result = new HashMap<>();
        routes
                .entrySet()
                .stream()
                .forEach(x-> result.put(x.getKey(), new Route(x.getValue())));
        return routes;
    }

    //Вернуть элемент по ключу.
    @Override
    public Optional<Route> getByKey(int key) {
        return routes
                .entrySet()
                .stream()
                .filter(x -> Objects.equals(x.getKey(), key)) //наш фильтр поиска по key
                .findFirst() //найти первый элемент
                .map(x -> x.getValue()); //преобразовать в Optional<Route>;
    }

    //Вернуть элемент по id.
    @Override
    public Optional<Route> getById(int id) {
        return routes
                .entrySet()
                .stream()
                .filter(x -> Objects.equals(x.getValue().getId(), id)) //наш фильтр поиска по id
                .findFirst() //найти первый элемент
                .map(x ->  new Route(x.getValue())); //преобразовать в Optional<Route>;
    }

    //Вернуть сгруппированные элементы по имени
    @Override
    public HashMap<String, Integer> getGroupsInfoByName() {
        HashMap<String, Integer> result = new HashMap<>(); //Подготавливаем результат
        routes
                .entrySet()
                .stream()
                .collect(groupingBy(x -> x.getValue().getName())) //группируем элементы по имени
                .forEach((k,v) -> result.put(k, v.size())); //проходимся по каждому элементу в этой группе и добавляем его в наш result

        return result;
    }

    //Возвращает элементы, у которых дистанция равна аргументу
    @Override
    public HashMap<Integer, Route> getItemsByDistance(int distance) {
        HashMap<Integer, Route> result = new HashMap<>(); //Подготавливаем результат
        routes
                .entrySet()
                .stream()
                .filter((x) -> Objects.equals(x.getValue().getDistance(), distance)) //наш фильтр поиска по distance
                .forEach(x -> result.put(x.getKey(), new Route(x.getValue()))); //проходимся по каждому элементу в этой группе и добавляем его в наш result

        return result;
    }

    //Возвращает элементы, у которых имя начинается с нашего аргумента
    @Override
    public HashMap<Integer, Route> getItemsByNameStarts(String name) {
        HashMap<Integer, Route> result = new HashMap<>(); //Подготавливаем результат
        routes
                .entrySet()
                .stream()
                .filter(x -> x.getValue().getName().startsWith(name)) //наш фильтр поиска по name
                .forEach(x -> result.put(x.getKey(), new Route(x.getValue()))); //проходимся по каждому элементу в этой группе и добавляем его в наш result

        return result;
    }

    //Добавление нового элемента
    @Override
    public void addItem(int key, Route item) {
        //Пытаемся найти элемент с нашим key. Делаем это для того, чтобы заменить значения по ключу, т.к это HashMap
        var previousItem = routes.get(key);

        //Если мы не нашли элемент
        if (Objects.isNull(previousItem)) {
            //То нам надо найти максимальный id, который мы можем назначить. Перебором проходимся и находим его.
            int maxId = 0;
            for(var entry : routes.entrySet()) {
                if (entry.getValue().getId() > maxId) {
                    maxId = entry.getValue().getId();
                }
            }
            //Устанавливаем наше значение maxId
            item.setId(++maxId);
        } else {
            //Т.к элемент мы нашли, то мы можем взять его значение(оно всегда будет уникальным) и просто вставить в новый элемент.
            item.setId(previousItem.getId());
        }

        //Перезаписываем дату создания
        item.setCreationDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        routes.put(key, item);
    }

    //Добавляет много элемент.
    @Override
    public void addItems(HashMap<Integer, Route> items) {
        //Поочередно вызываем addItem, если items не null
        if (Objects.nonNull(items)) {
            items.forEach((k,v) -> addItem(k, v));
        }
    }

    //Обновление элемента по его id
    @Override
    public void updateItem(Route route) {
        routes
                .entrySet()
                .stream()
                .filter(x -> Objects.equals(x.getValue().getId(), route.getId())) //Наш фильтр поиска по id
                .findFirst() //Пытаемся найти первый
                .ifPresent(x -> routes.replace(x.getKey(), route)); //Если такой элемент есть, то мы перезаписываем данные
    }

    //Удалением элемента по ключу
    @Override
    public void removeItem(int key) throws KeyNotFoundException {
        //Если есть такой элемент
        if (Objects.nonNull(routes.get(key))) {
            routes.remove(key);
        } else {
            throw new KeyNotFoundException();
        }
    }

    //Удаление элементов, у которых id больше нашего
    @Override
    public void removeItemsByIdGreaterThan(int id) {
        routes
                .entrySet()
                .stream()
                .filter(x -> x.getValue().getId() > id) //наш фильтр поиска
                .map(x -> x.getKey()) //преобразуем
                .toList() //в лист
                .forEach(x -> routes.remove(x)); //проходимся по каждому и удаляем
    }

    //Удаление элементов по ключу, у которых он больше нашего
    @Override
    public void removeItemsByKeyGreaterThan(int key) {
        routes
                .entrySet()
                .stream()
                .filter(x -> x.getKey() > key) //наш фильтр поиска
                .map(x -> x.getKey()) //преобразуем
                .toList() //в лист
                .forEach(x -> routes.remove(x)); //проходимся по каждому и удаляем
    }

    //Очищение всей коллекции
    @Override
    public void clearItems() {
        routes.clear();
    }

    //endregion
}

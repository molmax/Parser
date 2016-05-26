package com.somecom.dbc;

import com.somecom.parser.Entry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Cannon on 16.04.2016.
 * <p>Интерфейс работает с БД. Реализует операции CRUD</p>
 */

public interface DBController {

    /**
     * <p>Устанавливает соединение с базой данных</p>
     * *@return объект connection
     * * */
    Connection getConnection();

    /**
     * <p>Закрывает соединение с базой данных</p>
     * */
    void closeConnection();

    /**
     * <p>Закрывает statement если он не null</p>
     * */
    void closeStatement();

    /**
     * <p>Метод для создания таблицы в базе данных</p>
     * @param tableName Имя создаваемой таблицы
     * @param createStatement Передаваемая команда CREATE TABLE
     * */
    void createTable(String tableName, String createStatement);

    /**
     * <p>Метод для вставки записей в таблицу</p>
     * @param tableName Имя таблицы с которой работаем
     * @param insertStatement Передаваемая команда INSERT
     * */
    void insertIntoTable(String tableName, String insertStatement);

    /**
     * <p>Метод для обновления записей в таблице</p>
     * @param tableName Имя таблицы с которой работаем
     * @param updateStatement Передаваемая команда UPDATE
     * */
    void updateTable(String tableName, String updateStatement);

    /**
     * <p>Метод для удаления записей из таблицы</p>
     * @param tableName Имя таблицы с которой работаем
     * @param deleteStatement Передаваемая команда DELETE
     * */
    void deleteFromTable(String tableName, String deleteStatement);

    /**
     * <p>Метод для работы с командой SELECT</p>
     * <p>Производится выборка из таблицы в соответствии с переданным в качестве аргумента запросом</p>
     * @param query Запрос к базе данных на операцию выборки
     * @return Возвращает ResultSet, содержащий информацию по результатам запроса SELECT. Так же результат выборки выводится в консоль
     * */
    ResultSet executeQuery(String query);

    /**
     * <p>Метод, возвращаюшщий все записи из таблицы</p>
     * @return Возвращает ResultSet, содержащий все записи в текущей таблице
     * */
    ResultSet getObjs();

    //Методы для работы с сервером
    /**
     * <p>Метод читает все записи из БД (для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    List<Entry> readAllFromDataBase(List <Entry> entries);

    /**
     * <p>Метод читает записи из выбранных проектов в БД(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param projectName Имя интересующего проекта
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    List<Entry> readFromDataBaseByProjectName (List <Entry> entries, String projectName);

    /**
     * <p>Метод возвращает записи из БД в соответствии с выбранными ключевыми словами(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param requirement Интересующий параметр(требование)
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    List<Entry> readFromDataBaseByRequirement (List <Entry> entries, String requirement);

    /**
     * <p>Метод возвращает записи из БД в соответствии с выбранными ключевыми словами и именем проекта(для web)</p>
     * @param entries Пустая коллекция, которую метод заполняет инициализированными объектами
     * @param requirement Интересующий параметр(требование)
     * @param projectName Имя интересующего проекта
     * @return Возвращает коллекцию объектов, соответствующих записям в БД
     * */
    List<Entry> readFromDataBaseByProjectNameAndRequirement (List <Entry> entries, String projectName, String requirement);
}

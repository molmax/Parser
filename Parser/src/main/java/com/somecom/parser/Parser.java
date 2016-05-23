package com.somecom.parser;

import java.io.IOException;
import java.util.List;

/**
 * Created by Cannon on 15.04.2016.
 * <p>Интерфейс занимается обработкой файла</p>
 */

public interface Parser {
    /**
     * <p>Метод принимает в качестве параметра путь к файлу XLSX, читает его и инициализирует поля экземпляра класса Entry,
     * переданного в конструктор класса ParserImp</p>
     * @param path Путь к файлу с которым необходимо работать
     * @return Возвращает ArrayList <Entry> с которой работает DBControllerImp
     * */
    List <Entry> parseXLS(String path, List<Entry> entries) throws IOException;

    /**
     * <p>Метод принимает в качестве параметра путь к файлу JSON, читает его и инициализирует поля экземпляра класса Entry,
     * переданного в конструктор класса ParserImp</p>
     * @param path Путь к файлу с которым необходимо работать
     * @return Возвращает ArrayList <Entry> с которой работает DBControllerImp
     * */
    List<Entry> parseJSON(String path, List<Entry> entries) throws IOException;
}
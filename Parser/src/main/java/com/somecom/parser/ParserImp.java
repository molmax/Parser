package com.somecom.parser;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.*;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by Cannon on 15.04.2016.
 * <p>Класс реализует интерфейс Parser.</p>
 */

public class ParserImp implements Parser{

    List<Entry> entries;

    private static int REQ_COLUMN_NUM;
    private static int DESCR_COLUMN_NUM;
    private static int COMM_COLUMN_NUM;
    private static int RES_COLUMN_NUM;
    private static String REQUIREMENT;
    private static String DESCRIPTION;
    private static String COMMENT;
    private static String RESULT;
    private static String PROJECT_NAME;

    /**
     * <p>Метод принимает в качестве параметра путь к файлу XLSX, читает его и инициализирует поля экземпляра класса Entry,
     * переданного в конструктор класса ParserImp</p>
     * @param path Путь к файлу с которым необходимо работать
     * @return Возвращает ArrayList <Entry> с которой работает DBControllerImp
     * */
     public List <Entry> parseXLS(String path, List <Entry> entries) throws IOException{

         this.entries = entries;
         File file = new File(path);
         Reader config = new InputStreamReader(new FileInputStream("D:/xparse.properties"), "UTF-8");

         Properties props = new Properties();
         try{
             props.load(config);
         } catch (FileNotFoundException e){
             System.out.println("File not found!");
             e.printStackTrace();
         } catch (IOException e){
             System.out.println("Can not read file");
             e.printStackTrace();
         }
         REQUIREMENT = props.getProperty("requirementColumn");
         DESCRIPTION = props.getProperty("descriptionColumn");
         COMMENT = props.getProperty("commentColumn");
         RESULT = props.getProperty("resultColumn");
         PROJECT_NAME = file.toString().substring(3);

         FileInputStream in = new FileInputStream(file);
         XSSFWorkbook wb = new XSSFWorkbook(in);

            for (Sheet sheet : wb ) {

                Iterator<Row> ri = sheet.rowIterator();

                while(ri.hasNext()){

                XSSFRow row = (XSSFRow)ri.next();

                if(row.getRowNum()==0) {

                    for (int i = 0; i < row.getLastCellNum(); i++) {

                        XSSFCell tmpColumn = row.getCell(i);

                        if (tmpColumn.getStringCellValue().equals(REQUIREMENT)) {
                            REQ_COLUMN_NUM = i;
                        }

                        else if (tmpColumn.getStringCellValue().equals(DESCRIPTION)) {
                            DESCR_COLUMN_NUM = i;
                        }
                        else if (tmpColumn.getStringCellValue().equals(COMMENT)) {
                            COMM_COLUMN_NUM = i;
                        }
                        else if (tmpColumn.getStringCellValue().equals(RESULT)) {
                            RES_COLUMN_NUM = i;
                        }
                    }
                }
                }
            }
                    for (Sheet sheets : wb ) {

                        Iterator<Row> rit = sheets.rowIterator();
                        if(rit.hasNext()){
                            rit.next();
                        }

                        while(rit.hasNext()){

                            XSSFRow currentRow = (XSSFRow)rit.next();
                            XSSFCell requirementColumn = currentRow.getCell(REQ_COLUMN_NUM);
                            XSSFCell descriptionColumn = currentRow.getCell(DESCR_COLUMN_NUM);
                            XSSFCell commentColumn = currentRow.getCell(COMM_COLUMN_NUM);
                            XSSFCell resultColumn = currentRow.getCell(RES_COLUMN_NUM);

                Entry entry = new Entry();

                switch(PROJECT_NAME)  {
                    case "project1.xlsx": entry.setProjectName("project_1");
                        break;
                    case "project2.xlsx": entry.setProjectName("project_2");
                        break;
                    default: entry.setProjectName(null);
                }

                if(requirementColumn != null){
                    entry.setRequirement(requirementColumn.getStringCellValue());
                }
                if(descriptionColumn != null){
                    entry.setDescription(descriptionColumn.getStringCellValue());
                }
                if(commentColumn != null) {
                    entry.setComment(commentColumn.getStringCellValue());
                }
                if(resultColumn != null){
                    entry.setResult(resultColumn.getStringCellValue());
                }
                entries.add(entry);
           }
     }
         return entries;
     }

    /**
     * <p>Метод принимает в качестве параметра путь к файлу JSON, читает его и инициализирует поля экземпляра класса Entry,
     * переданного в конструктор класса ParserImp</p>
     * @param path Путь к файлу с которым необходимо работать
     * @return Возвращает ArrayList <Entry> с которой работает DBControllerImp
     * */
    public List<Entry> parseJSON(String path, List<Entry> entries) throws IOException {

        this.entries = entries;

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null){
            buffer.append(s);
        }
        reader.close();

        String jsonString = buffer.toString();

        JSONObject obj = new JSONObject(jsonString);
        JSONArray actors = obj.getJSONArray("actors");

        for (int i = 0; i<actors.length(); i++){
            JSONObject actor = actors.getJSONObject(i);

            String firstName = actor.getString("firstName");
            String lastName = actor.getString("lastName");
            String address = actor.getString("address");
            String phone = actor.getString("phone");

            Entry entry = new Entry();
            entry.setFirstName(firstName);
            entry.setLastName(lastName);
            entry.setAddress(address);
            entry.setPhone(phone);
            entries.add(entry);
        }
        return entries;
    }
}



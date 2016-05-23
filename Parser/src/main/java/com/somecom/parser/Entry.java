package com.somecom.parser;

/**
 * Created by Cannon on 22.04.2016.
 * <p>Объект класса представляет сущность из читаемого файла (JSON, excel...)
 * Поля данного класса соответсвуют полям в читаемом файле и столбцам в БД</p>
 */
public class Entry {

    private String requirement;
    private String description;
    private String comment;
    private String result;
    private String projectName;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProjectName() {

        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

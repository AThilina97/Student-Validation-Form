package lk.ijse.dep10.app.model;

import lk.ijse.dep10.app.util.CourseType;
import lk.ijse.dep10.app.util.Gender;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String id;
    private String name;
    private Gender gender;
    private ArrayList<String> contacts;
    private String department;
    private ArrayList<String> modules;
    private ArrayList<String> selectModule;
    private CourseType courseType;


    public Student() {
    }

    public Student(String id, String name, Gender gender, ArrayList<String> contacts, String department,
                   ArrayList<String> modules, ArrayList<String> selectModule, CourseType courseType) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.contacts = contacts;
        this.department = department;
        this.modules = modules;
        this.selectModule = selectModule;
        this.courseType = courseType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<String> getModules() {
        return modules;
    }

    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }

    public ArrayList<String> getSelectModule() {
        return selectModule;
    }

    public void setSelectModule(ArrayList<String> selectModule) {
        this.selectModule = selectModule;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}

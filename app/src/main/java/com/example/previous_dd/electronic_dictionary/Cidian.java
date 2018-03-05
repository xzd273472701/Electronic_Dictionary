package com.example.previous_dd.electronic_dictionary;

/**
 * Created by previous_DD on 2017/6/13.
 */

public class Cidian {
    private int id;
    private String english;
    private String chinese;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Cidian(String english, String chinese) {

        this.english = english;
        this.chinese = chinese;
    }


    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public Cidian() {
    }

    @Override
    public String toString() {
        return "Cidian{" +
                "  english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}

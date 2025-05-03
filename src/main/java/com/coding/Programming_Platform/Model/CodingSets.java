package com.coding.Programming_Platform.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="codingsets")
public class CodingSets {
    @Id
    private int set_id;
    private String set_name;

    public CodingSets(){}

    public CodingSets(int id,String name){
        set_id=id;
        set_name=name;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }
}

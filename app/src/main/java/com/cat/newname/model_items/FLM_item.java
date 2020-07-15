package com.cat.newname.model_items;

public class FLM_item {
    int id;
    String name, email, repNo;

    public FLM_item(int id, String name, String email, String repNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.repNo = repNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepNo() {
        return repNo;
    }

    public void setRepNo(String repNo) {
        this.repNo = repNo;
    }
}

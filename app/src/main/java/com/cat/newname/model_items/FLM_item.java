package com.cat.newname.model_items;

public class FLM_item {


    int id;
    String name, email;
    int scored, target ,repNo;
    String percentage;

    public FLM_item(int id, String name, String email, int scored, int target, int repNo, String percentage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.scored = scored;
        this.target = target;
        this.repNo = repNo;
        this.percentage = percentage;
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

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getRepNo() {
        return repNo;
    }

    public void setRepNo(int repNo) {
        this.repNo = repNo;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}

package com.cat.newname.model_items;

public class Rep_item {

    int id;
    String name, email, type;
    int scored, target;
    String percentage;

    public Rep_item(int id, String name, String email, String type, int scored, int target, String percentage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.scored = scored;
        this.target = target;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}

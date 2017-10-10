package com.example.admin.kamathotelapp.Model;

/**
 * Created by USER on 8/10/2017.
 */

public class LegalEntityModel {
    String id;

    String legal_id;
    String value;
    String text;
    String parent_Ref;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLegal_id() {
        return legal_id;
    }

    public void setLegal_id(String legal_id) {
        this.legal_id = legal_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParent_Ref() {
        return parent_Ref;
    }

    public void setParent_Ref(String parent_Ref) {
        this.parent_Ref = parent_Ref;
    }



}

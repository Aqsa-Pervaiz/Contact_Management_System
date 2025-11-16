package com.example.demo.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class LabeledValue {
    private String label;  
    private String value;  

    public LabeledValue() {}

    public LabeledValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}

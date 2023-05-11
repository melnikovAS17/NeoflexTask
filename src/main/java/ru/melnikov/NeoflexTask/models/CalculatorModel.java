package ru.melnikov.NeoflexTask.models;


import org.springframework.stereotype.Component;

@Component
public class CalculatorModel {

    private float salary;

    private int days;

    private String exactDays;

    public CalculatorModel(){    }
    public CalculatorModel(float salary,int days, String exactDays){
        this.salary=salary;
        this.days = days;
        this.exactDays=exactDays;
    }
    public float getSalary(){return salary;}
    public void setSalary(float salary){this.salary=salary;}

    public String getExactDays() {
        return exactDays;
    }

    public void setExactDays(String exactDays) {
        this.exactDays = exactDays;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }


}

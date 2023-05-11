package ru.melnikov.NeoflexTask.models;

import java.util.ArrayList;

public class Calendar {
    int number;
    int amountOfDays;
    ArrayList<Integer> holidays;
    public int getNumber(){
        return number;
    }
    public ArrayList<Integer> getHolidays(){
        return holidays;
    }
    public int getAmountOfDays() {return amountOfDays;}
    //Конструктор устанавливает числа праздничных дней в конкретном месяце(2023 год) и передаёт их в массив
    public Calendar(int number, int amountOfDays){
        this.number = number;
        this.amountOfDays = amountOfDays;
        this.holidays = new ArrayList<>();
        {
            //Number - номер месяца(1 - январь и тд.)
            if (number == 1) {
                holidays.add(1);
                holidays.add(2);
                holidays.add(3);
                holidays.add(4);
                holidays.add(5);
                holidays.add(6);
                holidays.add(7);
                holidays.add(8);

            }
            if (number == 2) {
                holidays.add(23);
                holidays.add(24);
                holidays.add(25);
                holidays.add(26);
            }
            if (number == 3) {
                holidays.add(8);
            }
            if (number == 4) {
                holidays.add(29);
                holidays.add(30);
            }
            if (number == 5) {
                holidays.add(1);
                holidays.add(6);
                holidays.add(7);
                holidays.add(8);
                holidays.add(9);
            }
            if (number == 6) {
                holidays.add(10);
                holidays.add(11);
                holidays.add(12);
            }
            //С июля по октябрь, а ткаже декабрь(31 выходной день) гос. праздников в месяцах нет
            if (number == 11) {
                holidays.add(4);
                holidays.add(5);
                holidays.add(6);
            }
        }


    }
}

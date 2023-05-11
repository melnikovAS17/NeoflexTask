package ru.melnikov.NeoflexTask.dao;

import org.springframework.stereotype.Component;
import ru.melnikov.NeoflexTask.models.CalculatorModel;

import java.util.ArrayList;
//Класс имитирует взаимодействие с БД
@Component
public class CalculatorDAO {
    //Условная БД с калькулятором для расчёта без учёта праздничных и выходных дней
    ArrayList<CalculatorModel> calculatorModels = new ArrayList<>();
    public void setCalculatorModels(CalculatorModel calculatorModel){
        calculatorModels.add(calculatorModel);
    }
    public float getSalary(){return calculatorModels.get(calculatorModels.size()-1).getSalary();}
    public int getDays() {
        return calculatorModels.get(calculatorModels.size()-1).getDays();
    }
    public String getExactDays(){return calculatorModels.get(calculatorModels.size()-1).getExactDays();}

}

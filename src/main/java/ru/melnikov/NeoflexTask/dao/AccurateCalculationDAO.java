package ru.melnikov.NeoflexTask.dao;

import org.springframework.stereotype.Component;
import ru.melnikov.NeoflexTask.models.Calendar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
//Класс имитирует взаимодействие с БД
@Component
public class AccurateCalculationDAO {
    final CalculatorDAO calculatorDAO;

    //Условная БД с календарём праздничных дней в месяцах
    ArrayList<Calendar> calendar = new ArrayList<>();{
        calendar.add(new Calendar(0,0));
        calendar.add(new Calendar(1,31));
        calendar.add(new Calendar(2,28));
        calendar.add(new Calendar(3,31));
        calendar.add(new Calendar(4,30));
        calendar.add(new Calendar(5,31));
        calendar.add(new Calendar(6,30));
        calendar.add(new Calendar(7,31));
        calendar.add(new Calendar(8,31));
        calendar.add(new Calendar(9,30));
        calendar.add(new Calendar(10,31));
        calendar.add(new Calendar(11,30));
        calendar.add(new Calendar(12,31));

    }
    public AccurateCalculationDAO(CalculatorDAO calculatorDAO) {
        this.calculatorDAO = calculatorDAO;
    }
    public ArrayList<Integer> getMonthHolidays(int n){
        return calendar.get(n).getHolidays();
    }
    public int getAmountOfDays(int n){
        return calendar.get(n).getAmountOfDays();
    }
    //НДФЛ
    private static final float NDFL = 0.87f;
    //12f*29.3f - вычисление среднего количества дней в году
    private static final float averageNumberOfDaysInAYears = 12f*29.3f;
    public Number accurateCalculation()  {
        float salary = calculatorDAO.getSalary();
        String exactDays = calculatorDAO.getExactDays();
        int days = calculatorDAO.getDays();
        //Условие выбора расчёта, если поле точных дней ухода в отпуск "дд.мм-дд.мм" пустое, тогда расчёт без учёта
        //праздников
        if(calculatorDAO.getExactDays().equals("00.00-00.00")){
            float value = salary/(averageNumberOfDaysInAYears) * NDFL * (float) days;
            BigDecimal result = BigDecimal.valueOf(value);
            return result.setScale(2,RoundingMode.UP);
        }else{
            //Получаем числа из строки, которую вводит пользователь(дд.мм-дд.мм)
            ArrayList<Integer> numbers = new ArrayList<>();
            for (String part : exactDays.split("[.\\-]")) {
                numbers.add(Integer.parseInt(part));
            }
            //Количество дней отпуска вычислияется исходя из условий ввода(отпуск заканчивается в одном месяце или в следующем)
            int daysOfExactDays;
                if (numbers.get(1).equals(numbers.get(3))) {
                    daysOfExactDays = (numbers.get(2) - numbers.get(0));
                } else {
                    daysOfExactDays = getAmountOfDays(numbers.get(1)) - numbers.get(0) + numbers.get(2);
                }
                ArrayList<Integer> daysOfVacationInFirstMonth = new ArrayList<>();
                ArrayList<Integer> daysOfVacationINSecondMonth = new ArrayList<>();
                //По условию надо учесть при расчёте выходные дни, тк нет данных по сотруднику (дни больничных, прогулов и тп)
                //будем вычитать из среднего кол-ва дней в году все гос. праздники(неоплачиваемые дни)
                int unpaidDays = 0;
                for(int i = 0; i < 12; i++){
                    unpaidDays += getMonthHolidays(i).size();
                }
                //Далее в цикле будет заполняться массив, если месяц до "-" и после(дд.мм-дд.мм) одниковый, значит сотрудник
                //уходит и придёт с отпуска в один и тот же месяц, если месяца разные, нам надо учесть праздники в другом
                //месяце и соответственно вычесть их из числа оплачиваемых дней
                if (numbers.get(1).equals(numbers.get(3))) {
                    for (int number = numbers.get(0); number < numbers.get(0) + days; number++) {
                        daysOfVacationInFirstMonth.add(number);
                    }
                } else {
                    for (int number = numbers.get(0);
                         number < getAmountOfDays(numbers.get(1)); number++) {
                        daysOfVacationInFirstMonth.add(number);
                    }
                    for (int number = 1;
                         number < numbers.get(2); number++) {
                        daysOfVacationINSecondMonth.add(number);
                    }
                }
                int count = 0;
                ArrayList<Integer> massiveOfHolidaysFirstMonth = getMonthHolidays(numbers.get(1));
                ArrayList<Integer> massiveOfHolidaysSecondMonth = getMonthHolidays(numbers.get(3));
                for (Integer integer : massiveOfHolidaysFirstMonth) {
                    if (daysOfVacationInFirstMonth.contains(integer)) {
                        count++;
                    }
                }
                for (Integer integer : massiveOfHolidaysSecondMonth) {
                    if (daysOfVacationINSecondMonth.contains(integer)) {
                        count++;
                    }
                }

                float value = salary / ((averageNumberOfDaysInAYears)- unpaidDays) * ((float)daysOfExactDays - (float) count) * NDFL;
                BigDecimal result = new BigDecimal(value);
                return result.setScale(2,RoundingMode.UP);
        }
    }
}


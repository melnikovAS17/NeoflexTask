package ru.melnikov.NeoflexTask.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.melnikov.NeoflexTask.dao.AccurateCalculationDAO;
import ru.melnikov.NeoflexTask.dao.CalculatorDAO;
import ru.melnikov.NeoflexTask.models.CalculatorModel;

@Controller
public class MainController {
    final
    CalculatorModel calculatorModel;
    final
    AccurateCalculationDAO accurateCalculationDAO;
    final
    CalculatorDAO calculatorDAO;

    public MainController(CalculatorModel calculatorModel, CalculatorDAO calculatorDAO, AccurateCalculationDAO accurateCalculationDAO) {
        this.calculatorModel = calculatorModel;
        this.calculatorDAO = calculatorDAO;
        this.accurateCalculationDAO = accurateCalculationDAO;
    }
    @GetMapping("/calculacte")
    public String calculator( @ModelAttribute("calculator") CalculatorModel calculatorModel, Model model) {
            calculatorDAO.setCalculatorModels(calculatorModel);
        while(calculatorModel.getSalary()==0){
            return "Calculator";
        }
        Number result = accurateCalculationDAO.accurateCalculation();
        model.addAttribute("result", result);
        return "Calculator";
    }
}

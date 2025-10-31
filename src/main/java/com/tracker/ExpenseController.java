package com.tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExpenseController {
    
    private List<Expense> expenses = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String sort, Model model) {
        List<Expense> sortedExpenses = new ArrayList<>(expenses);
        
        // Sort based on parameter
        if ("amount-high".equals(sort)) {
            sortedExpenses.sort(Comparator.comparingDouble(Expense::getAmount).reversed());
        } else if ("amount-low".equals(sort)) {
            sortedExpenses.sort(Comparator.comparingDouble(Expense::getAmount));
        } else if ("date-new".equals(sort)) {
            sortedExpenses.sort(Comparator.comparing(Expense::getDate).reversed());
        } else if ("date-old".equals(sort)) {
            sortedExpenses.sort(Comparator.comparing(Expense::getDate));
        }
        
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("expenses", sortedExpenses);
        model.addAttribute("total", total);
        model.addAttribute("currentSort", sort);
        
        return "index";
    }

    @PostMapping("/add")
    public String addExpense(@RequestParam String project,
                             @RequestParam String category,
                             @RequestParam String description,
                             @RequestParam double amount,
                             @RequestParam LocalDate date) {
        Expense expense = new Expense(nextId++, project, category, amount, description, date);
        expenses.add(expense);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenses.removeIf(e -> e.getId().equals(id));
        return "redirect:/";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"expenses.csv\"");
        
        PrintWriter writer = response.getWriter();
        
        writer.println("Date,Project,Category,Description,Amount");
        
        for (Expense expense : expenses) {
            writer.println(String.format("%s,%s,%s,\"%s\",%.2f",
                expense.getDate(),
                expense.getProject(),
                expense.getCategory(),
                expense.getDescription().replace("\"", "\"\""),
                expense.getAmount()
            ));
        }
        
        writer.flush();
    }
}

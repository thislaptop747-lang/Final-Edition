package com.tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ExpenseController {
    
    private final Map<Long, Expense> expenses = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    @GetMapping("/")
    public String index(Model model) {
        List<Expense> expenseList = new ArrayList<>(expenses.values());
        expenseList.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
        double total = expenseList.stream().mapToDouble(Expense::getAmount).sum();
        model.addAttribute("expenses", expenseList);
        model.addAttribute("total", total);
        return "index";
    }

    @PostMapping("/add")
    public String addExpense(@RequestParam String project,
                           @RequestParam String category,
                           @RequestParam double amount,
                           @RequestParam String description,
                           @RequestParam String date) {
        Long id = idCounter.incrementAndGet();
        Expense expense = new Expense(id, project, category, amount, description, LocalDate.parse(date));
        expenses.put(id, expense);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenses.remove(id);
        return "redirect:/";
    }
}

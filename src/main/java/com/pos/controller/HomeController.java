package com.pos.controller;

import com.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PosTableService posTableService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalOrders", orderService.getAllOrders().size());
        model.addAttribute("todayRevenue", orderService.getRevenueInRange(
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0),
                LocalDateTime.now()
        ));
        model.addAttribute("occupiedTables", posTableService.getTablesByStatus("OCCUPIED").size());
        model.addAttribute("emptyTables", posTableService.getTablesByStatus("EMPTY").size());
        model.addAttribute("pendingOrders", orderService.getOrdersByStatus("PENDING").size());

        return "dashboard";
    }
}


package com.pos.controller;

import com.pos.entity.*;
import com.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PosTableService posTableService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("tables", posTableService.getAllTables());
        return "order-list";
    }

    @PostMapping("/quick-create/{tableId}")
    public String quickCreateOrder(@PathVariable Long tableId, Principal principal, RedirectAttributes redirect) {
        PosTable table = posTableService.getTableById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        User user = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setTable(table);
        order.setUser(user);
        Order saved = orderService.createOrder(order);
        posTableService.updateTableStatus(tableId, "OCCUPIED");

        redirect.addAttribute("orderId", saved.getId());
        return "redirect:/order/detail/{orderId}";
    }

    @GetMapping("/new/{tableId}")
    public String newOrder(@PathVariable Long tableId, Model model) {
        PosTable table = posTableService.getTableById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        Order order = new Order();
        order.setTable(table);

        model.addAttribute("order", order);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAvailableProducts());

        return "order-form";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, Principal principal, RedirectAttributes redirect) {
        User user = userService.getUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);
        Order saved = orderService.createOrder(order);

        // Update table status
        posTableService.updateTableStatus(order.getTable().getId(), "OCCUPIED");

        redirect.addAttribute("orderId", saved.getId());
        return "redirect:/order/detail/{orderId}";
    }

    // New intermediate route to find the active order for a table
    @GetMapping("/active/{tableId}")
    public String getActiveOrder(@PathVariable Long tableId, RedirectAttributes redirect) {
        Optional<Order> activeOrder = orderService.getActiveOrderByTable(tableId);
        
        if (activeOrder.isPresent()) {
            redirect.addAttribute("orderId", activeOrder.get().getId());
            return "redirect:/order/detail/{orderId}";
        } else {
            // Fallback: If somehow occupied but no pending order, go create a new one
            return "redirect:/order/new/" + tableId;
        }
    }

    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(orderId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAvailableProducts());

        return "order-detail";
    }

    @PostMapping("/detail/{orderId}/add-item")
    public String addOrderItem(@PathVariable Long orderId,
                              @RequestParam Long productId,
                              @RequestParam Integer quantity,
                              @RequestParam(required = false) String size,
                              @RequestParam(required = false) String note,
                              RedirectAttributes redirect) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(product);
        detail.setQuantity(quantity);
        detail.setSize(size);
        detail.setNote(note);
        detail.setUnitPrice(product.getPrice());

        orderService.addOrderDetail(detail);
        orderService.calculateOrderTotal(order);

        redirect.addAttribute("orderId", orderId);
        return "redirect:/order/detail/{orderId}";
    }

    @PostMapping("/detail/{orderId}/remove-item/{detailId}")
    public String removeOrderItem(@PathVariable Long orderId, @PathVariable Long detailId, RedirectAttributes redirect) {
        orderService.removeOrderDetail(detailId);

        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderService.calculateOrderTotal(order);

        redirect.addAttribute("orderId", orderId);
        return "redirect:/order/detail/{orderId}";
    }

    @GetMapping("/checkout/{orderId}")
    public String checkout(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(orderId));

        return "order-checkout";
    }

    @PostMapping("/checkout/{orderId}/pay")
    public String pay(@PathVariable Long orderId,
                     @RequestParam String paymentMethod,
                     RedirectAttributes redirect) {
        Order order = orderService.payOrder(orderId, paymentMethod);

        // Update table status
        posTableService.updateTableStatus(order.getTable().getId(), "EMPTY");

        redirect.addAttribute("orderId", orderId);
        return "redirect:/order/receipt/{orderId}";
    }

    @GetMapping("/receipt/{orderId}")
    public String receipt(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(orderId));

        return "order-receipt";
    }
}
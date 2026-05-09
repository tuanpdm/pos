package com.pos.controller;

import com.pos.entity.*;
import com.pos.service.*;
import com.pos.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PosTableService posTableService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/products")
    public String productList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin-products";
    }

    @GetMapping("/products/new")
    public String productForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin-product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirect) {
        productService.createProduct(product);
        redirect.addFlashAttribute("message", "Product created successfully");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin-product-form";
    }

    @PostMapping("/products/{id}/update")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, RedirectAttributes redirect) {
        productService.updateProduct(id, product);
        redirect.addFlashAttribute("message", "Product updated successfully");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirect) {
        productService.deleteProduct(id);
        redirect.addFlashAttribute("message", "Product deleted successfully");
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/toggle")
    public String toggleProductAvailability(@PathVariable Long id, RedirectAttributes redirect) {
        productService.toggleAvailability(id);
        redirect.addFlashAttribute("message", "Product availability updated");
        return "redirect:/admin/products";
    }

    @GetMapping("/categories")
    public String categoryList(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", new Category());
        return "admin-categories";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, RedirectAttributes redirect) {
        categoryService.createCategory(category);
        redirect.addFlashAttribute("message", "Category created successfully");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/update")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category, RedirectAttributes redirect) {
        categoryService.updateCategory(id, category);
        redirect.addFlashAttribute("message", "Category updated successfully");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirect) {
        categoryService.deleteCategory(id);
        redirect.addFlashAttribute("message", "Category deleted successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users";
    }

    @GetMapping("/users/new")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-user-form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirect) {
        userService.createUser(user);
        redirect.addFlashAttribute("message", "User created successfully");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin-user-form";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, RedirectAttributes redirect) {
        userService.updateUser(id, user);
        redirect.addFlashAttribute("message", "User updated successfully");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirect) {
        userService.toggleUserStatus(id);
        redirect.addFlashAttribute("message", "User status updated");
        return "redirect:/admin/users";
    }

    @GetMapping("/tables")
    public String tableList(Model model) {
        model.addAttribute("tables", posTableService.getAllTables());
        return "admin-tables";
    }

    @GetMapping("/tables/new")
    public String tableForm(Model model) {
        model.addAttribute("table", new PosTable());
        return "admin-table-form";
    }

    @PostMapping("/tables/save")
    public String saveTable(@ModelAttribute PosTable table, RedirectAttributes redirect) {
        posTableService.createTable(table);
        redirect.addFlashAttribute("message", "Table created successfully");
        return "redirect:/admin/tables";
    }

    @PostMapping("/tables/{id}/delete")
    public String deleteTable(@PathVariable Long id, RedirectAttributes redirect) {
        posTableService.deleteTable(id);
        redirect.addFlashAttribute("message", "Table deleted successfully");
        return "redirect:/admin/tables";
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        model.addAttribute("todayOrders", orderService.getOrdersInRange(startOfDay, endOfDay));
        model.addAttribute("todayRevenue", orderService.getRevenueInRange(startOfDay, endOfDay));
        model.addAttribute("totalOrders", orderService.getAllOrders().size());

        return "admin-reports";
    }
}

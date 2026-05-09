package com.pos.config;

import com.pos.entity.*;
import com.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PosTableRepository posTableRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role cashierRole = new Role();
            cashierRole.setName("ROLE_CASHIER");
            roleRepository.save(cashierRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setRole(roleRepository.findByName("ROLE_ADMIN"));
            admin.setStatus(true);
            userRepository.save(admin);

            User cashier = new User();
            cashier.setUsername("cashier");
            cashier.setPassword(passwordEncoder.encode("cashier123"));
            cashier.setFullName("Nhân viên Thu ngân");
            cashier.setRole(roleRepository.findByName("ROLE_CASHIER"));
            cashier.setStatus(true);
            userRepository.save(cashier);
        }

        if (categoryRepository.findAll().isEmpty()) {
            String[] categoryNames = {"Cà phê", "Trà sữa", "Nước ép", "Bánh ngọt", "Đồ ăn nhẹ"};
            for (String name : categoryNames) {
                Category category = new Category();
                category.setName(name);
                categoryRepository.save(category);
            }
        }

        if (productRepository.findAll().isEmpty()) {
            Category coffeeCategory = categoryRepository.findByName("Cà phê");
            Category teaCategory = categoryRepository.findByName("Trà sữa");
            Category juiceCategory = categoryRepository.findByName("Nước ép");

            Product espresso = new Product();
            espresso.setCategory(coffeeCategory);
            espresso.setName("Espresso");
            espresso.setPrice(new BigDecimal("25000"));
            espresso.setIsAvailable(true);
            espresso.setDescription("Cà phê Espresso đậm đà");
            productRepository.save(espresso);

            Product americano = new Product();
            americano.setCategory(coffeeCategory);
            americano.setName("Americano");
            americano.setPrice(new BigDecimal("30000"));
            americano.setIsAvailable(true);
            americano.setDescription("Cà phê Americano");
            productRepository.save(americano);

            Product cappuccino = new Product();
            cappuccino.setCategory(coffeeCategory);
            cappuccino.setName("Cappuccino");
            cappuccino.setPrice(new BigDecimal("40000"));
            cappuccino.setIsAvailable(true);
            cappuccino.setDescription("Cappuccino với sua tuoi");
            productRepository.save(cappuccino);

            Product milkTea = new Product();
            milkTea.setCategory(teaCategory);
            milkTea.setName("Trà sữa thái");
            milkTea.setPrice(new BigDecimal("35000"));
            milkTea.setIsAvailable(true);
            milkTea.setDescription("Trà sữa thái ngon");
            productRepository.save(milkTea);

            Product orangeJuice = new Product();
            orangeJuice.setCategory(juiceCategory);
            orangeJuice.setName("Nước cam");
            orangeJuice.setPrice(new BigDecimal("25000"));
            orangeJuice.setIsAvailable(true);
            orangeJuice.setDescription("Nước cam tươi");
            productRepository.save(orangeJuice);
        }

        if (posTableRepository.findAll().isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                PosTable table = new PosTable();
                table.setName("Bàn " + i);
                table.setStatus("EMPTY");
                table.setDescription("Bàn phục vụ " + i);
                posTableRepository.save(table);
            }
        }
    }
}


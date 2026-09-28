package com.afreen.supplychain_iq.config;

import com.afreen.supplychain_iq.entity.*;
import com.afreen.supplychain_iq.enums.OrderStatus;
import com.afreen.supplychain_iq.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public void run(String... args) {
        if (supplierRepository.count() > 0) return; // don't duplicate on restart

        Supplier s1 = new Supplier();
        s1.setName("Global Textiles Ltd");
        s1.setEmail("contact@globaltextiles.com");
        s1.setPhone("+91-9876543210");
        s1.setAddress("Hyderabad, Telangana");
        s1.setReliabilityScore(BigDecimal.valueOf(0.88));
        supplierRepository.save(s1);

        Supplier s2 = new Supplier();
        s2.setName("Prime Electronics Supply");
        s2.setEmail("sales@primeelectronics.com");
        s2.setPhone("+91-9123456780");
        s2.setAddress("Bengaluru, Karnataka");
        s2.setReliabilityScore(BigDecimal.valueOf(0.72));
        supplierRepository.save(s2);

        Product p1 = new Product();
        p1.setName("Cotton Fabric Roll");
        p1.setSku("TXT-001");
        p1.setDescription("100% cotton, 50m roll");
        p1.setUnitPrice(BigDecimal.valueOf(450.00));
        p1.setCurrentStock(120);
        p1.setReorderThreshold(30);
        p1.setSupplier(s1);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Circuit Board Module");
        p2.setSku("ELC-014");
        p2.setDescription("Arduino-compatible board");
        p2.setUnitPrice(BigDecimal.valueOf(299.00));
        p2.setCurrentStock(8); // intentionally low stock
        p2.setReorderThreshold(15);
        p2.setSupplier(s2);
        productRepository.save(p2);

        PurchaseOrder po1 = new PurchaseOrder();
        po1.setOrderNumber("PO-SAMPLE01");
        po1.setProduct(p2);
        po1.setSupplier(s2);
        po1.setQuantity(100);
        po1.setOrderDate(LocalDate.now().minusDays(3));
        po1.setExpectedDeliveryDate(LocalDate.now().plusDays(4));
        po1.setStatus(OrderStatus.APPROVED);
        purchaseOrderRepository.save(po1);

        System.out.println("Sample data loaded successfully!");
    }
}

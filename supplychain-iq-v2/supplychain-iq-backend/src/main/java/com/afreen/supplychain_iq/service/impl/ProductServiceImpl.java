package com.afreen.supplychain_iq.service.impl;

import com.afreen.supplychain_iq.dto.ProductDTO;
import com.afreen.supplychain_iq.entity.Product;
import com.afreen.supplychain_iq.entity.Supplier;
import com.afreen.supplychain_iq.exception.ResourceNotFoundException;
import com.afreen.supplychain_iq.repository.ProductRepository;
import com.afreen.supplychain_iq.repository.SupplierRepository;
import com.afreen.supplychain_iq.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        Product product = toEntity(dto, supplier);
        return toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO getById(Long id) {
        return toDTO(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getLowStock() {
        return productRepository.findAll().stream()
                .filter(p -> p.getCurrentStock() <= p.getReorderThreshold())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        existing.setName(dto.getName());
        existing.setSku(dto.getSku());
        existing.setDescription(dto.getDescription());
        existing.setUnitPrice(dto.getUnitPrice());
        existing.setCurrentStock(dto.getCurrentStock());
        existing.setReorderThreshold(dto.getReorderThreshold());
        if (dto.getSupplierId() != null && !dto.getSupplierId().equals(existing.getSupplier().getId())) {
            Supplier newSupplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
            existing.setSupplier(newSupplier);
        }
        return toDTO(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private Product toEntity(ProductDTO dto, Supplier supplier) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setDescription(dto.getDescription());
        product.setUnitPrice(dto.getUnitPrice());
        product.setCurrentStock(dto.getCurrentStock());
        product.setReorderThreshold(dto.getReorderThreshold());
        product.setSupplier(supplier);
        return product;
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSku(product.getSku());
        dto.setDescription(product.getDescription());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setCurrentStock(product.getCurrentStock());
        dto.setReorderThreshold(product.getReorderThreshold());
        dto.setSupplierId(product.getSupplier().getId());
        return dto;
    }
}

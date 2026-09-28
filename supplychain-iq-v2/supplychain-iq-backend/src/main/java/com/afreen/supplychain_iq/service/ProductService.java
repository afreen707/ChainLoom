package com.afreen.supplychain_iq.service;

import com.afreen.supplychain_iq.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    ProductDTO getById(Long id);
    List<ProductDTO> getAll();
    List<ProductDTO> getLowStock();
    ProductDTO update(Long id, ProductDTO dto);
    void delete(Long id);
}

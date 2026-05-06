package com.smartlogix.msinventario.repository;

import com.smartlogix.msinventario.model.Producto;
import com.smartlogix.msinventario.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(CategoriaProducto categoria);
    List<Producto> findByStockGreaterThan(Integer stock);
}
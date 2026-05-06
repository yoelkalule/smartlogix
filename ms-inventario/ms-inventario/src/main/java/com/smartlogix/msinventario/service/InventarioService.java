package com.smartlogix.msinventario.service;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.model.CategoriaProducto;
import com.smartlogix.msinventario.model.Producto;
import com.smartlogix.msinventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final ProductoRepository productoRepository;

    public InventarioService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(CategoriaProducto.valueOf(request.getCategoria().toUpperCase()));
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public Producto actualizarStock(Long id, Integer cantidad) {
        Producto producto = obtenerProducto(id);
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para producto: " + id);
        }
        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
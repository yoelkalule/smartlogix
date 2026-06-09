package com.smartlogix.msinventario.service;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.dto.ProductoResponse;
import com.smartlogix.msinventario.model.CategoriaProducto;
import com.smartlogix.msinventario.model.Producto;
import com.smartlogix.msinventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    private final ProductoRepository productoRepository;

    public InventarioService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponse crearProducto(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(CategoriaProducto.valueOf(request.getCategoria().toUpperCase()));

        Producto guardado = productoRepository.save(producto);
        return convertirAResponse(guardado);
    }

    public List<ProductoResponse> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse obtenerProducto(Long id) {
        return convertirAResponse(buscarProductoEntidad(id));
    }

    public ProductoResponse actualizarStock(Long id, Integer cantidad) {
        Producto producto = buscarProductoEntidad(id);

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para producto: " + id);
        }

        producto.setStock(producto.getStock() - cantidad);

        Producto actualizado = productoRepository.save(producto);

        return convertirAResponse(actualizado);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public ProductoResponse devolverStock(Long id, Integer cantidad) {
        Producto producto = buscarProductoEntidad(id);

        producto.setStock(producto.getStock() + cantidad);

        Producto actualizado = productoRepository.save(producto);

        return convertirAResponse(actualizado);
    }

    private Producto buscarProductoEntidad(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    private ProductoResponse convertirAResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria().name()
        );
    }
}
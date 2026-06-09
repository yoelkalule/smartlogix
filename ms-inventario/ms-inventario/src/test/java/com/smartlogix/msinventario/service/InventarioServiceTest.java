package com.smartlogix.msinventario.service;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.dto.ProductoResponse;
import com.smartlogix.msinventario.model.CategoriaProducto;
import com.smartlogix.msinventario.model.Producto;
import com.smartlogix.msinventario.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void crearProducto_conDatosValidos_debeRetornarProductoResponse() {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Mouse");
        request.setDescripcion("Mouse gamer");
        request.setPrecio(19990.0);
        request.setStock(20);
        request.setCategoria("ELECTRONICA");

        Producto guardado = new Producto();
        guardado.setId(1L);
        guardado.setNombre("Mouse");
        guardado.setDescripcion("Mouse gamer");
        guardado.setPrecio(19990.0);
        guardado.setStock(20);
        guardado.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);

        ProductoResponse response = inventarioService.crearProducto(request);

        assertEquals(1L, response.getId());
        assertEquals("Mouse", response.getNombre());
        assertEquals("Mouse gamer", response.getDescripcion());
        assertEquals(19990.0, response.getPrecio());
        assertEquals(20, response.getStock());
        assertEquals("ELECTRONICA", response.getCategoria());

        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void actualizarStock_conStockInsuficiente_debeLanzarExcepcion() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Mouse");
        producto.setDescripcion("Mouse gamer");
        producto.setPrecio(19990.0);
        producto.setStock(2);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventarioService.actualizarStock(1L, 5);
        });

        assertEquals("Stock insuficiente para producto: 1", exception.getMessage());

        verify(productoRepository).findById(1L);
        verify(productoRepository, never()).save(any(Producto.class));
    }
}
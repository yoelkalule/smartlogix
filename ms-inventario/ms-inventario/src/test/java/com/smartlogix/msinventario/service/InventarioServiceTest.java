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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void crearProducto() {

        ProductoRequest request = new ProductoRequest();
        request.setNombre("Mouse");
        request.setDescripcion("Mouse gamer");
        request.setPrecio(19990.0);
        request.setStock(20);
        request.setCategoria("ELECTRONICA");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Mouse");
        producto.setDescripcion("Mouse gamer");
        producto.setPrecio(19990.0);
        producto.setStock(20);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.save(any()))
                .thenReturn(producto);

        ProductoResponse response =
                inventarioService.crearProducto(request);

        assertEquals(1L, response.getId());
        assertEquals("Mouse", response.getNombre());
        assertEquals("Mouse gamer", response.getDescripcion());
        assertEquals(19990.0, response.getPrecio());
        assertEquals(20, response.getStock());
        assertEquals("ELECTRONICA", response.getCategoria());

        verify(productoRepository).save(any());
    }

    @Test
    void listarProductos() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Mouse");
        producto.setDescripcion("Mouse gamer");
        producto.setPrecio(19990.0);
        producto.setStock(20);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findAll())
                .thenReturn(List.of(producto));

        List<ProductoResponse> lista =
                inventarioService.listarProductos();

        assertEquals(1, lista.size());
        assertEquals("Mouse", lista.get(0).getNombre());

        verify(productoRepository).findAll();
    }

    @Test
    void obtenerProducto_existente() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Mouse");
        producto.setDescripcion("Mouse gamer");
        producto.setPrecio(19990.0);
        producto.setStock(20);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        ProductoResponse response =
                inventarioService.obtenerProducto(1L);

        assertEquals(1L, response.getId());

        verify(productoRepository).findById(1L);
    }

    @Test
    void obtenerProducto_inexistente() {

        when(productoRepository.findById(100L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> inventarioService.obtenerProducto(100L));

        assertEquals(
                "Producto no encontrado: 100",
                ex.getMessage());

        verify(productoRepository).findById(100L);
    }

    @Test
    void actualizarStock() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(productoRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        ProductoResponse response =
                inventarioService.actualizarStock(1L, 5);

        assertEquals(5, response.getStock());

        verify(productoRepository).save(any());
    }

    @Test
    void actualizarStock_insuficiente() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(2);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> inventarioService.actualizarStock(1L, 5));

        assertEquals(
                "Stock insuficiente para producto: 1",
                ex.getMessage());

        verify(productoRepository, never()).save(any());
    }

    @Test
    void devolverStock() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);
        producto.setCategoria(CategoriaProducto.ELECTRONICA);

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(producto));

        when(productoRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        ProductoResponse response =
                inventarioService.devolverStock(1L, 5);

        assertEquals(15, response.getStock());

        verify(productoRepository).save(any());
    }

    @Test
    void eliminarProducto() {

        doNothing().when(productoRepository)
                .deleteById(1L);

        inventarioService.eliminarProducto(1L);

        verify(productoRepository).deleteById(1L);
    }

}
package com.smartlogix.msinventario.controller;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.dto.ProductoResponse;
import com.smartlogix.msinventario.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioControllerTest {

    private InventarioService inventarioService;
    private InventarioController controller;

    @BeforeEach
    void setUp() {
        inventarioService = Mockito.mock(InventarioService.class);
        controller = new InventarioController(inventarioService);
    }

    @Test
    void testEndpoint() {
        assertEquals("ms-inventario funcionando", controller.test());
    }

    @Test
    void crearProducto() {

        ProductoRequest request = new ProductoRequest();
        request.setNombre("Mouse");
        request.setDescripcion("Gaming");
        request.setPrecio(10000.0);
        request.setStock(10);
        request.setCategoria("ELECTRONICA");

        ProductoResponse response =
                new ProductoResponse(
                        1L,
                        "Mouse",
                        "Gaming",
                        10000.0,
                        10,
                        "ELECTRONICA"
                );

        when(inventarioService.crearProducto(request))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.crear(request);

        assertEquals(200, resultado.getStatusCode().value());
        assertEquals(response, resultado.getBody());

        verify(inventarioService).crearProducto(request);
    }

    @Test
    void listarProductos() {

        List<ProductoResponse> lista = List.of(
                new ProductoResponse(
                        1L,
                        "Mouse",
                        "Gaming",
                        10000.0,
                        10,
                        "ELECTRONICA"
                )
        );

        when(inventarioService.listarProductos()).thenReturn(lista);

        ResponseEntity<List<ProductoResponse>> resultado =
                controller.listar();

        assertEquals(200, resultado.getStatusCode().value());
        assertEquals(1, resultado.getBody().size());

        verify(inventarioService).listarProductos();
    }

    @Test
    void obtenerProducto() {

        ProductoResponse response =
                new ProductoResponse(
                        1L,
                        "Mouse",
                        "Gaming",
                        10000.0,
                        10,
                        "ELECTRONICA"
                );

        when(inventarioService.obtenerProducto(1L))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.obtener(1L);

        assertEquals(200, resultado.getStatusCode().value());
        assertEquals(response, resultado.getBody());

        verify(inventarioService).obtenerProducto(1L);
    }

    @Test
    void actualizarStock() {

        ProductoResponse response =
                new ProductoResponse(
                        1L,
                        "Mouse",
                        "Gaming",
                        5.0,
                        20,
                        "ELECTRONICA"
                );

        when(inventarioService.actualizarStock(1L,5))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.actualizarStock(1L,5);

        assertEquals(200, resultado.getStatusCode().value());

        verify(inventarioService)
                .actualizarStock(1L,5);
    }

    @Test
    void devolverStock() {

        ProductoResponse response =
                new ProductoResponse(
                        1L,
                        "Mouse",
                        "Gaming",
                        5.0,
                        30,
                        "ELECTRONICA"
                );

        when(inventarioService.devolverStock(1L,10))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.devolverStock(1L,10);

        assertEquals(200, resultado.getStatusCode().value());

        verify(inventarioService)
                .devolverStock(1L,10);
    }

    @Test
    void eliminarProducto() {

        doNothing().when(inventarioService)
                .eliminarProducto(1L);

        ResponseEntity<Void> resultado =
                controller.eliminar(1L);

        assertEquals(204, resultado.getStatusCode().value());

        verify(inventarioService)
                .eliminarProducto(1L);
    }
}
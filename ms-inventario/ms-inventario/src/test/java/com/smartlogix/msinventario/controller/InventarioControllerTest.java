package com.smartlogix.msinventario.controller;

import com.smartlogix.msinventario.dto.ProductoRequest;
import com.smartlogix.msinventario.dto.ProductoResponse;
import com.smartlogix.msinventario.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InventarioControllerTest {

    private InventarioService inventarioService;
    private InventarioController controller;

    @BeforeEach
    void setUp() {
        inventarioService = Mockito.mock(InventarioService.class);
        controller = new InventarioController(inventarioService);
    }

    private ProductoResponse productoResponse() {
        return new ProductoResponse(
                1L,
                "Mouse",
                "Mouse gamer",
                19990.0,
                20,
                "ELECTRONICA"
        );
    }

    @Test
    void testEndpoint() {
        assertEquals("ms-inventario funcionando", controller.test());
    }

    @Test
    void crearProducto() {

        ProductoRequest request = new ProductoRequest();

        ProductoResponse response = productoResponse();

        when(inventarioService.crearProducto(request))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.crear(request);

        assertEquals(response, resultado.getBody());

        verify(inventarioService).crearProducto(request);
    }

    @Test
    void listarProductos() {

        List<ProductoResponse> lista =
                List.of(productoResponse());

        when(inventarioService.listarProductos())
                .thenReturn(lista);

        ResponseEntity<List<ProductoResponse>> resultado =
                controller.listar();

        assertEquals(lista, resultado.getBody());

        verify(inventarioService).listarProductos();
    }

    @Test
    void obtenerProducto() {

        ProductoResponse response = productoResponse();

        when(inventarioService.obtenerProducto(1L))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.obtener(1L);

        assertEquals(response, resultado.getBody());

        verify(inventarioService).obtenerProducto(1L);
    }

    @Test
    void actualizarStock() {

        ProductoResponse response = productoResponse();

        when(inventarioService.actualizarStock(1L, 5))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.actualizarStock(1L, 5);

        assertEquals(response, resultado.getBody());

        verify(inventarioService)
                .actualizarStock(1L, 5);
    }

    @Test
    void eliminarProducto() {

        ResponseEntity<Void> resultado =
                controller.eliminar(1L);

        assertEquals(204, resultado.getStatusCode().value());

        verify(inventarioService)
                .eliminarProducto(1L);
    }

    @Test
    void devolverStock() {

        ProductoResponse response = productoResponse();

        when(inventarioService.devolverStock(1L, 5))
                .thenReturn(response);

        ResponseEntity<ProductoResponse> resultado =
                controller.devolverStock(1L, 5);

        assertEquals(response, resultado.getBody());

        verify(inventarioService)
                .devolverStock(1L, 5);
    }
}
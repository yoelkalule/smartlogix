package com.smartlogix.msinventario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MsInventarioApplicationTest {

    @Test
    void contexto() {
        assertDoesNotThrow(MsInventarioApplication::new);
    }
}
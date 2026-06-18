package com.smartlogix.msusuarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MsUsuariosApplicationTest {

    @Test
    void contexto() {

        MsUsuariosApplication app =
                new MsUsuariosApplication();

        assertNotNull(app);
    }
}
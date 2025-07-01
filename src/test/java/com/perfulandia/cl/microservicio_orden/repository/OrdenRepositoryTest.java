package com.perfulandia.cl.microservicio_orden.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import com.perfulandia.cl.microservicio_orden.model.Orden;
import java.util.Date;
import org.junit.jupiter.api.Assertions;


public class OrdenRepositoryTest {

    @Autowired
    private OrdenRepository ordenRepository;

    @Test
    public void testFindById() {
        
    }
}

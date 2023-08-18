package com.productos.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductoTest {

    @Autowired
    private ProductoRepositorio repositorio;

    @Test
    @Rollback(false)
    @Order(1)
    public void testGuardarProducto(){
        Producto producto = new Producto("Impresora Intel", 900);
        Producto productoGuardado = repositorio.save(producto);

        assertNotNull(productoGuardado);

    }

    @Test
    @Order(2)
    public void testBuscarProductoPorNombre(){
        String nombre = "Impresora Intel";
        Producto producto = repositorio.findByNombre(nombre);

        assertThat(producto.getNombre()).isEqualTo(nombre);
    }

     @Test
     @Order(3)
    public void testBuscarProductoPorNombreExistente(){
        String nombre = "Televisor Samsung HD222";
        Producto producto = repositorio.findByNombre(nombre);

        assertNull(producto);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testActualizarProducto(){
      String nombreProducto = "Televisor HD2"; //El nuevo valor

      Producto producto = new Producto(nombreProducto, 2000); //Valores nuevos
      producto.setId(2); //ID del producto a actualizar

      repositorio.save(producto);

      Producto productoActualizado = repositorio.findByNombre(nombreProducto);
      assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);

    }

    @Test
    @Order(5)
    public void testListarProducto(){
        List<Producto> productos = (List<Producto>) repositorio.findAll();
        for (Producto producto : productos) {
                System.out.println(producto);
        }
        assertThat(productos).size().isGreaterThan(0);

        
    }

    
    @Test
    @Rollback(false)
    @Order(6)
    public void testEliminarProducto(){
       Integer id=3;

       boolean esExistenteAntesDeEliminar= repositorio.findById(id).isPresent();

       repositorio.deleteById(id);

       boolean NoExisteAntesDeEliminar= repositorio.findById(id).isPresent();

       assertTrue(esExistenteAntesDeEliminar);

       assertFalse(NoExisteAntesDeEliminar);




        
    }


    
}

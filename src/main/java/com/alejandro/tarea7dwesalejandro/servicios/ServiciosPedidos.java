package com.alejandro.tarea7dwesalejandro.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.tarea7dwesalejandro.modelo.Carrito;
import com.alejandro.tarea7dwesalejandro.modelo.Ejemplares;
import com.alejandro.tarea7dwesalejandro.modelo.EstadoPedido;
import com.alejandro.tarea7dwesalejandro.modelo.ItemCarrito;
import com.alejandro.tarea7dwesalejandro.modelo.ItemPedido;
import com.alejandro.tarea7dwesalejandro.modelo.Mensajes;
import com.alejandro.tarea7dwesalejandro.modelo.Pedido;
import com.alejandro.tarea7dwesalejandro.modelo.Personas;
import com.alejandro.tarea7dwesalejandro.modelo.Plantas;
import com.alejandro.tarea7dwesalejandro.repositorios.EjemplaresRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.ItemPedidoRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.MensajesRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PedidoRepository;
import com.alejandro.tarea7dwesalejandro.repositorios.PlantasRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiciosPedidos {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EjemplaresRepository ejemplaresRepository;
    
    @Autowired
    private MensajesRepository mensajesRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ServiciosPersonas serviciosPersonas;

    @Autowired
    private PlantasRepository plantasRepository;

    @Transactional
    public void registrarPedido(Carrito carrito, Personas cliente) {
        List<ItemCarrito> itemsCarrito = carrito.getItems();

        if (itemsCarrito == null || itemsCarrito.isEmpty()) {
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDate.now());
        pedido.setPersona(cliente);
        pedidoRepository.save(pedido);

        for (ItemCarrito itemCarrito : itemsCarrito) {
            Plantas planta = plantasRepository.findById(itemCarrito.getCodigo())
                .orElseThrow(() -> new RuntimeException("Planta no encontrada: " + itemCarrito.getCodigo()));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setCantidad(itemCarrito.getCantidad());
            itemPedido.setPlanta(planta);

            int cantidad = itemCarrito.getCantidad();

            List<Ejemplares> disponibles = ejemplaresRepository
                .findFirstNDisponiblesPorPlanta(planta.getCodigo(), cantidad);

            if (disponibles.size() < cantidad) {
                throw new RuntimeException("No hay suficientes ejemplares disponibles para la planta: " + planta.getCodigo());
            }

            for (Ejemplares ej : disponibles) {
                ej.setDisponible(false);
                ejemplaresRepository.save(ej);

                Mensajes anotacion = new Mensajes();
                anotacion.setEjemplar(ej);
                anotacion.setPersona(cliente);
                anotacion.setFecha(LocalDateTime.now());
                anotacion.setMensaje("El cliente " + cliente.getNombre() + " compró el ejemplar " +
                    ej.getNombre() + " el día " + anotacion.getFecha().toLocalDate() +
                    " en el pedido " + pedido.getId());
                mensajesRepository.save(anotacion);
            }

            itemPedidoRepository.save(itemPedido);
        }

        carrito.getItems().clear();
    }
    
    @Transactional
    public void actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }
    
    public List<Pedido> obtenerPedidosDePersona(Personas persona) {
        return pedidoRepository.findWithItemsByPersona(persona);
    }
    
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }
    
    @Transactional
    public List<Pedido> filtrarPedidosPorFecha(LocalDate desde, LocalDate hasta) {
        List<Pedido> pedidos = pedidoRepository.findByFechaBetween(desde, hasta);

        for (Pedido p : pedidos) {
            p.getItems().size();
        }

        return pedidos;
    }
}


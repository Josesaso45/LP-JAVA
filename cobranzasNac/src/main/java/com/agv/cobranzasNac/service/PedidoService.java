package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.model.Pedido;
import com.agv.cobranzasNac.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio CRUD para la entidad Pedido.
 * Provee métodos para operaciones básicas.
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Obtiene todos los pedidos.
     * @return Lista de todos los Pedidos.
     */
    public List<Pedido> listarTodos() {
    	// ¡Debemos modificar el repositorio para que esto funcione!
        return pedidoRepository.findByActivoTrue();    
    }

    /**
     * Busca un pedido por su ID.
     * @param id El ID del pedido.
     * @return Un Optional que puede contener el Pedido si se encuentra.
     */
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Guarda un nuevo pedido o actualiza uno existente.
     * @param pedido El Pedido a guardar.
     * @return El Pedido guardado (con su ID asignado).
     */
    public Pedido guardar(Pedido pedido) {
        // Aquí se podría añadir lógica de validación antes de guardar
        return pedidoRepository.save(pedido);
    }

    /**
     * Elimina un pedido por su ID.
     * @param id El ID del pedido a eliminar.
     */
    public void eliminar(Long id) {
    	// 1. Buscamos el pedido
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        
        // 2. Si existe...
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setActivo(false); // 3. Lo marcamos como inactivo
            pedidoRepository.save(pedido); // 4. Guardamos el cambio
        }
        // Si no existe, simplemente no hace nada.
    }
}
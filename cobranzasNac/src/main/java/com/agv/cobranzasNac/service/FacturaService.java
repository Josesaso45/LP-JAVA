package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.repository.FacturaRepository;

import com.agv.cobranzasNac.model.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	/**
	 * Obtiene todas las facturas ACTIVAS.
	 */
	public List<Factura> listarTodos() {
		// 🔑 CAMBIO: Ya no usamos findAll(), usamos el método que creamos
		return facturaRepository.findByActivoTrue();
	}
	
	/**
	 * Busca una factura por id.
	 */
	public Optional<Factura> buscarPorId(Long id) {
		return facturaRepository.findById(id);
	}
	
	/**
	 * Guarda o actualiza una factura.
	 */
	public Factura guardar(Factura factura) {
		return facturaRepository.save(factura);
	}
	
	/**
	 * Elimina una factura (Baja Lógica).
	 * En lugar de borrarla, la marca como inactiva.
	 */
	public void eliminar(Long id) {
		// 🔑 CAMBIO CLAVE: Implementamos la Baja Lógica
		
		// 1. Buscamos la factura
		Optional<Factura> facturaOpt = facturaRepository.findById(id);
		
		// 2. Si existe...
		if (facturaOpt.isPresent()) {
			Factura factura = facturaOpt.get();
			factura.setActivo(false); // 3. La marcamos como inactiva
			facturaRepository.save(factura); // 4. Guardamos el cambio (es un update)
		}
	}
}
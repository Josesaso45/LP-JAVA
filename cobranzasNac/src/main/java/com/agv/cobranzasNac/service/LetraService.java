package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.repository.LetraRepository;
import com.agv.cobranzasNac.model.Letra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LetraService {
	
	@Autowired
	private LetraRepository letraRepository;
	
	/**
	 * Obtiene todas las letras ACTIVAS.
	 */
	public List<Letra> obtenerTodasLasLetras() {
		// 🔑 CAMBIO: Usamos el método de baja lógica
		return letraRepository.findByActivoTrue();
	}
	
	//buscar letra por id
	public Optional<Letra> obtenerLetraPorId(Long id) {
		return letraRepository.findById(id);
	}
	
	//guardar o actualizar letra
	public Letra guardarLetra(Letra letra) {
		return letraRepository.save(letra);
	}
	
	/**
	 * Elimina una letra (Baja Lógica).
	 * En lugar de borrarla, la marca como inactiva.
	 */
	public void eliminarLetra(Long id) {
		// 🔑 CAMBIO CLAVE: Implementamos la Baja Lógica
		Optional<Letra> letraOpt = letraRepository.findById(id);
		
		if (letraOpt.isPresent()) {
			Letra letra = letraOpt.get();
			letra.setActivo(false); // La marcamos como inactiva
			letraRepository.save(letra); // Guardamos el cambio
		}
	}
}
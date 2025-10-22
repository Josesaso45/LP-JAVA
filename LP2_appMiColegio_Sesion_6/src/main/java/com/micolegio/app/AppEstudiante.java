package com.micolegio.app;

import com.micolegio.app.JPAUtil;
import java.time.LocalDate;
import java.util.Scanner;

import com.micolegio.model.Estudiante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppEstudiante {
	public static void main(String[] args) {
		
		
		//Persistencia
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		//Iniciando Variables
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		while (opcion != 5) {
			System.out.println("Registro de Estudiante \n");

			System.out.println("\n-- CRUD ESTUDIANTE --");
			System.out.println("1. Crear Estudiante");
			System.out.println("2. Buscar Estudiante");
			System.out.println("3. Actualizar Estudiante");
			System.out.println("4. Eliminar Estudiante");	
			System.out.println("5. Salir");
			System.out.println("Elija una opcion: ");
				

		
			opcion = sc.nextInt();
			sc.nextLine();
		
			switch (opcion) {
			case 1:
				Estudiante objEst1 = new Estudiante();
				
				//Datos
				System.out.println("Digite su nombre: ");
				objEst1.setNombre(sc.nextLine());
				
				System.out.println("Digite su apellido: ");
				objEst1.setApellido(sc.nextLine());
				
				System.out.println("Digite su email: ");
				objEst1.setEmail(sc.nextLine());
				
				System.out.println("Digite su fecha de nacimiento (YYYY-MM-DD): ");
				objEst1.setFechaNacimiento(LocalDate.parse(sc.nextLine()));
				
				//Ejecucion 
				em.getTransaction().begin();
				em.persist(objEst1);
				em.getTransaction().commit();
				
				//Cerrar Persistencia
				System.out.println("Estudiante registrado con exito");
				break;
			case 2:
				
				System.out.println("Digita el ID del estudiante a buscar: ");
				
				int idEstudiante = sc.nextInt();
				
				Estudiante estudianteEncontrado = em.find(Estudiante.class, idEstudiante);
				
				if (estudianteEncontrado != null) {
					System.out.println("Id" + estudianteEncontrado.getId() + "\n");
					System.out.println("Nombre: " + estudianteEncontrado.getNombre() + "\n");
					System.out.println("Apellido: " + estudianteEncontrado.getApellido() + "\n");
					System.out.println("Email: " + estudianteEncontrado.getEmail() + "\n");
					System.out.println("Fecha de Nacimiento: " + estudianteEncontrado.getFechaNacimiento() + "\n");
					} else {
						System.out.println("Estudiante no encontrado");
					}
				
				
				break;
			case 3:
				System.out.println("Digita el ID del estudiante a actualizar: ");
				
				//Datos
				Estudiante estudianteActualizar = em.find(Estudiante.class, sc.nextInt());
				sc.nextLine();
				
				System.out.println("Digite el nuevo nombre: ");
				estudianteActualizar.setNombre(sc.nextLine());
				
				System.out.println("Digite el nuevo apellido: ");
				estudianteActualizar.setApellido(sc.nextLine());
				
				System.out.println("Digite el nuevo email: ");
				estudianteActualizar.setEmail(sc.nextLine());
				//Ejecucion
				
				em.getTransaction().begin();
				em.merge(estudianteActualizar);//Actualizar
				em.getTransaction().commit();
				break;
			case 4:
				//Datos
				System.out.println("Digite el ID del estudiante a eliminar: ");
				Estudiante estudianteEliminar = em.find(Estudiante.class, sc.nextInt());
				
				//Ejecucion
				em.getTransaction().begin();
				em.remove(estudianteEliminar);
				em.getTransaction().commit();
				System.out.println("Estudiante eliminado con exito");
				
				break;
			case 5:
				System.out.println("Salir");
				em.close();
				JPAUtil.shutdown();
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		}
		//fin del menu
	}
}

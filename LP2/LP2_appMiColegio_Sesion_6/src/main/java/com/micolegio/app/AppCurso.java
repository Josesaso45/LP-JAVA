package com.micolegio.app;

import java.util.Scanner;

import com.micolegio.model.Curso;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppCurso {
	public static void main(String[] args) {
		
		//Persistencia
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		
		//Iniciando Variables
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		while (opcion != 5) {
			System.out.println("Registro de Curso \n");

			System.out.println("\n-- CRUD CURSO --");
			System.out.println("1. Crear Curso");
			System.out.println("2. Buscar Curso");
			System.out.println("3. Actualizar Curso");
			System.out.println("4. Eliminar Curso");	
			System.out.println("5. Salir");
			System.out.println("Elija una opcion: ");
				
	
			opcion = sc.nextInt();
			sc.nextLine();
		
			switch (opcion) {
			case 1:
				Curso objCur1 = new Curso();

				//Datos
				System.out.println("Digite el nombre del curso: ");
				objCur1.setNombre(sc.nextLine());
				
				System.out.println("Digite los creditos del curso: ");
				objCur1.setCreditos(sc.nextInt());
				sc.nextLine();
				
				//Ejecucion 
				em.getTransaction().begin();
				em.persist(objCur1);
				em.getTransaction().commit();
				
				//Cerrar persistencia
				System.out.println("Curso registrado con exito!");
				break;
			case 2:
				
				System.out.println("Digite el ID del curso a buscar: ");
				
				int idBuscar = sc.nextInt();
				Curso cursoEncontrado = em.find(Curso.class, idBuscar);
				
				if (cursoEncontrado != null) {
					System.out.println("Curso encontrado: ");
					System.out.println("ID: " + cursoEncontrado.getId());
					System.out.println("Nombre: " + cursoEncontrado.getNombre());
					System.out.println("Créditos: " + cursoEncontrado.getCreditos());
				} else {
					System.out.println("Curso no encontrado.");
				}
				break;
			case 3:
				//Actualizar Curso
				System.out.println("Digite el ID del curso a actualizar: ");
				
				int idActualizar = sc.nextInt();
				sc.nextLine();
				
				Curso cursoActualizar = em.find(Curso.class, idActualizar);
				
				if (cursoActualizar != null) {
					System.out.println("Digite el nuevo nombre del curso: ");
					cursoActualizar.setNombre(sc.nextLine());
					
					System.out.println("Digite los nuevos creditos del curso: ");
					cursoActualizar.setCreditos(sc.nextInt());
					
					em.getTransaction().begin();
					em.merge(cursoActualizar);
					em.getTransaction().commit();
					
					System.out.println("Curso actualizado con exito!");
				} else {
					System.out.println("Curso no encontrado.");
				}
				break;
			case 4:
				//datos
				System.out.println("Digite el ID del curso a eliminar: ");
				
				int idEliminar = sc.nextInt();
				Curso cursoEliminar = em.find(Curso.class, idEliminar);
				
				if(cursoEliminar != null) {
					//Ejecucion
					em.getTransaction().begin();
					em.remove(cursoEliminar);
					em.getTransaction().commit();	
					System.out.println("Curso eliminado con exito!");
				}else {
					System.out.println("Curso no encontrado.");
				}
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
	}
}

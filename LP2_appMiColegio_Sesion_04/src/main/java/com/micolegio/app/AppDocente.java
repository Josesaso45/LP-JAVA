package com.micolegio.app;

import java.time.LocalDate;
import java.util.Scanner;

import com.micolegio.model.Docente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppDocente {
	public static void main(String[] args) {
		
		//Persistencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("LP2_appMiColegio_Sesion_04PU");
		EntityManager em = emf.createEntityManager();
		
		//Iniciando Variables
		Docente objDoc1 = new Docente();
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		while (opcion != 5) {
			System.out.println("Registro de Docente \n");

			System.out.println("\n-- CRUD DOCENTE --");
			System.out.println("1. Crear Docente");
			System.out.println("2. Buscar Docente");
			System.out.println("3. Actualizar Docente");
			System.out.println("4. Eliminar Docente");	
			System.out.println("5. Salir");
			System.out.println("Elija una opcion: ");
				
	
			opcion = sc.nextInt();
			sc.nextLine();
		
			switch (opcion) {
			case 1:
				//Datos
				System.out.println("Digite su nombre: ");
				objDoc1.setNombre(sc.nextLine());
				
				System.out.println("Digite su apellido: ");
				objDoc1.setApellido(sc.nextLine());
				
				System.out.println("Digite su sueldo: ");
				objDoc1.setSueldo(sc.nextDouble());
				sc.nextLine();
				
				System.out.println("Digite su correo: ");
				objDoc1.setCorreo(sc.nextLine());
				
				System.out.println("Digite su fecha de nacimiento (YYYY-MM-DD): ");
				objDoc1.setFechaNacimiento(LocalDate.parse(sc.nextLine()));
				
				//Ejecucion 
				em.getTransaction().begin();
				em.persist(objDoc1);
				em.getTransaction().commit();
				
				//Cerrar Persistencia
				System.out.println("Docente registrado con exito!");
				break;
				case 2:
					//Buscar Docente
					System.out.println("Ingrese el ID del docente a buscar: ");
					
					int idBuscar = sc.nextInt();
					Docente docenteEncontrado = em.find(Docente.class, idBuscar);
					
					if (docenteEncontrado != null) {
						System.out.println("Id: " + docenteEncontrado.getId() + "\n");
						System.out.println("Nombre: " + docenteEncontrado.getNombre() + "\n");
						System.out.println("Apellido: " + docenteEncontrado.getApellido() + "\n");
						System.out.println("Sueldo: " + docenteEncontrado.getSueldo() + "\n");
						System.out.println("Correo: " + docenteEncontrado.getCorreo() + "\n");
						System.out.println("Fecha de Nacimiento: " + docenteEncontrado.getFechaNacimiento() + "\n");
						} else {
							System.out.println("Docente no encontrado");
						}
					break;
					case 3:
						//Actualizar Docente
						System.out.println("Ingrese el ID del docente a actualizar: ");
						
						int idActualizar = sc.nextInt();
						Docente docenteActualizar = em.find(Docente.class, idActualizar);
						
						if (docenteActualizar != null) {
							System.out.println("Digite el nuevo nombre: ");
							docenteActualizar.setNombre(sc.nextLine());
							
							System.out.println("Digite el nuevo apellido: ");
							docenteActualizar.setApellido(sc.nextLine());
							
							System.out.println("Digite el nuevo sueldo: ");
							docenteActualizar.setSueldo(sc.nextDouble());
							
							System.out.println("Digite el nuevo correo: ");
							docenteActualizar.setCorreo(sc.nextLine());
							
							System.out.println("Digite la nueva fecha de nacimiento (YYYY-MM-DD): ");
							docenteActualizar.setFechaNacimiento(LocalDate.parse(sc.nextLine()));
							
							em.getTransaction().begin();
							em.merge(docenteActualizar);
							em.getTransaction().commit();
							
							System.out.println("Docente actualizado con exito!");
						} else {
							System.out.println("Docente no encontrado");
						}
						break;
						case 4:	
							//Eliminar Docente 
							
							//Datos
							System.out.println("Ingrese el ID del docente a eliminar: ");
							
							Docente docenteEliminar = em.find(Docente.class, sc.nextInt());
							
							//Ejecucion
							if (docenteEliminar != null) {
								em.getTransaction().begin();
								em.remove(docenteEliminar);
								em.getTransaction().commit();
								
								System.out.println("Docente eliminado con exito!");
							} else {
								System.out.println("Docente no encontrado");
							}
							break;
							case 5:
								System.out.println("Saliendo del programa...");
								break;
								default:
									System.out.println("Opcion no valida. Intente de nuevo.");
									break;
			}
		}

		
	}
}

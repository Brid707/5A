/*
 * App.java    Version 3.0   14 Feb 2025
 *
 * Copyright (c) 2025 Bridget Mendez.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Bridget Mendez ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * Bridget Mendez.
 *
 * BRIDGET MENDEZ MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BRIDGET
 * MENDEZ SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR
 * ITS DERIVATIVES.
 */

import java.util.Scanner;

/**
 * Aplicación principal para el cálculo de la distribución t de Student
 * mediante integración numérica con la Regla de Simpson.
 * 
 * <p>Este programa implementa el Programa 5 del PSP (Personal Software Process)
 * y permite ejecutar las 3 pruebas estándar del curso, así como calcular
 * integrales personalizadas de la distribución t.</p>
 *
 * <p>La distribución t de Student se utiliza en estadística cuando el tamaño
 * de la muestra es pequeño y la desviación estándar poblacional es desconocida.</p>
 */
/*
 * App.java    Version 3.0   14 Feb 2025
 *
 * Autora: Bridget Mendez
 */

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Logic logic = new Logic();
        boolean activo = true;

        System.out.println("==============================================");
        System.out.println("        PROGRAMA 5 – DISTRIBUCIÓN t (PSP)");
        System.out.println("        Autora: Bridget Mendez");
        System.out.println("        Versión 3.0 – 14/02/2025");
        System.out.println("==============================================");

        while (activo) {

            mostrarMenu();

            System.out.print("Seleccione una opción: ");
            int opcion = leerEntero(sc);

            switch (opcion) {

                case 1:
                    ejecutarTresPruebas(logic, sc);
                    break;

                case 0:
                    System.out.println("\nFinalizando el programa...");
                    activo = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.\n");
            }
        }

        sc.close();
    }

    private static void mostrarMenu() {

        System.out.println("\n----------------------------------------------");
        System.out.println("                 MENÚ PRINCIPAL               ");
        System.out.println("----------------------------------------------");
        System.out.println(" 1. Ejecutar las 3 pruebas del PSP");
        System.out.println(" 0. Salir");
       	System.out.println("----------------------------------------------");
    }

    private static void ejecutarTresPruebas(Logic logic, Scanner sc) {

        System.out.println("\n>>> EJECUTAR 3 PRUEBAS PSP <<<");

        for (int i = 1; i <= 3; i++) {

            System.out.println("\n--- PRUEBA " + i + " ---");

            System.out.print("Ingrese x: ");
            double x = leerDouble(sc);

            System.out.print("Ingrese dof (grados de libertad): ");
            int dof = leerEntero(sc);

            System.out.print("Ingrese valor esperado: ");
            double esperado = leerDouble(sc);

            logic.ejecutarPrueba(i, x, dof, esperado);
        }

        System.out.println("\n>>> Todas las pruebas PSP han sido ejecutadas.\n");
    }

    private static int leerEntero(Scanner sc) {

        while (!sc.hasNextInt()) {
            System.out.print("Valor inválido. Ingrese un entero: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double leerDouble(Scanner sc) {

        while (!sc.hasNextDouble()) {
            System.out.print("Valor inválido. Ingrese un real: ");
            sc.next();
        }
        return sc.nextDouble();
    }
}

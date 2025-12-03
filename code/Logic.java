/*
 * Logic.java   Version 3.0   14 Feb 2025
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

/**
 * Clase de lógica de negocio que coordina el cálculo de la
 * distribución t mediante integración numérica adaptativa.
 *
 * <p>Esta clase gestiona el proceso de integración adaptativa,
 * ajustando dinámicamente el número de segmentos hasta alcanzar
 * la precisión deseada (epsilon = 0.00001).</p>
 *
 * <p><strong>Patrón de diseño:</strong> Esta clase actúa como un Facade
 * que coordina SimpsonIntegration y OutPut.</p>
 *
 * @version 3.0 14 Feb 2025
 * @author  Bridget Mendez
 */
public class Logic {

    /** Número de segmentos para la integración de Simpson */
    private int intNumSeg;
    
    /** Precisión deseada (epsilon) para la convergencia */
    private double dblE;
    
    /** Grados de libertad de la distribución t */
    private int intDOF;
    
    /** Límite superior de integración */
    private double dblX;

    /** Objeto para realizar la integración de Simpson */
    private SimpsonIntegration simpson;
    
    /** 
     * Objeto para escribir resultados a archivos.
     * RECICLADO del Programa 3 (Linear Regression)
     */
    private OutPut output;

    /**
     * Constructor que inicializa los objetos necesarios.
     * Establece epsilon en 0.00001 según especificación PSP.
     */
    public Logic() {
        simpson = new SimpsonIntegration();
        output = new OutPut();
        dblE = 0.00001;
    }

    /**
     * Ejecuta una prueba PSP y guarda los resultados en un archivo.
     * Calcula la integral, compara con el valor esperado y reporta
     * la diferencia.
     *
     * @param num      número de la prueba (1, 2 o 3)
     * @param x        límite superior de integración
     * @param dof      grados de libertad
     * @param esperado valor esperado para comparación
     */
    public void ejecutarPrueba(int num, double x, int dof, double esperado) {

        dblX = x;
        intDOF = dof;

        double resultado = integrar();
        double diferencia = Math.abs(resultado - esperado);

        String texto =
            "PRUEBA " + num + "\n" +
            "x = " + x + "\n" +
            "dof = " + dof + "\n" +
            "Resultado = " + resultado + "\n" +
            "Esperado = " + esperado + "\n" +
            "Diferencia = " + diferencia + "\n";

        output.writeData(num + ".txt", texto);

        System.out.println("\n===========================================");
        System.out.println("   PRUEBA " + num);
        System.out.println("===========================================");
        System.out.println("Valor calculado: " + resultado);
        System.out.println("Esperado:        " + esperado);
        System.out.println("Diferencia:      " + diferencia);
    }

    /**
     * Realiza la integración adaptativa de Simpson.
     * 
     * <p>Algoritmo de integración adaptativa:</p>
     * <ol>
     * <li>Comienza con 10 segmentos</li>
     * <li>Calcula la integral</li>
     * <li>Duplica el número de segmentos</li>
     * <li>Recalcula la integral</li>
     * <li>Si |nuevo - previo| > epsilon, repite desde paso 3</li>
     * <li>Retorna el valor cuando converge</li>
     * </ol>
     *
     * <p>Este método implementa la técnica de refinamiento adaptativo
     * descrita en el Assignment Kit del Programa 5, página 4-5.</p>
     *
     * @return valor de la integral ∫₀ˣ t(u) du
     */
    private double integrar() {

        intNumSeg = 10;
        double previo = 0.0;
        double nuevo = 0.0;

        do {
            previo = nuevo;
            nuevo = simpson.integrar(dblX, intDOF, intNumSeg);
            intNumSeg *= 2;

        } while (Math.abs(nuevo - previo) > dblE);

        return nuevo;
    }

    /**
     * Calcula e imprime una integral personalizada sin guardar en archivo.
     * Útil para pruebas rápidas o exploración de valores.
     *
     * @param x   límite superior de integración
     * @param dof grados de libertad
     */
    public void calcularIntegralPersonalizada(double x, int dof) {

        dblX = x;
        intDOF = dof;

        double v = integrar();

        System.out.println("\nRESULTADO PERSONALIZADO:");
        System.out.println("∫ t(x) dx desde 0 hasta " + x + " = " + v);
        System.out.println("Grados de libertad: " + dof);
    }
}

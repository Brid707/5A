/*
 * GammaFunction.java   Version 3.0   14 Feb 2025
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
 * Implementación de la función Gamma (Γ) utilizando la aproximación de Lanczos.
 *
 * <p>La función Gamma es una extensión de la función factorial a números reales y complejos.
 * Para valores enteros: Γ(n) = (n-1)!
 * Para valores no enteros: se utiliza la aproximación de Lanczos.</p>
 *
 * <p><strong>Aproximación de Lanczos:</strong></p>
 * <p>Γ(x) = √(2π) · t^(z+0.5) · e^(-t) · A(z)</p>
 * <p>donde t = z + 7.5 y A(z) es una serie con coeficientes predeterminados.</p>
 *
 * <p><strong>Referencias:</strong></p>
 * <ul>
 * <li>Assignment Kit Programa 5, páginas 7-9</li>
 * <li>Lanczos, C. (1964). "A Precision Approximation of the Gamma Function"</li>
 * </ul>
 *
 * @version 3.0 14 Feb 2025
 * @author  Bridget Mendez
 */
public class GammaFunction {

    /** 
     * Almacena el último valor calculado de Gamma.
     * Útil para debugging y validación.
     */
    private double gammaValue;

    /**
     * Calcula la función Gamma para valores enteros usando factorial.
     * 
     * <p>Fórmula: Γ(n) = (n-1)!</p>
     * <p>Ejemplo: Γ(5) = 4! = 24</p>
     *
     * @param n valor entero (debe ser >= 1)
     * @return Γ(n) = (n-1)!
     */
    public double computeIntGamma(int n) {
        if (n <= 1) {
            gammaValue = 1.0;
            return 1.0;
        }
        
        gammaValue = 1.0;
        for (int i = n - 1; i > 0; i--) {
            gammaValue *= i;
        }
        
        return gammaValue;
    }

    /**
     * Calcula la función Gamma para valores de punto flotante usando
     * la aproximación de Lanczos.
     *
     * <p><strong>Algoritmo de Lanczos:</strong></p>
     * <pre>
     * 1. Si x es entero, usar factorial: Γ(x) = (x-1)!
     * 2. Calcular z = x - 1
     * 3. Calcular acumulador A(z) = 0.99999999999980993 + Σ p[i]/(z+i+1)
     * 4. Calcular t = z + 7.5
     * 5. Γ(x) = √(2π) · t^(z+0.5) · e^(-t) · A(z)
     * </pre>
     *
     * <p><strong>Coeficientes de Lanczos (g=7):</strong></p>
     * <p>Los coeficientes p[0..7] proporcionan una precisión de ~15 dígitos
     * decimales para la mayoría de los valores de x.</p>
     *
     * @param x valor real (debe ser > 0)
     * @return Γ(x) calculado con aproximación de Lanczos
     */
    public double computeDblGamma(double x) {

        // Coeficientes de Lanczos para g=7
        double[] p = {
            676.5203681218851,
            -1259.1392167224028,
            771.32342877765313,
            -176.61502916214059,
            12.507343278686905,
            -0.13857109526572012,
            9.9843695780195716e-6,
            1.5056327351493116e-7
        };

        // Si x es entero, usar el cálculo factorial más eficiente
        if (x == (int)x) {
            return computeIntGamma((int)x);
        }

        // Aproximación de Lanczos para valores no enteros
        double z = x - 1;
        double acc = 0.99999999999980993;

        // Calcular el acumulador sumando los términos de la serie
        for (int i = 0; i < 8; i++) {
            acc += p[i] / (z + i + 1);
        }

        double t = z + 7.5;

        // Fórmula de Lanczos: Γ(x) = √(2π) · t^(z+0.5) · e^(-t) · acc
        gammaValue =
            Math.sqrt(2 * Math.PI) *
            Math.pow(t, z + 0.5) *
            Math.exp(-t) *
            acc;

        return gammaValue;
    }

    /**
     * Obtiene el último valor calculado de la función Gamma.
     * Útil para debugging y validación de resultados.
     *
     * @return último valor de Gamma calculado
     */
    public double getGammaValue() {
        return gammaValue;
    }
}

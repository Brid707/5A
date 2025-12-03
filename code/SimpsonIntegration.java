/*
 * SimpsonIntegration.java   Version 3.0   14 Feb 2025
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
 * Implementa la integración numérica con la Regla de Simpson 1/3
 * para calcular la distribución t de Student.
 *
 * <p><strong>Regla de Simpson 1/3:</strong></p>
 * <p>∫ₐᵇ f(x)dx ≈ (h/3)[f(x₀) + 4f(x₁) + 2f(x₂) + 4f(x₃) + ... + f(xₙ)]</p>
 * <p>donde h = (b-a)/n y n debe ser par</p>
 *
 * <p><strong>Función de distribución t:</strong></p>
 * <pre>
 * F(x) = [Γ((dof+1)/2) / (√(dof·π) · Γ(dof/2))] · (1 + x²/dof)^(-(dof+1)/2)
 * </pre>
 *
 * <p><strong>Referencias:</strong></p>
 * <ul>
 * <li>Assignment Kit Programa 5, páginas 4-6 (Simpson's Rule)</li>
 * <li>Assignment Kit Programa 5, páginas 6-8 (t distribution)</li>
 * <li>Ejemplo detallado: páginas 9-12</li>
 * </ul>
 *
 * @version 3.0 14 Feb 2025
 * @author  Bridget Mendez
 */
public class SimpsonIntegration {

    /** Número de segmentos para la integración (debe ser par) */
    private int intNumSeg;
    
    /** Ancho de cada segmento: w = x / numSeg */
    private double dblW;
    
    /** Grados de libertad de la distribución t */
    private int intDOF;
    
    /** Límite superior de integración */
    private double dblX;

    /** Array de puntos xi = i * w, para i = 0 hasta numSeg */
    private double[] dblTotXi;
    
    /** Array de términos base: 1 + (xi² / dof) */
    private double[] dblFirstBaseTerms;
    
    /** Exponente: -(dof + 1) / 2 */
    private double dblExponent;
    
    /** Coeficiente de la distribución t */
    private double dblCoeff;
    
    /** Array de valores de la función evaluada: f(xi) */
    private double[] dblFxi;
    
    /** Array de términos finales con multiplicadores de Simpson (1, 4, 2, 4, ..., 1) */
    private double[] dblFinalTerms;
    
    /** Valor final de la integral */
    private double dblFinalValue;

    /** Objeto para calcular la función Gamma */
    private GammaFunction gamma;

    /**
     * Constructor que inicializa el objeto GammaFunction.
     */
    public SimpsonIntegration() {
        gamma = new GammaFunction();
    }

    /**
     * Calcula la integral ∫₀ˣ t(u) du usando la Regla de Simpson.
     *
     * <p>Este método coordina todos los pasos del cálculo:</p>
     * <ol>
     * <li>Calcular ancho de segmento (w)</li>
     * <li>Generar puntos xi</li>
     * <li>Calcular términos base (1 + xi²/dof)</li>
     * <li>Calcular exponente</li>
     * <li>Calcular coeficiente de la distribución t</li>
     * <li>Evaluar f(xi) para cada punto</li>
     * <li>Aplicar multiplicadores de Simpson</li>
     * <li>Calcular valor final de la integral</li>
     * </ol>
     *
     * @param x      límite superior de integración
     * @param dof    grados de libertad
     * @param numSeg número de segmentos (debe ser par)
     * @return valor de ∫₀ˣ t(u) du
     */
    public double integrar(double x, int dof, int numSeg) {

        dblX = x;
        intDOF = dof;
        intNumSeg = numSeg;

        computeW();
        computeXi();
        computeFirstBaseTerms();
        computeExponent();
        computeCoefficient();
        computeFxi();
        computeFinalTerms();
        computeFinalValue();

        return dblFinalValue;
    }

    /**
     * Calcula el ancho de cada segmento.
     * 
     * <p>Fórmula: w = x / n</p>
     * <p>Ejemplo: si x=1.1 y n=10, entonces w=0.11</p>
     */
    public void computeW() {
        dblW = dblX / intNumSeg;
    }

    /**
     * Genera los puntos de evaluación xi.
     * 
     * <p>Fórmula: xi = i · w, para i = 0, 1, 2, ..., n</p>
     * <p>Ejemplo con x=1.1, n=10, w=0.11:</p>
     * <ul>
     * <li>x₀ = 0 · 0.11 = 0.0</li>
     * <li>x₁ = 1 · 0.11 = 0.11</li>
     * <li>x₂ = 2 · 0.11 = 0.22</li>
     * <li>...</li>
     * <li>x₁₀ = 10 · 0.11 = 1.1</li>
     * </ul>
     */
    public void computeXi() {

        dblTotXi = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            dblTotXi[i] = i * dblW;
        }
    }

    /**
     * Calcula los términos base para cada punto.
     * 
     * <p>Fórmula: 1 + (xi² / dof)</p>
     * <p>Estos términos son la base que se elevará al exponente en f(xi).</p>
     * <p>Ejemplo con dof=9, x₁=0.11:</p>
     * <p>1 + (0.11² / 9) = 1 + 0.0121/9 = 1.001344...</p>
     */
    public void computeFirstBaseTerms() {

        dblFirstBaseTerms = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            dblFirstBaseTerms[i] =
                1.0 + (Math.pow(dblTotXi[i], 2.0) / intDOF);
        }
    }

    /**
     * Calcula el exponente de la distribución t.
     * 
     * <p>Fórmula: -(dof + 1) / 2</p>
     * <p>Ejemplo con dof=9:</p>
     * <p>exponente = -(9 + 1) / 2 = -10/2 = -5</p>
     */
    public void computeExponent() {
        dblExponent = -1.0 * (intDOF + 1.0) / 2.0;
    }

    /**
     * Calcula el coeficiente de la distribución t.
     * 
     * <p><strong>Fórmula:</strong></p>
     * <pre>
     * coef = Γ((dof+1)/2) / (√(dof·π) · Γ(dof/2))
     * </pre>
     *
     * <p><strong>Ejemplo con dof=9:</strong></p>
     * <pre>
     * numerador   = Γ((9+1)/2) = Γ(5) = 4! = 24
     * denominador = √(9·π) · Γ(9/2) = √(9·π) · Γ(4.5)
     *             = 5.3174 · 11.6317 = 61.8446
     * coef = 24 / 61.8446 = 0.388035
     * </pre>
     *
     * <p><strong>Referencia:</strong> Ver páginas 7-9 del Assignment Kit para
     * ejemplos detallados del cálculo de Gamma.</p>
     */
    public void computeCoefficient() {

        double arriba =
            gamma.computeDblGamma((intDOF + 1.0) / 2.0);

        double abajo =
            Math.sqrt(intDOF * Math.PI) *
            gamma.computeDblGamma(intDOF / 2.0);

        dblCoeff = arriba / abajo;
    }

    /**
     * Evalúa la función de distribución t en cada punto xi.
     * 
     * <p><strong>Fórmula completa:</strong></p>
     * <pre>
     * f(xi) = coef · (1 + xi²/dof)^(-(dof+1)/2)
     * </pre>
     *
     * <p>Este es el paso crítico donde se evalúa la función que se
     * está integrando. Los valores f(xi) serán multiplicados por los
     * ponderadores de Simpson (1, 4, 2, 4, ..., 1) en el siguiente paso.</p>
     *
     * <p><strong>Ejemplo con dof=9, coef=0.388035, x₁=0.11:</strong></p>
     * <pre>
     * base = 1 + (0.11²/9) = 1.001344
     * f(0.11) = 0.388035 · (1.001344)^(-5)
     *         = 0.388035 · 0.993308
     *         = 0.385444
     * </pre>
     */
    public void computeFxi() {

        dblFxi = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {

            dblFxi[i] = dblCoeff *
                Math.pow(dblFirstBaseTerms[i], dblExponent);
        }
    }

    /**
     * Aplica los multiplicadores de Simpson 1/3 a cada f(xi).
     * 
     * <p><strong>Patrón de multiplicadores:</strong></p>
     * <ul>
     * <li>Primer punto (i=0): multiplicador = 1</li>
     * <li>Puntos impares (i=1,3,5,...): multiplicador = 4</li>
     * <li>Puntos pares internos (i=2,4,6,...): multiplicador = 2</li>
     * <li>Último punto (i=n): multiplicador = 1</li>
     * </ul>
     *
     * <p><strong>Ejemplo con n=10:</strong></p>
     * <pre>
     * términos = [1·f(x₀), 4·f(x₁), 2·f(x₂), 4·f(x₃), ..., 4·f(x₉), 1·f(x₁₀)]
     * </pre>
     *
     * <p><strong>Referencia:</strong> Ver página 10 del Assignment Kit,
     * Tabla 2, columna "Multiplier" para ejemplo detallado.</p>
     */
    public void computeFinalTerms() {

        dblFinalTerms = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {

            if (i == 0 || i == intNumSeg) {
                dblFinalTerms[i] = dblFxi[i];

            } else if (i % 2 == 0) {
                dblFinalTerms[i] = 2.0 * dblFxi[i];

            } else {
                dblFinalTerms[i] = 4.0 * dblFxi[i];
            }
        }
    }

    /**
     * Calcula el valor final de la integral sumando los términos ponderados.
     * 
     * <p><strong>Fórmula de Simpson:</strong></p>
     * <pre>
     * integral = (w/3) · Σ términos_finales
     * </pre>
     *
     * <p><strong>Ejemplo con w=0.11, suma_términos=9.547426:</strong></p>
     * <pre>
     * integral = (0.11/3) · 9.547426
     *          = 0.036667 · 9.547426
     *          = 0.350059
     * </pre>
     *
     * <p>Este resultado debe compararse con el valor esperado 0.35006,
     * dando una diferencia de ~0.000001, dentro de la precisión aceptable.</p>
     *
     * <p><strong>Referencia:</strong> Ver página 11 del Assignment Kit
     * para el ejemplo completo de cálculo.</p>
     */
    public void computeFinalValue() {

        double sum = 0.0;

        for (double v : dblFinalTerms) {
            sum += v;
        }

        dblFinalValue = (dblW / 3.0) * sum;
    }
}

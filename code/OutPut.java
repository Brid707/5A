/*
 * OutPut.java   Version 3.0   14 Feb 2025
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
 * FITNESS FOR A PARTICULAR PURPOSE, OR NONINFRINGEMENT. BRIDGET 
 * MENDEZ SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE 
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR 
 * ITS DERIVATIVES.
 */

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase utilitaria para manejo de archivos de salida.
 *
 * <p><strong>*** CÓDIGO RECICLADO ***</strong></p>
 * <p>Esta clase fue desarrollada originalmente para el Programa 3
 * (Linear Regression - Regresión Lineal) y se reutiliza sin cambios
 * en el Programa 5 (t Distribution Integration).</p>
 *
 * <p><strong>Análisis de reciclaje:</strong></p>
 * <ul>
 * <li><strong>Programa origen:</strong> Programa 3 (Linear Regression)</li>
 * <li><strong>Programa destino:</strong> Programa 5 (t Distribution)</li>
 * <li><strong>Porcentaje de reciclaje:</strong> 100% (sin modificaciones)</li>
 * <li><strong>Justificación:</strong> La funcionalidad de escritura de
 *     archivos es genérica y se aplica a cualquier tipo de resultado
 *     numérico o textual.</li>
 * </ul>
 *
 * <p><strong>Ventajas del reciclaje:</strong></p>
 * <ol>
 * <li>Código ya probado y validado en producción</li>
 * <li>Reduce tiempo de desarrollo</li>
 * <li>Menor probabilidad de defectos</li>
 * <li>Consistencia en el manejo de archivos entre programas</li>
 * </ol>
 *
 * <p><strong>Buenas prácticas PSP:</strong></p>
 * <p>El Personal Software Process (PSP) fomenta el reciclaje de código
 * como una práctica fundamental para mejorar la productividad y calidad.
 * Esta clase es un ejemplo perfecto de código reutilizable que mantiene
 * una única responsabilidad (escritura de archivos) y puede aplicarse
 * en múltiples contextos.</p>
 *
 * @version 3.0 14 Feb 2025
 * @author  Bridget Mendez
 */
public class OutPut {

    /**
     * Constructor por defecto.
     * No requiere inicialización de estado.
     */
    public OutPut() { }

    /**
     * Escribe texto en un archivo especificado.
     * 
     * <p>Si el archivo no existe, lo crea. Si existe, lo sobrescribe.</p>
     *
     * <p><strong>Manejo de errores:</strong></p>
     * <p>Los errores de I/O se capturan y reportan en la consola de errores
     * estándar (System.err), pero no detienen la ejecución del programa.</p>
     *
     * <p><strong>Uso típico:</strong></p>
     * <pre>
     * OutPut output = new OutPut();
     * output.writeData("resultado1.txt", "Integral = 0.35006");
     * </pre>
     *
     * @param file nombre del archivo (puede incluir ruta relativa o absoluta)
     * @param txt  texto a escribir en el archivo
     */
    public void writeData(String file, String txt) {

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(txt);
            fw.close();

        } catch (IOException e) {
            System.err.println(
                "Error escribiendo archivo: " + e.getMessage());
        }
    }
}

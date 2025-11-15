package com.dam.accesodatos.ra1;

import com.dam.accesodatos.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IMPLEMENTACIÓN PARA ESTUDIANTES - RA1: Gestión de Ficheros
 * 
 * Esta clase contiene esqueletos de métodos que los estudiantes deben completar
 * para demostrar su comprensión del RA1 y sus criterios de evaluación.
 * 
 * INSTRUCCIONES:
 * 1. Completar cada método marcado con TODO
 * 2. Usar solo las clases Java I/O indicadas en comentarios
 * 3. Implementar manejo robusto de excepciones
 * 4. Ejecutar tests para validar implementación
 * 5. Documentar decisiones técnicas en comentarios
 * 
 * PROHIBIDO:
 * - Usar librerías externas no permitidas
 * - Copiar código sin entender
 * - Omitir manejo de excepciones
 */
@Service
public class FileUserServiceImpl implements FileUserService {

    private final ObjectMapper objectMapper;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public FileUserServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules(); // Para LocalDateTime
    }

    // ========================================================================================
    // CE1.a: ANÁLISIS DE CLASES RELACIONADAS CON TRATAMIENTO DE FICHEROS
    // ========================================================================================

    @Override
    public String getFileInfo(String filePath) {
        /*
         * TODO CE1.a: Implementar información detallada de archivo (actividad 1 de la presentación vista en clase)
         * 
         * Pasos requeridos:
         * 1. Crear objeto File con la ruta proporcionada
         * 2. Verificar que existe con exists()
         * 3. Determinar si es archivo o directorio con isFile()/isDirectory()
         * 4. Obtener tamaño con length() (solo para archivos)
         * 5. Obtener permisos con canRead(), canWrite(), canExecute()
         * 6. Formatear fecha de modificación con SimpleDateFormat
         * 7. Construir string con formato: "Tipo: archivo/directorio, Tamaño: X bytes, Permisos: rwx, Fecha: ..."
         * 
         * Clases requeridas:
         * - File (exists, isFile, isDirectory, length, lastModified, canRead, canWrite, canExecute)
         * - SimpleDateFormat (para formatear fecha)
         * - Date (para convertir timestamp)
         */
        
        // Paso 1: Crear objeto File con la ruta proporcionada
        File file = new File(filePath);
        
        // Paso 2: Verificar que existe
        if (!file.exists()) {
            return "Error: El archivo o directorio no existe: " + filePath;
        }
        
        // Paso 3: Determinar si es archivo o directorio
        String tipo = file.isFile() ? "archivo" : "directorio";
        
        // Paso 4: Obtener tamaño (solo para archivos)
        String tamaño;
        if (file.isFile()) {
            tamaño = file.length() + " bytes";
        } else {
            tamaño = "N/A (directorio)";
        }
        
        // Paso 5: Obtener permisos
        String permisos = "";
        permisos += file.canRead() ? "r" : "-";
        permisos += file.canWrite() ? "w" : "-";
        permisos += file.canExecute() ? "x" : "-";
        
        // Paso 6: Formatear fecha de modificación
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fechaModificacion = new Date(file.lastModified());
        String fechaFormateada = formatter.format(fechaModificacion);
        
        // Paso 7: Construir string con formato especificado
        return String.format("Tipo: %s, Tamaño: %s, Permisos: %s, Fecha: %s", 
                           tipo, tamaño, permisos, fechaFormateada);
    }

    @Override
    public String compareIOPerformance(String filePath) {
        /*
         * TODO CE1.a: Implementar comparación de rendimiento I/O con y sin buffering
         * 
         * Pasos requeridos:
         * 1. Validar que archivo existe
         * 2. Primera prueba: leer con FileReader (sin buffer)
         *    - Usar System.currentTimeMillis() antes y después
         *    - Leer carácter por carácter con read()
         * 3. Segunda prueba: leer con BufferedReader
         *    - Medir tiempo de la misma manera
         *    - Usar readLine() para leer líneas
         * 4. Calcular diferencia de tiempo
         * 5. Retornar comparación formateada
         * 
         * Formato sugerido:
         * "FileReader: 1250ms
         *  BufferedReader: 45ms
         *  Mejora: 96.4% más rápido con buffer"
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar compareIOPerformance con medición de tiempo");

        // VARIABLES //
        File file = new File(filePath);
        long inicio, fin, tiempoFileReader, tiempoBufferedReader;
        int leerCaracter;
        // -----------------------------------------//

        /* Paso 1: Validar archivo existente */
        if (!file.exists()) {
            return "Error no existe el archivo.";
        }

        /* Paso 2: Leer con FileReader (sin buffer) */
        try (FileReader fr = new FileReader(filePath)) {
            inicio = System.currentTimeMillis();

            // Leer carácter por carácter hasta que se acaban
            while ((leerCaracter = fr.read()) != -1) {
            }

            fin = System.currentTimeMillis();
            tiempoFileReader = fin - inicio;

        } catch (IOException ex) {
            return "Error al leer el archivo: " + ex.getMessage();
        }

        /* Paso 3: Leer con BufferedReader */
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            inicio = System.currentTimeMillis();

            // Leer línea por línea
            String linea;
            while ((linea = br.readLine()) != null) {
                // Procesar cada línea (no hacer nada, solo leer)
            }

            fin = System.currentTimeMillis();
            tiempoBufferedReader = fin - inicio;

        } catch (IOException ex) {
            return "Error al leer el archivo: " + ex.getMessage();
        }

        /* Paso 4 y 5: Calcular diferencia de tiempo y retornar formateado */
        double mejora = ((double) (tiempoFileReader - tiempoBufferedReader) / tiempoFileReader) * 100;

        return String.format(
                "FileReader: %dms | BufferedReader: %dms | Mejora: %.1f%% más rápido con buffer",
                tiempoFileReader, tiempoBufferedReader, mejora
        );
    }

    @Override
    public String compareNIOvsIO(String filePath) {
        /*
         * TODO [OPCIONAL CE1.a]: Implementar comparación entre enfoques NIO y IO tradicional
         * ⚠️ MÉTODO OPCIONAL - Concepto avanzado NIO. Prioriza getFileInfo() y compareIOPerformance()
         * 
         * Pasos requeridos:
         * 1. Enfoque tradicional (java.io):
         *    - Usar File para verificar existencia
         *    - Usar BufferedReader para leer líneas
         *    - Contar líneas manualmente
         * 2. Enfoque NIO (java.nio.file):
         *    - Usar Path y Files.exists() para verificar
         *    - Usar Files.readAllLines() para leer todo de una vez
         *    - Obtener tamaño de lista directamente
         * 3. Comparar sintaxis y funcionalidades
         * 4. Medir tiempo de ejecución de ambos
         * 5. Retornar comparación formateada
         * 
         * Formato sugerido:
         * "IO Tradicional: 15 líneas de código, 45ms
         *  NIO: 3 líneas de código, 32ms
         *  NIO es más conciso y eficiente"
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar compareNIOvsIO usando Files vs BufferedReader");
        /* 1 */

        // ------ IO ------ //

        // VARIABLES //
        int contador = 0;
        long inicio, fin, tiempobuffereaderIO;
        // ____________________________________ //

        File archivo = new File(filePath);
        if (!archivo.exists()) {
            return "Error, el archivo no existe";
        }


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            inicio = System.currentTimeMillis();

            // Creamos un contador de líneas de texto usando el buffer
            while (bufferedReader.readLine() != null) {
                contador++;
            }

            fin = System.currentTimeMillis();

            // CALCULAR DIFERENCIA
            tiempobuffereaderIO = fin - inicio;


        } catch (IOException e) {

            return "Error al leer el archivo" + e.getMessage();

        }

        // ------ NIO ------ //

        long inicioNIO, finNIO, timepobuffereaderNIO;
        int lineasNIO = 0;

        try {
            // Usamos path porque es más moderno y más potente a la hora de representar rutas de archivos en "JAVA" //
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                return "Error, el archivo no existe (NIO)";
            }

            inicioNIO = System.currentTimeMillis();

            // USo list, porque el Files.readAllLines devuelve una Lista en vez de un arraylist, ahorrando memoria del sistema //
            List<String> lines = Files.readAllLines(path);
            lineasNIO = lines.size();

            finNIO = System.currentTimeMillis();
            timepobuffereaderNIO = finNIO - inicioNIO;


        } catch (IOException e) {
            return "Error al leer el archivo" + e.getMessage();
        }
        return "IO Tradicional: " + contador + " lineas de código, en " + tiempobuffereaderIO + "ms\n" +
                "NIO : " + contador + " lineas de código, en " + timepobuffereaderNIO + " ms";

    }

    // ========================================================================================
    // CE1.b: UTILIZACIÓN DE FLUJOS PARA ACCESO A INFORMACIÓN EN FICHEROS
    // ========================================================================================

    @Override
    public String searchTextInFile(String filePath, String searchText) {
        /*
         * TODO CE1.b: Implementar búsqueda de texto en archivo (actividad 4 de la presentación vista en clase)
         * 
         * Pasos requeridos:
         * 1. Validar que archivo existe
         * 2. Usar BufferedReader para leer línea por línea
         * 3. Para cada línea: usar String.contains() o indexOf() para buscar
         * 4. Llevar contador de línea actual
         * 5. Acumular resultados: número de línea y contenido donde aparece
         * 6. Contar total de ocurrencias
         * 7. Retornar string formateado con resultados
         * 
         * Formato sugerido:
         * "Línea 5: contenido de la línea donde aparece texto
         *  Línea 12: otra línea con el texto
         *  Total: 2 ocurrencias encontradas"
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar searchTextInFile usando BufferedReader");

        // VARIABLES // Creación de Variables necesarias para la relación
        File archivo = new File(filePath);
        StringBuilder resultado = new StringBuilder();
        int numLinea = 0, totalOcurrencias = 0;

        /* 1: Validamos si el archivo file existe */

        if (!archivo.exists()) {
            return "Error, el archivo no existe";
        }

        /* 2: Usamos el buffereader para comprobar el texto buscado en el archivo */

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String linea;

            // Creamos un bucle para comprobar en cuantas líneas se encuentra el texto buscado //
            while ((linea = bufferedReader.readLine()) != null) {
                numLinea++;

                if (linea.contains(searchText)) {
                    // Concatenamos el string para sacarlo por pantalla con los resultados //
                    resultado.append("Línea ").append(numLinea).append(": ").append(linea).append("\n");
                    // 6. Contar ocurrencias
                    totalOcurrencias++;
                }
            }


        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }

        if (totalOcurrencias == 0) {
            return "No se encontraron ocurrencias";
        }

        return resultado.append("Total: ").append(totalOcurrencias).append(" ocurrencias encontradas").toString();
    }

    @Override
    public String randomAccessRead(String filePath, long position, int length) {
        /*
         * TODO CE1.b: Implementar lectura desde posición específica
         * 
         * Pasos requeridos:
         * 1. Validar que archivo existe
         * 2. Crear RandomAccessFile en modo "r" (solo lectura)
         * 3. Usar seek(position) para posicionar puntero
         * 4. Crear buffer de bytes del tamaño especificado
         * 5. Usar read(buffer) para leer datos
         * 6. Convertir bytes a String
         * 7. Manejar EOF si posición está fuera del archivo
         * 
         * Clases requeridas:
         * - RandomAccessFile (constructor con modo "r", seek, read)
         * - String constructor para convertir bytes
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar randomAccessRead usando RandomAccessFile");

        // VARIABLES //
        File archivo = new File(filePath);
        // PASO 1: Validar que el archivo existe
        if (!archivo.exists()) {
            return "Error: El archivo no existe: " + filePath;
        }

        // PASO 2 y 3: Crear RandomAccessFile y posicionarse
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")) {

            // PASO 3: Usar seek para posicionar el puntero en la posición especificada
            randomAccessFile.seek(position);

            // PASO 4: Crear buffer de bytes del tamaño especificado
            byte[] buffer = new byte[length];

            // PASO 5: Usar read(buffer) para leer datos
            int bytesLeidos = randomAccessFile.read(buffer);

            // PASO 7: Manejar EOF si la posición está fuera del archivo
            // Usamos él -1 para indicar el final del archivo
            if (bytesLeidos == -1) {
                return "Error: Posición fuera del archivo (EOF alcanzado)";
            }

            // PASO 6: Convertir bytes a String
            // Solo convertimos los bytes que se leyeron realmente
            String contenido = new String(buffer, 0, bytesLeidos, StandardCharsets.UTF_8);

            return "Contenido leído desde posición " + position + " (" + bytesLeidos + " bytes):\n" + contenido;

        } catch (EOFException e) {
            return "Error: Fin de archivo alcanzado antes de lo esperado";
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }

    }

    @Override
    public boolean randomAccessWrite(String filePath, long position, String content) {
        /*
         * TODO CE1.b: Implementar escritura en posición específica
         * 
         * Pasos requeridos:
         * 1. Crear directorios padre si no existen
         * 2. Crear RandomAccessFile en modo "rw" (lectura/escritura)
         * 3. Usar seek(position) para posicionar puntero
         * 4. Convertir content a bytes con getBytes()
         * 5. Usar write(bytes) para escribir datos
         * 6. Cerrar archivo con close()
         * 7. Retornar true si exitoso
         * 
         * Clases requeridas:
         * - RandomAccessFile (constructor con modo "rw", seek, write)
         * - String.getBytes() para obtener bytes
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar randomAccessWrite usando RandomAccessFile");

        try {
            // PASO 1: Crear directorio padre si no existen
            // Variables
            Path path = Paths.get(filePath);
            Path parentPath = path.getParent();

            if (parentPath != null) {
                Files.createDirectories(parentPath);
            }

            // PASO 2: Crear RandomAccessFile en modo "rw" (lectura/escritura)
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {

                // PASO 3: Usar seek para posicionarse en la posición especificada
                randomAccessFile.seek(position);

                // PASO 4: Convertir String a bytes
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

                // PASO 5: Escribir los bytes en el archivo
                randomAccessFile.write(bytes);
                // PASO 6: Cerrar el archivo con close
            }

            // PASO 7: Retornar true si exitoso
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo en posición " + position + ": " + e.getMessage(), e);
        }

    }

    @Override
    public boolean convertFileEncoding(String sourceFile, String targetFile, 
                                     String sourceCharset, String targetCharset) {
        /*
         * TODO CE1.b: Implementar conversión entre codificaciones (UTF-8, ISO-8859-1)
         * 
         * Pasos requeridos:
         * 1. Validar que archivo origen existe
         * 2. Crear InputStreamReader con FileInputStream y charset origen
         * 3. Crear OutputStreamWriter con FileOutputStream y charset destino
         * 4. Envolver con BufferedReader/BufferedWriter para eficiencia
         * 5. Leer línea por línea y escribir con nueva codificación
         * 6. Usar try-with-resources para cerrar recursos
         * 7. Retornar true si conversión exitosa
         * 
         * Ejemplo: convertir de ISO-8859-1 a UTF-8
         * InputStreamReader isr = new InputStreamReader(new FileInputStream(source), "ISO-8859-1");
         * OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(target), "UTF-8");
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar convertFileEncoding usando InputStreamReader/OutputStreamWriter");

        // PASO 1: validar que el archivo origen existe //
        File archivo = new File(sourceFile);
        if (!archivo.exists()) {
            return false;
        }

        try {
            // PASO 2: Crear InputStreamReader con FileInputStream y charset origen //
            InputStreamReader isr = new InputStreamReader(new FileInputStream(sourceFile), sourceCharset);

            // PASO 3: Crear OutputStreamWriter con FileOutputStream y charset destino //
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(targetFile), targetCharset);

            // PASO 4: Envolver con BufferedReader/BufferedWriter //
            BufferedWriter bw = new BufferedWriter(osw);
            BufferedReader br = new BufferedReader(isr);

            // PASO 5: Leer línea por línea y escribir con nueva codificación
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();

            }
            // PASO 6: Usar try-with-resources para cerrar recursos
            // Al terminar él try-with-resources cierra de manera manual los recursos que hemos usado dentro de este //
            //bw.close();
            //br.close();

            // PASO 7: Retorna true si la conversion fue exitosa
            return true;

            // El cierre de los recursos se cierra de manera automática después del true, usando el Try-With-resources //

        } catch (IOException e) {
            System.err.println("Error en conversión de codificación: " + e.getMessage());
            return false;
        }
    }

    // ========================================================================================
    // CE1.c: UTILIZACIÓN DE CLASES PARA GESTIÓN DE FICHEROS Y DIRECTORIOS
    // ========================================================================================

    @Override
    public List<String> listUserFiles(String directoryPath) {
        /*
         * TODO CE1.c: Implementar listado de archivos usando Java I/O
         * 
         * Pasos requeridos:
         * 1. Validar que directoryPath existe y es directorio
         * 2. Usar Files.list() o File.listFiles()
         * 3. Filtrar solo archivos (no directorios)
         * 4. Filtrar por extensiones: .csv, .json, .xml
         * 5. Retornar lista de nombres de archivo
         * 
         * Clases I/O requeridas:
         * - Files, Paths
         * - Stream API para filtrado
         * - FilenameFilter o predicados
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar listUserFiles usando Files.list()");
        // Creamos el objeto ruta con la que nos especifica el ejercicio //
        Path ruta = Paths.get(directoryPath);

        // PASO 1: Validar que directoryPath existe y es directorio //
        if (!Files.exists(ruta)) {
            System.err.println("Error: El archivo no existe: " + directoryPath);
            // Usamos arraylist ya que un list no se puede instanciar directamente //
            return new ArrayList<>();
        }

        if (!Files.isDirectory(ruta)) {
            System.err.println("Error: La ruta no es un directorio: " + directoryPath);
            return new ArrayList<>();
        }

        try {
            // PASO 2: Usar Files.list() o File.listFiles() //
            // Usamos la clase file para obtener la ruta con los archivos y carpetas, PASO 3: luego filtramos el contenido para
            // que solo salgan los archivos. //
            List<String> files = Files.list(ruta).filter(Files::isRegularFile).filter(p -> {
                //PASO 4: Cuando solo tenemos los archivos hacemos un filtrado de los archivos que terminan en csv, json y xml //
                String fileName = p.getFileName().toString().toLowerCase();
                return fileName.endsWith(".csv") ||
                        fileName.endsWith(".json") ||
                        fileName.endsWith(".xml");
                // Como este código saca las rutas convertimos las rutas en strings con el nombre de lo archivos. //
            }).map(p -> p.getFileName().toString()).collect(Collectors.toList());
            // PASO 5: Y sacamos la lista //
            return files;
        } catch (IOException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean validateDirectoryStructure(String basePath) {
        /*
         * TODO [OPCIONAL CE1.c]: Implementar validación de estructura de directorios
         * ⚠️ MÉTODO OPCIONAL - Muy utilitario. Prioriza listUserFiles() primero
         * 
         * Pasos requeridos:
         * 1. Definir estructura esperada (ej: data/, exports/, temp/)
         * 2. Para cada directorio: verificar si existe
         * 3. Crear directorios faltantes con Files.createDirectories()
         * 4. Verificar permisos de lectura/escritura
         * 5. Retornar true si todo está correcto
         * 
         * Clases I/O requeridas:
         * - Files, Paths
         * - Files.exists(), Files.isDirectory()
         * - Files.createDirectories()
         * - Files.isReadable(), Files.isWritable()
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar validateDirectoryStructure usando Files API");
        
        // Estructura sugerida:
        // basePath/
        //   ├── data/       (archivos de datos)
        //   ├── exports/    (archivos exportados)
        //   └── temp/       (archivos temporales)

        // Creamos una lista ordenada donde guardemos los directorios //
        String[] directorios = {"data", "exports", "temp"};

        try {
            // Usamos un for-each mostrar la lista de directorios //
            for (String dir : directorios) {

                // Creamos el path para que obtenga las rutas del basepath y del los directorios mencionados antes //
                Path path = Paths.get(basePath, dir);

                // PASO 1 y 2: Crear el directorio si no existe, si no existen los creamos //
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                // PASO 3. Validar que sea realmente un directorio y si falla sacamos por pantalla el error //
                if (!Files.isDirectory(path)) {
                    System.err.println("No es un directorio: " + path);
                    return false;
                }

                // PASO 4. Validar permisos y si falla sacamos por pantalla el error //
                if (!Files.isReadable(path) || !Files.isWritable(path)) {
                    System.err.println("Permisos insuficientes: " + path);
                    return false;
                }
            }

            // PASO 5: si todo funciona sacamos true //
            return true;


        } catch (IOException e) {
            System.err.println("Error validando estructura de directorios: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String createTempFile(String prefix, String content) {
        /*
         * TODO [OPCIONAL CE1.b]: Implementar creación de archivo temporal
         * ⚠️ MÉTODO OPCIONAL - Poco valor sobre flujos. Prioriza searchTextInFile(), randomAccessRead(), convertFileEncoding()
         * 
         * Pasos requeridos:
         * 1. Usar File.createTempFile(prefix, ".tmp") para crear archivo temporal
         * 2. Obtener ruta absoluta con getAbsolutePath()
         * 3. Escribir contenido usando FileWriter
         * 4. Cerrar recursos correctamente
         * 5. Retornar ruta del archivo temporal creado
         * 
         * NOTA: Los archivos temporales se crean en directorio del sistema
         * (ej: C:\\Users\\usuario\\AppData\\Local\\Temp en Windows)
         * 
         * Clases requeridas:
         * - File.createTempFile() (método estático)
         * - FileWriter para escribir contenido
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar createTempFile usando File.createTempFile()");

        try {
            //PASO 1: Crear archivo temporal en el directorio del sistema tmp //
            File tempFile = File.createTempFile(prefix, ".tmp");

            //PASO 2: Escribimos el contenido de content con el writer, y lo guardamos el file temporal //
            // CURIOSIDAD: El FileWriter usa la codificación por defecto de la JVM(maquina virtual de java) que puede variar entre plataformas //
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(content);
            }
            // Se cierra auto por el try-with-resources //

            // PASO 3: Devolvemos la ruta absoluta del archivo que hemos creado //
            return tempFile.getAbsolutePath();

        } catch (IOException e) {
            System.err.println("Error al crear archivo temporal: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String formatTextFile(String sourceFile) {
        /*
         * TODO [OPCIONAL CE1.b]: Implementar formateador basado en ejemplo ArreglarFichero de la presentación vista en clase
         * ⚠️ MÉTODO OPCIONAL - Más String que I/O. Prioriza searchTextInFile() primero
         * 
         * Pasos requeridos (basado en ArreglarFichero de la presentación):
         * 1. Validar que archivo origen existe
         * 2. Crear archivo temporal para resultado
         * 3. Leer archivo línea por línea con BufferedReader
         * 4. Para cada línea, procesar carácter por carácter:
         *    - Eliminar espacios al principio de línea (princLinea flag)
         *    - Sustituir múltiples espacios consecutivos por uno solo (espacios flag)
         *    - Convertir primera letra de línea a mayúscula (primerLetra flag)
         *    - Mantener otros caracteres como están
         * 5. Escribir línea procesada a archivo temporal
         * 6. Retornar ruta del archivo temporal
         * 
         * Variables de control sugeridas:
         * - boolean princLinea = true (inicio de línea)
         * - boolean espacios = false (espacios consecutivos)
         * - boolean primerLetra = false (primera letra encontrada)
         */
        
        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar formatTextFile basado en ejemplo ArreglarFichero de la presentación vista en clase");
        // PASO 1: Creamos el objeto File con la ruta del archivo fuente. //
        File inputFile = new File(sourceFile);

        // Comprobamos que el archivo existe y es válido. Si no, mostramos el error. //
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("El archivo no existe o no es válido: " + sourceFile);
            return null;
        }

        try {
            // PASO 2: Creamos un archivo temporal con el prefijo "formatted_" que guardara el texto formateado. //
            // El archivo se guardará automáticamente en la carpeta temporal del sistema //
            File tempFile = File.createTempFile("formatted_", ".tmp");

            // PASO 3: Leemos el archivo con BufferedReader y utilizamos BufferedWriter para escribir el resultado. //
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                // Iniciamos el bucle que va leyendo el archivo línea por línea. //
                while ((line = reader.readLine()) != null) {
                    // Creamos un StringBuilder para ir guardando el progreso de la modificación. //
                    StringBuilder formatted = new StringBuilder();

                    // Creamos las variables para tener un control del estado de las líneas //
                    boolean princLinea = true;
                    boolean espacios = false;
                    boolean primerLetra = false;

                    // Recorremos la línea carácter por carácter con un for-each //
                    for (char c : line.toCharArray()) {
                        // Si estamos al principio de la línea y el carácter es un espacio, lo ignoramos //
                        if (princLinea && Character.isWhitespace(c)) {
                            continue;
                        }

                        // Una vez encontramos un carácter no blanco, ya no estamos al inicio de la línea //
                        princLinea = false;

                        // Si el carácter actual es un espacio en blanco ///
                        if (Character.isWhitespace(c)) {
                            // Solo agregamos un espacio si no acabamos de agregar otro (evitamos duplicados) //
                            if (!espacios) {
                                formatted.append(' ');
                                espacios = true;
                            }
                        } else {
                            // Si aún no se ha encontrado la primera letra, la convertimos a mayúscula //
                            if (!primerLetra) {
                                formatted.append(Character.toUpperCase(c));
                                primerLetra = true;
                            } else {
                                // Si no es la primera letra, simplemente la agregamos tal cual //
                                formatted.append(c);
                            }
                            // Reiniciamos la variable espacios al encontrar un carácter no blanco //
                            espacios = false;
                        }
                    }

                    // Escribimos la línea formateada en el archivo temporal. //
                    writer.write(formatted.toString());
                    writer.newLine();
                }
            }

            // Paso 4: Devolvemos la ruta absoluta del archivo temporal con el resultado. //
            return tempFile.getAbsolutePath();

        } catch (IOException e) {
            System.err.println("Error al formatear archivo: " + e.getMessage());
            return null;
        }
    }

    // ========================================================================================
    // CE1.d: ESCRITURA Y LECTURA DE INFORMACIÓN EN FORMATO XML
    // ========================================================================================

    @Override
    public List<User> readUsersFromXML(String filePath) {
        /*
         * TODO CE1.d: Implementar lectura de XML usando DOM parser
         * 
         * Pasos requeridos:
         * 1. Crear DocumentBuilderFactory y DocumentBuilder
         * 2. Usar DocumentBuilder.parse() para obtener Document
         * 3. Obtener todos los elementos "user" con getElementsByTagName()
         * 4. Para cada elemento user: extraer texto de cada campo
         * 5. Convertir texto a tipos apropiados (Long, Boolean, LocalDateTime)
         * 6. Crear objeto User con los datos extraídos
         * 
         * Clases XML requeridas:
         * - DocumentBuilderFactory, DocumentBuilder
         * - Document, Element, NodeList
         * - NO usar JAXB automático
         */

        // TODO: Implementar aquí
        //throw new UnsupportedOperationException("TODO: Implementar readUsersFromXML usando DOM parser");
        
        // ESTRUCTURA XML esperada:
        // <users>
        //   <user>
        //     <id>1</id>
        //     <name>Juan</name>
        //     ... otros campos
        //   </user>
        // </users>

        // VARIABLES //
        List<User> users = new ArrayList<>();

        try {
            // 1. Crear DocumentBuilderFactory y DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Usar DocumentBuilder.parse() para obtener Document
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();

            // 3. Obtener todos los elementos "user" con getElementsByTagName()
            NodeList userNodeList = document.getElementsByTagName("user");

            // 4. Para cada elemento user: extraer texto de cada campo
            for (int i = 0; i < userNodeList.getLength(); i++) {
                Node userNode = userNodeList.item(i);

                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;

                    // 5. Convertir texto a tipos apropiados
                    Long id = Long.parseLong(getElementTextContent(userElement, "id"));
                    String name = getElementTextContent(userElement, "name");
                    String email = getElementTextContent(userElement, "email");
                    String department = getElementTextContent(userElement, "department");
                    String role = getElementTextContent(userElement, "role");
                    Boolean active = Boolean.parseBoolean(getElementTextContent(userElement, "active"));

                    // Parsear fechas (formato ISO: yyyy-MM-ddTHH:mm:ss)
                    String createdAtStr = getElementTextContent(userElement, "createdAt");
                    LocalDateTime createdAt = LocalDateTime.parse(createdAtStr);

                    String updatedAtStr = getElementTextContent(userElement, "updatedAt");
                    LocalDateTime updatedAt = LocalDateTime.parse(updatedAtStr);

                    // 6. Crear objeto User con los datos extraídos
                    User user = new User(id, name, email, department, role);
                    user.setActive(active);
                    user.setCreatedAt(createdAt);
                    user.setUpdatedAt(updatedAt);

                    users.add(user);
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error al configurar el parser XML: " + e.getMessage(), e);
        } catch (SAXException e) {
            throw new RuntimeException("Error al parsear el XML: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo XML: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al leer usuarios desde XML: " + e.getMessage(), e);
        }

        return users;

    }

    private String getElementTextContent(Element userElement, String tagName) {
        NodeList userNodeList = userElement.getElementsByTagName(tagName);
        if (userNodeList.getLength() > 0) {
            return userNodeList.item(0).getTextContent().trim();
        }
        return "";
    }

    @Override
    public boolean writeUsersToXML(List<User> users, String filePath) {
        /*
         * TODO CE1.d: Implementar escritura de XML usando DOM y Transformer
         * 
         * Pasos requeridos:
         * 1. Crear DocumentBuilderFactory y DocumentBuilder
         * 2. Crear nuevo Document con createElement()
         * 3. Crear elemento raíz "users"
         * 4. Para cada User: crear elemento "user" con subelementos
         * 5. Usar Transformer para escribir Document a archivo
         * 6. Configurar Transformer para pretty-print (setOutputProperty)
         * 
         * Clases XML requeridas:
         * - DocumentBuilderFactory, DocumentBuilder, Document
         * - Element (createElement, appendChild, setTextContent)
         * - TransformerFactory, Transformer
         * - DOMSource, StreamResult
         */
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar writeUsersToXML usando DOM y Transformer");
    }

    @Override
    public List<User> readUsersFromXMLSAX(String filePath) {
        /*
         * TODO [OPCIONAL CE1.d]: Implementar lectura de XML usando SAX parser (alternativa eficiente)
         * ⚠️ MÉTODO OPCIONAL - Concepto avanzado. Prioriza readUsersFromXML() con DOM primero
         * 
         * Pasos requeridos:
         * 1. Crear SAXParserFactory y SAXParser
         * 2. Implementar DefaultHandler personalizado:
         *    - startElement(): detectar inicio de elementos
         *    - characters(): capturar contenido de texto
         *    - endElement(): procesar fin de elementos
         * 3. Mantener estado durante parsing (usuario actual, campo actual)
         * 4. Usar SAXParser.parse() con el handler
         * 
         * Clases SAX requeridas:
         * - SAXParserFactory, SAXParser
         * - DefaultHandler (clase anónima o interna)
         * - Atributos de estado para tracking
         */
        
        List<User> users = new ArrayList<>();
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar readUsersFromXMLSAX usando SAX parser");
        
        // PISTA: Crear clase que extienda DefaultHandler
        /*
        class UserSAXHandler extends DefaultHandler {
            // TODO: Implementar startElement, characters, endElement
        }
        */
    }

    // ========================================================================================
    // CE1.e: ESCRITURA Y LECTURA DE INFORMACIÓN EN FORMATO JSON
    // ========================================================================================

    @Override
    public List<User> readUsersFromJSON(String filePath) {
        /*
         * TODO CE1.e: Implementar lectura de JSON usando Jackson
         * 
         * Pasos requeridos:
         * 1. Validar que archivo existe
         * 2. Usar ObjectMapper.readValue() con TypeReference para List<User>
         * 3. Manejar excepciones de Jackson apropiadamente
         * 4. Retornar lista vacía si archivo está vacío
         * 
         * Clases requeridas:
         * - ObjectMapper (ya creado como campo)
         * - TypeReference<List<User>>
         * - File (para pasar a readValue)
         */
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar readUsersFromJSON usando Jackson ObjectMapper");
        
        // PISTA: objectMapper.readValue(new File(filePath), new TypeReference<List<User>>() {});
    }

    @Override
    public boolean writeUsersToJSON(List<User> users, String filePath) {
        /*
         * TODO CE1.e: Implementar escritura de JSON usando Jackson
         * 
         * Pasos requeridos:
         * 1. Crear directorios padre si no existen
         * 2. Configurar ObjectMapper para pretty-print
         * 3. Usar ObjectMapper.writeValue() para escribir a archivo
         * 4. Manejar excepciones apropiadamente
         */
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar writeUsersToJSON usando Jackson ObjectMapper");
    }

    // ========================================================================================
    // CE1.f: ESCRITURA Y LECTURA DE INFORMACIÓN EN OTROS FORMATOS ESTÁNDAR (CSV)
    // ========================================================================================

    @Override
    public List<User> readUsersFromCSV(String filePath) {
        /*
         * TODO CE1.f: Implementar lectura de CSV usando Java I/O vanilla
         * 
         * Pasos requeridos:
         * 1. Validar que el archivo existe usando Files.exists()
         * 2. Usar BufferedReader con FileReader para leer líneas
         * 3. Saltear la primera línea (cabeceras)
         * 4. Para cada línea: usar String.split(",") para separar campos
         * 5. Convertir cada línea a objeto User
         * 6. Manejar parsing de LocalDateTime desde String
         * 7. Usar try-with-resources para garantizar cierre de recursos
         * 8. Lanzar RuntimeException con mensaje descriptivo si hay errores
         * 
         * Clases Java I/O requeridas:
         * - Files, Paths (validación)
         * - FileReader, BufferedReader (lectura)
         * - String.split() (parsing)
         * - LocalDateTime.parse() (conversión fechas)
         */
        
        List<User> users = new ArrayList<>();
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar readUsersFromCSV usando BufferedReader");
        
        // EJEMPLO de estructura esperada:
        // try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        //     String line = reader.readLine(); // Saltear cabeceras
        //     while ((line = reader.readLine()) != null) {
        //         String[] fields = line.split(",");
        //         // TODO: Convertir fields a User
        //     }
        // } catch (IOException e) {
        //     throw new RuntimeException("Error leyendo CSV: " + e.getMessage(), e);
        // }
    }

    @Override
    public boolean writeUsersToCSV(List<User> users, String filePath) {
        /*
         * TODO CE1.f: Implementar escritura de CSV usando Java I/O vanilla
         * 
         * Pasos requeridos:
         * 1. Crear directorios padre si no existen usando Files.createDirectories()
         * 2. Usar PrintWriter con FileWriter para escribir
         * 3. Escribir línea de cabeceras CSV
         * 4. Para cada User: formatear campos separados por comas
         * 5. Manejar formato de LocalDateTime a String
         * 6. Usar try-with-resources
         * 7. Retornar true si exitoso, lanzar RuntimeException si error
         * 
         * Clases Java I/O requeridas:
         * - Files, Paths (crear directorios)
         * - FileWriter, PrintWriter (escritura)
         * - DateTimeFormatter (formateo fechas)
         */
        
        // TODO: Implementar aquí
        throw new UnsupportedOperationException("TODO: Implementar writeUsersToCSV usando PrintWriter");
    }


    // ========================================================================================
    // MÉTODOS AUXILIARES SUGERIDOS
    // ========================================================================================
    // Los estudiantes pueden crear estos métodos privados para ayudar en las implementaciones

    /**
     * TODO: Método auxiliar para convertir String CSV a User
     * @param csvLine Línea CSV con campos separados por comas
     * @return User creado desde la línea CSV
     */
    private User parseUserFromCSV(String csvLine) {
        // TODO: Implementar parsing de línea CSV a User
        throw new UnsupportedOperationException("TODO: Implementar parseUserFromCSV");
    }

    /**
     * TODO: Método auxiliar para convertir User a String CSV
     * @param user Usuario a convertir
     * @return Línea CSV con campos del usuario
     */
    private String userToCSV(User user) {
        // TODO: Implementar conversión de User a línea CSV
        throw new UnsupportedOperationException("TODO: Implementar userToCSV");
    }

    /**
     * TODO: Método auxiliar para crear directorios padre de un archivo
     * @param filePath Ruta del archivo
     */
    private void createParentDirectories(String filePath) {
        // TODO: Implementar creación de directorios padre
        // PISTA: Path parent = Paths.get(filePath).getParent();
        throw new UnsupportedOperationException("TODO: Implementar createParentDirectories");
    }
}
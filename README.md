EDD - FERNANDO TORRE MORA
PROYECTO 2
SuperMetroMendeley — Sistema de Gestión de Resúmenes Científicos

Este proyecto implementa un sistema para administrar resúmenes de investigaciones científicas.
Permite cargarlos desde archivos de texto, analizarlos, buscar por autores o palabras clave, y mantenerlos guardados de una sesión a otra.
Toda la aplicación utiliza estructuras de datos creadas manualmente: **HashTable**, **Árboles AVL** y **Listas simplemente enlazadas**, integradas dentro de una interfaz gráfica en Java Swing.

IMPORTANTE: PARA CORRER EL PROGRAMA USAR EL JDK. ESTAR PENDIENTE POR SI NO CORRE EL PROGRAMA.

Integrantes del equipo:

Jesus Rodriguez y Jesus Leal

Descripción general

El sistema está diseñado para recibir múltiples resúmenes en un formato estándar, procesarlos y almacenarlos eficientemente.
La HashTable funciona como repositorio principal, mientras que dos árboles AVL permiten búsquedas ordenadas tanto por autores como por palabras clave.

El programa también permite guardar todos los resúmenes en un archivo de texto para cargarlos más adelante.

Flujo del Programa

1️⃣ Pantalla de Inicio (`Bienvenido`)

Al iniciar, aparece una ventana con un botón *Iniciar*.
Aquí se crea una única instancia de `SistemaInvestigaciones`, que luego es usada por el resto del programa.

2️⃣ Elegir Método de Carga (`ElegirCarga`)

El usuario escoge entre:

* **Cargar nuevo resumen**
  Abre el módulo para importar un archivo desde el equipo.

* **Cargar resúmenes guardados**
  Carga el archivo `resumenes.txt` generado en la sesión anterior.

Desde aquí se pasa al menú principal.

---

🧠 Estructura Interna del Sistema

Todo el funcionamiento está gestionado por la clase:

```
SistemaInvestigaciones
```

Esta clase mantiene:

### ✔ HashTable de Resúmenes

* Clave → título del resumen
* Inserción y búsqueda → O(1) promedio
* Colisiones manejadas por **encadenamiento** con la clase `Lista`

### ✔ Árbol AVL de Autores

* Cada nodo representa un autor
* Cada nodo contiene una lista de títulos donde aparece

### ✔ Árbol AVL de Palabras Clave

* Cada nodo representa una palabra clave
* Cada nodo contiene los títulos asociados

Ambos AVL permiten:

* Inserciones balanceadas
* Recorridos ordenados (inorden)
* Búsquedas por coincidencia (substring)

---

# 📥 Carga de Resúmenes

El usuario selecciona un archivo en la ventana **CargarArchivo**.
El texto se pasa a `Cargar.cargarResumen()`, que:

1. Lee el título
2. Obtiene la sección de autores
3. Obtiene el cuerpo del resumen
4. Extrae las palabras clave
5. Crea un objeto `Resumen`
6. Lo registra en:

   * HashTable
   * arbolAutores
   * arbolPalabrasClave

También evita títulos duplicados revisando antes en la tabla hash.

---

# 🔍 Análisis de un Resumen

El usuario selecciona un título y se muestra:

* Título
* Autores
* Lista de palabras clave
* Frecuencia de cada palabra clave en el cuerpo

La frecuencia se calcula con una función desarrollada manualmente:

```
contarFrecuenciaPalabra()
```

La función detecta coincidencias estrictas, ignorando letras que rodeen la palabra.

---

# 🔎 Búsqueda por Palabra Clave

El usuario escribe una palabra o fragmento.

1. El AVL busca claves que contengan ese texto
2. Se extraen todos los títulos asociados
3. Se eliminan duplicados
4. Se muestran los resultados

---

# 🧑‍🏫 Búsqueda por Autor

Funciona igual que la búsqueda por palabras clave, pero usando el árbol de autores.

---

# 📚 Listado de Palabras Clave

El sistema obtiene todas las claves ordenadas alfabéticamente mediante un recorrido **inorden** del AVL.

Se muestran en pantalla y el usuario puede seleccionar una para ver todos los resúmenes asociados.

---

# 💾 Guardar y Cargar Resúmenes

## ✔ Guardar (`GuardarResumenes`)

Antes de salir, el programa recorre toda la HashTable y genera un archivo de texto en:

```
/src/test/resumenes.txt
```

El archivo conserva el formato compatible con la función de carga.

## ✔ Cargar (`Cargar.cargarDesdeArchivo`)

Cuando se selecciona *Cargar Resúmenes Anteriores*:

1. Se lee el archivo completo
2. Se divide por bloques de texto
3. Cada bloque se envía a `cargarResumen()`
4. Se reconstruye la HashTable y ambos AVL

Esto permite continuar exactamente donde quedó la sesión anterior.

---

# 🖥️ Interfaz Gráfica (Swing)

El sistema utiliza varias ventanas para permitir al usuario navegar fácilmente entre funciones:

| Ventana                | Función                          |
| ---------------------- | -------------------------------- |
| **Bienvenido**         | Inicio del sistema               |
| **ElegirCarga**        | Elegir cómo cargar los datos     |
| **CargarArchivo**      | Seleccionar archivo e importarlo |
| **Menu**               | Centro de navegación             |
| **AnalizaResumen**     | Muestra análisis detallado       |
| **BuscarPalabraClave** | Buscar por palabra clave         |
| **BuscarAutor**        | Buscar por autor                 |
| **ListarPalabras**     | Mostrar palabras clave ordenadas |

Cada ventana es sencilla y directa, con validaciones para evitar errores comunes del usuario.

# ✔️ Conclusión

El sistema implementa de forma correcta:

* Tablas Hash para acceso inmediato a los resúmenes
* Árboles AVL propios para organizar autores y palabras clave
* Búsquedas eficientes por fragmentos
* Frecuencias reales de palabras clave en los textos
* Carga y guardado persistente en archivos
* Interfaz gráfica clara y práctica



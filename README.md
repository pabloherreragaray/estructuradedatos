# Proyecto de Estructura de Datos
El proyecto "Programa para calcular la ruta mínima de un grafo" es un aplicativo de escritorio escrito en lenguaje Java, usando como interfaz gráfica los controles Swing.

El objetivo principal del programa será permitirle al usuario construir un grafo (con vértices y aristas), visualizarlo, modificarlo y finalmente, calcular una ruta mínima entre dos vértices. La ruta mínima hallada será visible en el mismo grafo. Adicionalmente, el programa mostrará la distancia de dicha ruta basándose en las distancias (pesos) de las aristas.

El proyecto está desarrollado en Java usando Eclipse como IDE.

## Descarga

Para descargar el programa haga clic en este enlace: https://github.com/pabloherreragaray/estructuradedatos/releases

## Requisitos para usarlo

* JRE 7 o superior instalado.

## Instrucciones de uso

### Edición del grafo
1. Ejecute el archivo "GrafosRutaMinima.jar". Desde Windows puede hacer doble clic en el archivo (si el JRE está correctamente configurado). En cualquier SO puede ejecutarlo con "java -jar (Ruta donde se encuentra el JAR)/GrafosRutaMinima.jar".
2. Si no ha creado un grafo antes, puede comenzar haciendo clic en el botón "Adicionar vértices" del panel lateral.
3. Al entrar en modo de adición de vértices, cada que haga clic sobre el panel blanco creará un vértice en el grafo. Por defecto los vértices se crearán con nombres automáticos que son las letras de A a la Z. Cuando llega a la Z, comienzan con A1, B1, ... Z1, A2, ..., etc. Puede nombrar los vértices manualmente si desactiva la casilla "Autonombrar vértices" del panel lateral. En ese caso el programa le preguntará por el nombre del nuevo vértice cada vez que haga clic.
4. Cuando ya no desee crear más vértices, haga clic en "Cancelar" para salir del modo de adición de vértices.
5. Luego puede hacer clic sobre el botón "Adición de aristas" del panel lateral. Con éste entra en modo de adición de aristas que es similar al de edición de vértices, solo que aquó deberá seleccionar dos vértices. Cuando seleccione el primero verá que se pone azul, cuando seleccione el segundo se creará una arista entre ambos vértices, y el programa quedará listo para crear una nueva arista.
6. Al igual que con los vértices, sale con "Cancelar".
7. Si desea eliminar una arista o vértice, haga clic en el botón "Eliminar" del panel lateral. El programa quedará a la espera de que haga clic cobre un vértice o arista. Al hacerlo se le preguntará si desea eliminarlo. Al hacer clic en "Sí" lo eliminará. Si no quiere que se le pregunte cada vez, active de la casilla "Eliminar sin preguntar".
8. Sale de este modo con "Cancelar".
9. Estando en modo de visualización (es decir, sin los modos anteriores), puede seleccionar los vértices y las aristas. Al seleccionar los vértices se pondrán azules y en el panel lateral se le mostrará el nombre, la X, la Y y con quienes está conectado. Al seleccionar una arista se pondrá negra y mostrará a quienes conecta y la distancia.
10. Puede cambiar el tamaño del grafo desde el panel lateral, en el área "Tamaño del grafo".

### Guardar y cargar un grafo
En el menú principal del aplicativo verá las siguientes opciones:
* Nuevo: crea un nuevo grafo limpio. Los cambios del grafo anterior se perderán.
* Abrir: abre un grafo previamente guardado en el disco del computador.
* Guardar/Guardar como: ambos guardan el grafo como un archivo XML en el disco del computador. Si ya guardó el grafo antes, "Guardar" lo irá guardando en la misma ubicación. "Guardar como" cambiará la ubicación.
* Cerrar: cierra el programa.

### Opciones de ejecución por línea de comandos
Si ejecuta el programa por línea de comandos puede cambiar el aspecto pasándole un parámetro adicional. Las opciones son:
* Ningún parámetro o "nimbus": abre el programa con el tema Nimbus.
* "windows": abre el programa con el tema estándar de Windows (solo funciona en Windows).
* "metal": abre el programa con el tema Metal, que es el estándar de Java.

Ejemplo: "java -jar ./GrafoRutaMinima.jar metal"

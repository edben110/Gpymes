# GPymes: guía para el trabajador

Bienvenido. Esta guía te explica, paso a paso y con ejemplos de todos los días, para qué sirve GPymes y cómo se usa. No necesitas saber nada de computadores más allá de leer y escribir datos.

---

## 1. ¿Para qué sirve GPymes?

Piensa en el cuaderno donde el dueño de una tienda anota todo: quién trabaja ahí, cuánto pagó de arriendo, cuánto le pagó a cada empleado y cuánto dinero le entró. GPymes es ese cuaderno, pero ordenado y que **hace las sumas solo**.

Con GPymes puedes:

- **Registrar negocios pequeños**, como una panadería, una tienda de barrio o un taller.
- **Llevar la lista de empleados** de cada negocio y saber en qué situación está cada uno (trabajando, de permiso, incapacitado o despedido).
- **Anotar los gastos** del negocio: arriendo, servicios, mercancía, transporte, etc.
- **Registrar el pago de nómina**, es decir, el sueldo de cada empleado.
- **Ver cuánto se ha gastado en total** y compararlo con lo que el negocio ha ganado, para saber si se está gastando de más.

> **¿Qué es una "pyme"?** Es la forma corta de decir *pequeña y mediana empresa*. En esta guía la llamamos simplemente **negocio**.

---

## 2. Los conceptos básicos

Antes de empezar, conviene tener claras estas seis palabras.

### Negocio

Es la empresa que se administra. Cada negocio tiene:

- **Un nombre**, que es obligatorio y **no se puede repetir**. No puede haber dos negocios llamados "Panaderia".
- **Sus ganancias**, que es opcional (ver más abajo).

*Ejemplo:* "Panaderia", con ganancias de 5.000.

### Empleado

Es una persona que trabaja en un negocio. Para registrarlo se necesita:

- **Nombre** (por ejemplo, Ana Torres).
- **Número de documento** (su cédula). **No se puede repetir**: dos personas no pueden tener el mismo documento.
- **Salario base**, lo que gana normalmente. Tiene que ser **mayor que cero**.
- **El negocio donde trabaja**.

Cada empleado empieza con **0 horas extra**, y siempre empieza en la situación **"laborando"** (trabajando).

### Gasto

Es cualquier dinero que sale del negocio. Cada gasto tiene:

- **El monto**, es decir, cuánto costó. Tiene que ser **mayor que cero**.
- **La categoría**, que dice en qué se gastó. Las opciones son:
  - Arriendo
  - Servicios públicos (agua, luz, gas)
  - Internet
  - Mercancía (lo que se compra para vender o producir, como harina para la panadería)
  - Mantenimiento (arreglos, reparaciones)
  - Transporte
  - Otros
- **La fecha de pago**. Es opcional: si no la escribes, se anota **la fecha y hora del momento** en que lo registras.
- **El negocio** al que pertenece.

*Ejemplo:* el arriendo del local de la panadería, 1.200, pagado el 1 de octubre.

### Nómina

Es **el pago del sueldo a un empleado**. Es un gasto más del negocio, pero especial, porque siempre va unido a una persona.

Al registrar una nómina indicas:

- **A qué empleado** se le paga.
- **El monto** pagado (mayor que cero).
- **Las deducciones**, que es opcional: lo que se le descuenta, por ejemplo salud o pensión. Si no escribes nada, queda en cero.
- **La fecha de pago**, también opcional.

La aplicación hace sola dos cosas:

1. Anota la nómina **en el negocio donde trabaja ese empleado**. No tienes que indicar el negocio.
2. La guarda como un gasto de la categoría **"nómina"**.

> **Importante:** un pago de nómina **solo** se puede registrar como nómina, eligiendo al empleado. Si intentas anotarlo como un gasto común con la categoría "nómina", la aplicación no te deja. Así ningún pago de sueldo queda sin saber a quién se le hizo.

*Ejemplo:* el sueldo de Ana Torres, 1.500, con 120 de deducciones.

> Las deducciones quedan anotadas como información. Lo que se suma a los gastos del negocio es **el monto** de la nómina.

### Ganancias

Es el dinero que el negocio ha ganado. Sirve como punto de comparación para saber si los gastos se están "comiendo" lo que entra.

- Se puede dejar en blanco al registrar el negocio y llenarlo después.
- No puede ser un número negativo.

### Total de gastos

Es **la suma de todos los gastos del negocio, incluidas las nóminas**. No tienes que calcularlo: la aplicación lo hace sola cada vez que consultas el negocio.

*Ejemplo:* arriendo de 1.200 más nómina de 1.500 da un **total de gastos de 2.700**.

---

## 3. ¿Qué puedes hacer en GPymes?

### 3.1 Registrar un negocio

Escribes el nombre del negocio y, si las conoces, sus ganancias. La aplicación le asigna un **número de identificación**: una cadena larga de letras y números que sirve para reconocer ese negocio sin confundirlo con otro. Ese número se usa después para agregarle empleados y gastos.

Al registrarlo verás el nombre, las ganancias, un total de gastos en 0 y la lista de empleados vacía.

### 3.2 Cambiar los datos de un negocio

Puedes cambiar el nombre y las ganancias.

- **El nombre siempre hay que escribirlo**, aunque no lo vayas a cambiar.
- **Las ganancias solo cambian si escribes un valor nuevo.** Si las dejas en blanco, se conservan las que ya tenía. Dejar el espacio vacío **no las borra**.

*Ejemplo:* la panadería tenía ganancias de 5.000. Cambias solo el nombre a "Panaderia Central" y dejas las ganancias en blanco: el negocio queda como "Panaderia Central", todavía con ganancias de 5.000.

### 3.3 Agregar un empleado

Escribes nombre, documento y salario base, y eliges el negocio. El empleado queda registrado como **"laborando"**.

### 3.4 Anotar un gasto

Eliges el negocio y la categoría, y escribes el monto y, si quieres, la fecha. El gasto se suma de inmediato al total del negocio.

### 3.5 Pagar una nómina

Eliges al empleado y escribes el monto y, si aplica, las deducciones. La nómina se suma sola a los gastos del negocio de ese empleado.

### 3.6 Cambiar la situación (estado) de un empleado

Cada empleado está siempre en **una** de estas cuatro situaciones:

| Situación | Qué significa | Ejemplo |
|---|---|---|
| **Laborando** | Está trabajando normalmente | Ana llega todos los días a hornear |
| **Permiso** | Tiene autorización para ausentarse | Ana pidió dos días para un trámite familiar |
| **Incapacitado** | No puede trabajar por salud | El médico le dio a Ana una semana de incapacidad |
| **Despedido** | Ya no trabaja en el negocio | Se terminó el contrato de Ana |

Las acciones disponibles son:

- **Dar permiso**: lo pasa a "permiso".
- **Incapacitar**: lo pasa a "incapacitado".
- **Despedir**: lo pasa a "despedido".
- **Reactivar**: lo devuelve a "laborando". Sirve, por ejemplo, cuando vuelve de un permiso o de una incapacidad, o cuando se le vuelve a contratar.

Desde cualquier situación se puede pasar a cualquier **otra**. Por ejemplo, de "permiso" a "incapacitado" si se enfermó durante el permiso, o de "despedido" a "laborando" si se le vuelve a contratar.

Lo que **no** se puede es pasarlo a la situación en la que **ya está**. Ver la sección 4.

> En la pantalla, la situación puede aparecer escrita con la palabra "Estado" delante, por ejemplo *EstadoLaborando* o *EstadoPermiso*. Significa lo mismo.

### 3.7 Consultar información

Puedes ver:

- **Todos los negocios**, cada uno con sus empleados, sus ganancias y su total de gastos.
- **Un negocio en particular**, o **buscarlo por su nombre**. La búsqueda debe ser con el nombre **exacto**, igual mayúsculas y minúsculas: "Panaderia" no es lo mismo que "panaderia".
- **Los empleados**: todos, uno en particular o solo los de un negocio.
- **Los gastos**: todos, uno en particular, solo los de un negocio o solo los de una categoría (por ejemplo, todo lo que se ha pagado en arriendo). En la lista de gastos **también aparecen las nóminas**, marcadas con la categoría "nómina".
- **Las nóminas**: todas, una en particular o solo las de un empleado (su historial de pagos).

### 3.8 Comparar el total de gastos con las ganancias

Para cada negocio, la aplicación te muestra el total de gastos y una frase que lo compara con las ganancias:

- Si se ha gastado **menos** de lo que se ha ganado:
  *"El total de gastos es 2700.0 y es menor a las ganancias"*. Todo en orden.
- Si se ha gastado **lo mismo o más** de lo que se ha ganado:
  *"El total de gastos es 2700.0 y es mayor o igual a las ganancias, ojo"*. Es una señal de alerta: hay que revisar en qué se está gastando.

> Si el negocio **no tiene ganancias anotadas**, la aplicación las cuenta como cero, así que cualquier gasto mostrará el mensaje de alerta. Por eso conviene mantener las ganancias al día.

---

## 4. Cuando algo sale mal

Si un dato no es correcto, la aplicación **no guarda nada** y te muestra un mensaje explicando el problema. Nada se pierde ni se daña: corriges el dato y lo intentas de nuevo.

| Lo que pasó | Qué te dice la aplicación | Qué hacer |
|---|---|---|
| Buscas un negocio, empleado, gasto o nómina que **no existe** | Que no lo encontró ("no encontrado") | Revisa que el número de identificación o el nombre estén bien escritos |
| Escribes un **monto negativo o en cero** en un gasto o nómina | Que el monto debe ser positivo | Escribe el valor real, mayor que cero |
| Escribes un **salario en cero o negativo** | Que el salario debe ser positivo | Corrige el salario |
| Escribes **ganancias negativas** | Que las ganancias no pueden ser negativas | Escribe cero o un valor positivo |
| Registras un negocio con un **nombre que ya existe** | Que el dato está repetido | Usa otro nombre, o busca el negocio que ya existe |
| Registras un empleado con un **documento que ya existe** | Que el dato está repetido | Revisa: probablemente esa persona ya está registrada |
| Dejas el **nombre en blanco** (negocio o empleado) | Que el nombre es obligatorio | Escribe el nombre |
| Intentas anotar un gasto común con la categoría **"nómina"** | Que la nómina solo se registra como pago de nómina, eligiendo al empleado | Usa la opción de pagar nómina |
| Pones a un empleado en **la situación en la que ya está** (despedir a alguien ya despedido, reactivar a alguien que ya está laborando…) | Que el empleado **ya está** en esa situación, por ejemplo *"el estado ya esta en Despedido"* | No hace falta hacer nada: el empleado ya tiene esa situación |

Dos cosas que conviene saber:

- Si pides **la lista** de empleados o gastos de un negocio que no existe, no verás un error: verás **la lista vacía**. Si esperabas ver datos, revisa que hayas elegido el negocio correcto.
- La aplicación **sí permite** registrar una nómina a un empleado despedido, por ejemplo para su liquidación. Revisa bien a quién le estás pagando.

---

## 5. Un día de ejemplo

Así sería un día normal usando GPymes en la **Panaderia** de doña Marta.

**8:00 a. m. Registrar el negocio (solo la primera vez).**
Registras el negocio "Panaderia" con ganancias de 5.000. La aplicación lo guarda con total de gastos en 0 y sin empleados.

**8:15 a. m. Agregar a la nueva empleada.**
Ana Torres empieza hoy. Registras su nombre, su documento (1012345678) y su salario base (1.500), y eliges la Panaderia. Ana queda como **laborando**.

**9:00 a. m. Anotar el arriendo.**
Doña Marta pagó el arriendo del local. Anotas un gasto de **1.200** en la categoría **arriendo** para la Panaderia.

**6:00 p. m. Pagar la nómina.**
Es día de pago. Registras la nómina de Ana: **1.500**, con **120** de deducciones. No tienes que elegir el negocio: la aplicación sabe que Ana trabaja en la Panaderia.

**6:10 p. m. Revisar cómo va el negocio.**
Consultas la Panaderia y ves:
- Ganancias: 5.000
- Total de gastos: **2.700** (1.200 de arriendo más 1.500 de nómina)
- Mensaje: *"El total de gastos es 2700.0 y es menor a las ganancias"*. Todo va bien.

**Al día siguiente: Ana se enferma.**
Ana manda su incapacidad médica. La **incapacitas**. Si por error lo intentas otra vez, la aplicación te avisa que **ya está incapacitada** y no cambia nada.

**Una semana después: Ana vuelve.**
La **reactivas** y queda otra vez como **laborando**.

**Fin de mes: actualizar las ganancias.**
Doña Marta te dice que este mes las ganancias reales fueron **2.000**. Actualizas el negocio: escribes el nombre (Panaderia) y las nuevas ganancias. Al consultar de nuevo, el mensaje cambia a:
*"El total de gastos es 2700.0 y es mayor o igual a las ganancias, ojo"*.
Esa es la señal para avisarle a doña Marta que está gastando más de lo que gana.

**Un error común: registrar dos veces el negocio.**
Si alguien intenta registrar otra vez "Panaderia", la aplicación lo rechaza porque el nombre ya existe. Así no quedan negocios duplicados.

---

## 6. Resumen rápido

- **Negocio**: nombre único, ganancias opcionales.
- **Empleado**: nombre, documento único, salario mayor que cero. Empieza "laborando".
- **Gasto**: monto mayor que cero y categoría. La nómina no se anota aquí.
- **Nómina**: pago a un empleado. Se suma sola a los gastos de su negocio.
- **Situaciones del empleado**: laborando, permiso, incapacitado, despedido. Se puede cambiar a cualquier otra, nunca a la misma.
- **Total de gastos**: suma de todo, incluidas las nóminas, comparado con las ganancias. Si dice **"ojo"**, se está gastando igual o más de lo que se gana.
- **Si algo sale mal**, no se guarda nada: lee el mensaje, corrige y vuelve a intentar.

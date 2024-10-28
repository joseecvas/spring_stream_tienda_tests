-- 9
SELECT * FROM fabricante limit 2 offset 3;

/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
select nombre, precio from producto;
/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
select codigo, nombre, precio*1.08, codigo_fabricante from producto;
/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
select UPPER(nombre), precio from producto;
/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
select nombre, UPPER(SUBSTRING(nombre, 1, 3)) from fabricante;
/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
select codigo from fabricante join producto on producto.codigo_fabricante = fabricante.codigo;
/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
select nombre from fabricante order by nombre desc;
/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
select nombre, precio from producto order by nombre asc, precio desc;
/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
select * from fabricante limit 5;
/**
 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
 */
 select * from fabricante limit 3 offset 3;
/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
select nombre, min(precio) from producto;
/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
select nombre, max(precio) from producto;
/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 *
	 */
select p.nombre from producto p where p.codigo_fabricante = 2;
/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
select nombre from producto where precio <=120;
/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
select * from producto where precio >=400;
/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
	 */
select * from producto where precio between 80 and 300;
/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
select * from producto where precio > 200 and codigo_fabricante = 6;
/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
select * from fabricante where codigo_fabricante = 1 or 3 or 5;
/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
select nombre, precio*100 from producto;
/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
select nombre from fabricante where nombre like 'S%';
/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
select * from producto where nombre like '%Portátil%';
/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
select * from producto where nombre like '%Monitor%' and precio < 215;
/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€.
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
select nombre, precio from producto where precio >=180 order by precio desc, nombre asc;
/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos.
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
select p.nombre, p.precio, f.nombre from producto as p join fabricante as f order by f.nombre asc;
/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
select p.nombre, max(p.precio), f.nombre from producto as p join fabricante as f;
/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
select * from producto where precio > 200;
/**
 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
 */
 select * from producto join fabricante where fabricante.nombre like 'Asus' or 'Hewlett-Packard' or 'Seagate';
/**
 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€.
 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
 * La salida debe quedar tabulada como sigue:
 */
select p.nombre, p.precio, f.nombre from producto p join fabricante f where precio >= 180 order by precio desc, nombre;
/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos.
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
 */
 select f.nombre, p.nombre from fabricante f left join producto p on f.codigo = p.codigo_fabricante order by f.nombre asc;
/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
select * from fabricante f left join producto p on f.id_fabricante = p.id_fabricante where p.id_producto is null;
/**
 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
 */
select count(nombre_producto) from producto;
/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
select count(f.codigo) from fabricante f join producto p on f.codigo = p.codigo_fabricante;
/**
	 * 32. Calcula la media del precio de todos los productos
	 */
Select avg(precio) from producto;
/**
 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
 */
select min(precio) from producto;
/**
 * 34. Calcula la suma de los precios de todos los productos.
 */
 select sum(precio) from producto;
/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
select count(p.codigo) from producto p join fabricante f on p.codigo_fabricante = f.codigo where f.nombre like 'Asus';
/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
select avg(p.precio) from producto p join fabricante f on p.codigo_fabricante = f.codigo where f.nombre like 'Asus';
/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial.
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
select min(p.precio) as precio_minimo, avg(p.precio) as precio_medio, max(p.precio) as precio_maximo, count(p.codigo) as numero_productos from producto p join fabricante f on p.codigo_fabricante = f.codigo where f.nombre like 'Crucial';
/**
 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes.
 * El listado también debe incluir los fabricantes que no tienen ningún producto.
 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene.
 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
 */
 select f.nombre, count(p.codigo) as numero_productos from producto p left join fabricante f on p.codigo_fabricante = f.codigo group by f.nombre order by numero_productos desc;
/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes.
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
select f.nombre, min(p.precio) as precio_minimo, avg(p.precio) as precio_medio, max(p.precio) as precio_maximo from producto p join fabricante f on p.codigo_fabricante = f.codigo group by f.nombre;
/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€.
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
select f.codigo, min(p.precio) as precio_minimo, avg(p.precio) as precio_medio, max(p.precio) as precio_maximo from producto p join fabricante f on p.codigo_fabricante = f.codigo having precio_medio > 200 group by f.codigo;
/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
select f.nombre, count(p.codigo) as numero_productos from producto p join fabricante f on p.codigo_fabricante = f.codigo having numero_productos >= 2;
/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €.
	 * Ordenado de mayor a menor número de productos.
	 */
select f.nombre, count(select p.codigo from producto p join fabricante f on p.codigo_fabricante = f.codigo where p.precio>=220) as numero_productos from producto p join fabricante f on p.codigo_fabricante = f.codigo;
/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
select f.nombre, sum(p.precio) as sumatorio_precio from producto p join fabricante f on p.codigo_fabricante = f.codigo having sumatorio_precio > 1000;
/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
select f.nombre, sum(p.precio) as sumatorio_precio from producto p join fabricante f on p.codigo_fabricante = f.codigo having sumatorio_precio > 1000 order by sumatorio_precio asc;
/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante.
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante.
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
select p.nombre, f.nombre, max(p.precio)from producto p join fabricante f on p.codigo_fabricante = f.codigo order by f.nombre asc;
/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
select (select avg(p.precio) from producto p join fabricante f on f.codigo = p.codigo_fabricante) as media_fabricante, nombre from producto where precio >= media_fabricante;

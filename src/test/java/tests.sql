
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
select codigo from fabricante join producto where producto.codigo_fabricante = fabricante.codigo;
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
select nombre from producto join fabricante where producto.codigo_fabricante = 2;
/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
select nombre from producto where precio <=120;

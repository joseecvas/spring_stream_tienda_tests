package org.iesvdm.tienda;

import org.iesvdm.tienda.modelo.Fabricante;
import org.iesvdm.tienda.modelo.Producto;
import org.iesvdm.tienda.repository.FabricanteRepository;
import org.iesvdm.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.*;


@SpringBootTest
class TiendaApplicationTests {
	@Autowired
	FabricanteRepository fabRepo;

	@Autowired
	ProductoRepository prodRepo;

	@Test
	void testAllFabricante() {
		var listFabs = fabRepo.findAll();

		listFabs.forEach(f -> {
			System.out.println(">>" + f + ":");
			f.getProductos().forEach(System.out::println);
		});
	}

	@Test
	void testAllProducto() {
		var listProds = prodRepo.findAll();

		listProds.forEach(p -> {
			System.out.println(">>" + p + ":" + "\nProductos mismo fabricante " + p.getFabricante());
			p.getFabricante().getProductos().forEach(pF -> System.out.println(">>>>" + pF));
		});
	}


	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
		var listProds = prodRepo.findAll();
		record NomPrec (String nombre, double precio){}
		var listNomPrec = listProds.stream()
		.map(p-> new NomPrec(p.getNombre(), p.getPrecio()))
				.toList();
		Assertions.assertEquals(listNomPrec.size(), 11);
		Assertions.assertEquals("Disco duro SATA3 1TB", listNomPrec.get(0).nombre);
		Assertions.assertEquals(86.99, listNomPrec.get(0).precio);
		listNomPrec.forEach(System.out::println);
	}

	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		var listProdsPrecEuro = prodRepo.findAll();
		var listPrecDolar = listProdsPrecEuro.stream().
				map(p -> {
					Producto pDolar = new Producto();
					pDolar.setCodigo(p.getCodigo());
					pDolar.setNombre(p.getNombre());
					pDolar.setPrecio(p.getPrecio() * 1.08);
					pDolar.setFabricante(p.getFabricante());
					return pDolar;
				})
				.toList();
		Assertions.assertEquals(11, listPrecDolar.size());
		Assertions.assertEquals(86.99*1.08, listPrecDolar.get(0).getPrecio());
	}

	void test2_v2_alterando_objetos_coleccion() {
		var listProdsPrecEuro = prodRepo.findAll();
		listProdsPrecEuro.forEach(Producto::eurToDol);
		listProdsPrecEuro.forEach(System.out::println);
	}

	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		var listProds = prodRepo.findAll();
		record NomPrec (String nombre, double precio){}
		var listProdsNomMayus = listProds.stream()
				.map(p -> new NomPrec(p.getNombre().toUpperCase(), p.getPrecio()))
				.toList();
		listProdsNomMayus.forEach(System.out::println);
		Assertions.assertEquals(11, listProdsNomMayus.size());
		Assertions.assertEquals("Disco duro SATA3 1TB".toUpperCase(), listProdsNomMayus.get(0).nombre);
		Assertions.assertEquals("Impresora HP Laserjet Pro M26nw".toUpperCase(), listProdsNomMayus.get(10).nombre);
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
		var listFabs = fabRepo.findAll();
		record NomPref (String nombre, String prefijo){}
		var listFabsDC = listFabs.stream().map(f -> new NomPref(f.getNombre(),f.getNombre().substring(0, 2).toUpperCase()))
				.toList();
		listFabsDC.forEach(System.out::println);
		Assertions.assertEquals(9, listFabsDC.size());
		Assertions.assertEquals("Asus", listFabsDC.get(0).nombre);
		Assertions.assertEquals("AS", listFabsDC.get(0).prefijo);
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(f->!f.getProductos().isEmpty())
				.map(Fabricante::getCodigo)
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(7, result.size());
		Assertions.assertEquals(1, result.get(0));
		Assertions.assertEquals(7, result.get(6));
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream().
		sorted(comparing(Fabricante::getNombre,reverseOrder()))
				.map(Fabricante::getNombre)
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(9, result.size());
		Assertions.assertEquals("Xiaomi", result.get(0));
		Assertions.assertEquals("Asus", result.get(8));


	}
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream().sorted(comparing(Producto::getNombre)
						.thenComparing(comparing(Producto::getPrecio, Comparator.reverseOrder())))
				.map(p -> p.getNombre())
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(11, result.size());
		Assertions.assertEquals("Disco duro SATA3 1TB", result.get(0));
		Assertions.assertEquals("Impresora HP Laserjet Pro M26nw", result.get(10));
	}
	
	/*
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.limit(5)
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(5, result.size());
		Assertions.assertEquals("Asus", result.get(0).getNombre());
		Assertions.assertEquals("Seagate", result.get(4).getNombre());
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.skip(3)
				.limit(2)
				.toList();
		result.forEach(System.out::println);
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("Samsung", result.get(0).getNombre());
		Assertions.assertEquals("Seagate", result.get(1).getNombre());
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.sorted(comparing(Producto::getPrecio))
				.map(p->p.getNombre()+p.getPrecio())
				.findFirst()
				.orElse(null);
		Assertions.assertTrue(result.contains("59.99"));
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
		var listProds = prodRepo.findAll();
		record NomPrec(String nombre, double precio){}
		var result = listProds.stream()
				.sorted(comparing(Producto::getPrecio, reverseOrder()))
				.map(p -> new NomPrec(p.getNombre(),p.getPrecio()))
				.findFirst()
				.orElse(null);
		Assertions.assertEquals(755.0, result.precio);
		Assertions.assertEquals("GeForce GTX 1080 Xtreme", result.nombre);
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
		var listProds = prodRepo.findAll();
		record NomFab(String nombre, int cod_fabricante){}
		var prodFab2 = listProds.stream()
				.filter(p->p.getFabricante().getCodigo() == 2)
				.map(p -> new NomFab(p.getNombre(), p.getFabricante().getCodigo()))
				.toList();
		Assertions.assertEquals(2, prodFab2.size());
		Assertions.assertEquals(2, prodFab2.get(0).cod_fabricante);
		Assertions.assertEquals(2, prodFab2.get(1).cod_fabricante);
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
		var listProds = prodRepo.findAll();
		record NomPrec (String nombre, double precio){}
		var result = listProds.stream()
				.filter(p->p.getPrecio()<=120)
				.map(p-> new NomPrec(p.getNombre(), p.getPrecio()))
				.toList();
		Assertions.assertEquals(3, result.size());
		Assertions.assertTrue(result.get(0).precio<=120);
		Assertions.assertTrue(result.get(1).precio<=120);
		Assertions.assertTrue(result.get(2).precio<=120);
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p->p.getPrecio()>=400)
				.toList();
		Assertions.assertEquals(3, result.size());
		Assertions.assertTrue(result.get(0).getPrecio()>=400);
		Assertions.assertTrue(result.get(1).getPrecio()>=400);
		Assertions.assertTrue(result.get(2).getPrecio()>=400);
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p->p.getPrecio()>=80 && p.getPrecio()<=300)
				.toList();
		Assertions.assertEquals(7, result.size());
		Assertions.assertTrue(result.get(0).getPrecio()>=80);
		Assertions.assertTrue(result.get(1).getPrecio()<=300);
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p->p.getPrecio()>200 && p.getFabricante().getCodigo() == 6)
				.toList();
		Assertions.assertEquals(1, result.size());
		Assertions.assertTrue(result.get(0).getPrecio()>200);
		Assertions.assertTrue(result.get(0).getFabricante().getCodigo() == 6);
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
		var listProds = prodRepo.findAll();
		Set<Integer> fabricantesSet = new HashSet<>();
		fabricantesSet.add(1);
		fabricantesSet.add(3);
		fabricantesSet.add(5);
		fabricantesSet.add(7);
		var result = listProds.stream()
				.filter(p->fabricantesSet.contains(p.getFabricante().getCodigo()))
				.toList();
		Assertions.assertEquals(6, result.size());
		Assertions.assertTrue(fabricantesSet.contains(result.get(0).getFabricante().getCodigo()));
		Assertions.assertTrue(fabricantesSet.contains(result.get(5).getFabricante().getCodigo()));
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
		var listProds = prodRepo.findAll();
		record NomPrec (String nombre, double precio){}
		var result = listProds.stream()
				.map(p-> new NomPrec(p.getNombre() ,p.getPrecio()*100))
				.toList();
		Assertions.assertEquals(11, result.size());
		Assertions.assertTrue(result.get(0).precio == 8699.0);
		Assertions.assertTrue(result.get(10).precio == 18000.0);
	}
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(f-> f.getNombre().charAt(0) == ('S'))
				.map(Fabricante::getNombre)
				.toList();
		Assertions.assertEquals(2, result.size());
		Assertions.assertTrue(result.get(0).charAt(0) == 'S');
		Assertions.assertTrue(result.get(1).charAt(0) == 'S');
	}
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p-> p.getNombre().contains("Portátil"))
				.toList();
		Assertions.assertEquals(2, result.size());
		Assertions.assertTrue(result.get(0).getNombre().contains("Portátil"));
		Assertions.assertTrue(result.get(1).getNombre().contains("Portátil"));
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p-> p.getNombre().contains("Monitor") && p.getPrecio()<215)
				.toList();
		Assertions.assertEquals(1, result.size());
		Assertions.assertTrue(result.get(0).getNombre().contains("Monitor"));
		Assertions.assertTrue(result.get(0).getPrecio()<215);
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p -> p.getPrecio() >= 180)
				.sorted(comparing(Producto::getPrecio, reverseOrder())
				.thenComparing(Producto::getNombre))
				.map(p-> p.getNombre() + p.getPrecio())
				.toList();
		Assertions.assertEquals(7, result.size());
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		var listProds = prodRepo.findAll();
		record NomPrecFab (String nombre, double precio, String nom_fabricante){}
		var result = listProds.stream()
				.sorted(comparing(p -> p.getFabricante().getNombre()))
				.map(p -> new NomPrecFab(p.getNombre(), p.getPrecio(), p.getFabricante().getNombre()))
				.toList();
		System.out.println(result.toString());
		Assertions.assertEquals(11, result.size());
		Assertions.assertTrue(result.get(0).nom_fabricante.compareTo(result.get(1).nom_fabricante) <= 0);
		Assertions.assertTrue(result.get(9).nom_fabricante.compareTo(result.get(10).nom_fabricante) <= 0);
	}
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		var listProds = prodRepo.findAll();
		listProds.stream()
				.sorted(comparing(Producto::getPrecio,reverseOrder()))
				.findFirst()
				.orElse(null);
	}

	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.filter(p -> p.getFabricante().getNombre().equals("Crucial") && p.getPrecio()>200)
				.toList();
		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Crucial", result.get(0).getFabricante().getNombre());
		Assertions.assertTrue(result.get(0).getPrecio()>200);
	}
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		var listProds = prodRepo.findAll();
		Set<String> setFabricante = new HashSet<>();
		setFabricante.add("Asus");
		setFabricante.add("Hewlett-Packard");
		setFabricante.add("Seagate");
		var result = listProds.stream()
				.filter(p->setFabricante.contains(p.getFabricante().getNombre()))
				.toList();
		Assertions.assertEquals(5, result.size());
		Assertions.assertTrue(setFabricante.contains(result.get(0).getFabricante().getNombre()));
		Assertions.assertTrue(setFabricante.contains(result.get(1).getFabricante().getNombre()));
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

	Producto                Precio             Fabricante
	-----------------------------------------------------
	GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
	Portátil Yoga 520      |452.79            |Lenovo
	Portátil Ideapd 320    |359.64000000000004|Lenovo
	Monitor 27 LED Full HD |199.25190000000003|Asus

	 */
	@Test
	void test27() {
		var listProds = prodRepo.findAll();
		var listProdsMayor180 = listProds.stream()
				.sorted(comparing(Producto::getPrecio, reverseOrder()).thenComparing(Producto::getNombre))
				.filter(p -> p.getPrecio() >= 180)
				.map(p -> p.getNombre() + "/" + p.getPrecio() + "/" + p.getFabricante().getNombre())
				.toList();
		System.out.println("Producto                       Precio             Fabricante");
		System.out.println("------------------------------------------------------------");
		listProdsMayor180.forEach(System.out::println);
		Assertions.assertEquals(7, listProdsMayor180.size());
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
		var listfabs = fabRepo.findAll();
		listfabs.stream()
						.map(f -> "Fabricante: " + f.getNombre() + "\n\n"
						+ "\tProductos:\n\t" + f.getProductos()
										.stream()
												.map(p -> p.getNombre() + "\n")
												.collect(joining()))
				.forEach(System.out::println);
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
		var listFabs = fabRepo.findAll();
		var result = listFabs.stream()
				.filter(f -> f.getProductos().isEmpty())
				.toList();
		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals(0, result.get(0).getProductos().size());
		Assertions.assertEquals(0, result.get(1).getProductos().size());
	}

	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
		var listProds = prodRepo.findAll();
		var nProductos = listProds.stream()
				.collect(counting());
		Assertions.assertEquals(11, nProductos);
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
		var listProds = prodRepo.findAll();
		var nfabricantesProd = listProds.stream()
				.map(Producto::getFabricante)
				.distinct()
				.count();
		Assertions.assertEquals(7, nfabricantesProd);
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
		var listProds = prodRepo.findAll();
		var mediaPrecio = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0); //La media de los productos es 271.7236363636364
		Assertions.assertEquals(271.7236363636364, mediaPrecio);
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
		var listProds = prodRepo.findAll();
		var result = listProds.stream()
				.min(comparing(Producto::getPrecio))
				.orElse(null);
		Assertions.assertEquals("Impresora HP Deskjet 3720", result.getNombre());
	}
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		var listProds = prodRepo.findAll();
		var sumaPrecio = listProds.stream()
				.mapToDouble(Producto::getPrecio)
				.sum(); // suma = 2988.96
		Assertions.assertEquals(2988.96, sumaPrecio);
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		var listProds = prodRepo.findAll();
		var nAsus = listProds.stream()
				.filter(p -> p.getFabricante().getNombre().equals("Asus"))
				.count();
		Assertions.assertEquals(2, nAsus);
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		var listProds = prodRepo.findAll();
		var mediaAsus = listProds.stream()
				.filter(p -> p.getFabricante().getNombre().equals("Asus"))
				.mapToDouble(Producto::getPrecio)
				.average()
				.orElse(0);
		Assertions.assertEquals(223.995, mediaAsus);
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		var listProds = prodRepo.findAll();
		Double[] resultados = listProds.stream()
				.filter(producto -> "Crucial".equals(producto.getFabricante().getNombre()))
				.map(Producto::getPrecio)
				.reduce(new Double[]{Double.MIN_VALUE, Double.MAX_VALUE, 0.0, 0.0},
						(acumulador, precio) -> {
							acumulador[0] = Math.max(acumulador[0], precio);
							acumulador[1] = Math.min(acumulador[1], precio);
							acumulador[2] += precio;
							acumulador[3] += 1;
							return acumulador;
						},
						(acum1, acum2) -> {
							acum1[0] = Math.max(acum1[0], acum2[0]);
							acum1[1] = Math.min(acum1[1], acum2[1]);
							acum1[2] += acum2[2];
							acum1[3] += acum2[3];
							return acum1;
						}
				);
		resultados[2] = resultados[3] > 0 ? resultados[2] / resultados[3] : 0.0;
		Assertions.assertEquals(755.0, resultados[0]);
		Assertions.assertEquals(120.0, resultados[1]);
		Assertions.assertEquals(437.5, resultados[2]);
		Assertions.assertEquals(2.0, resultados[3]);
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
		var listFabs = fabRepo.findAll();
		record NomProds (String nombre, int productos){}
	var result = listFabs.stream()
				.map(f -> new NomProds(f.getNombre(), f.getProductos().size()))
			.toList();
		String encabezado = String.format("%20s %20s%n", "Fabricante", "Productos");
		encabezado+= "*-".repeat(encabezado.length()/2);
		System.out.println(encabezado);
		for (NomProds tupla : result){
			System.out.println(String.format("%20s %20d", tupla.nombre(), tupla.productos()));
		}
		Assertions.assertEquals(9, result.size());
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
		var listFabs = fabRepo.findAll();
 		//TODO
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
		var listFabs = fabRepo.findAll();
		//TODO
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		var listFabs = fabRepo.findAll();
		//TODO
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		var listFabs = fabRepo.findAll();
		//TODO
	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		var listFabs = fabRepo.findAll();
		//TODO
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		var listFabs = fabRepo.findAll();
		//TODO	
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		var listFabs = fabRepo.findAll();
		//TODO
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		var listFabs = fabRepo.findAll();
		//TODO
	}

}

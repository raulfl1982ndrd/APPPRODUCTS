# APPPRODUCTS
Version funcional de la aplicacion con:

• Cuatro actividades diferentes una que carga los datos del API en la base de datos, otra que muestra
el listado de categorias otra que muestra los listado de  productos y otra que muestra el detalle 
del producto

• Se pasa el extra category del activity de la categoria al activity del listado de productos para 
recuperar los datos de los productos de la categoria seleccionada  si no se pasa categoria se 
recuperan todos los productos y el id del producto de la activity de listado de productos al 
activity de detalle que recupera los datos de el productos concreto.

• En la sesion se guarda si se ha cargado ya la base de datos desde el api

• Se crea la tabla products en la base de datos products.db

• Se llama al API https://dummyjson.com/products/?limit=0

•  Utiliza un RecyclerView para mostrar una lista de categorias, y capturar el evento de clic en 
cada elemento con una función lambda para ir a la actividad de Producto con el extra de la category 
o con Todas como categoria.

•  Utiliza un RecyclerView para mostrar una lista de productos, y capturar el evento de clic en cada
elemento con una función lambda para ir a la actividad de Detalle con el etra del id del producto.

• Mostrar de busqueda en  el AppBar (barra superior) tanto para categorias como para productos

• Internacionalización con string

• Usar ViewBinding en vez de findViewById



falta por hacer :

• Debe mostrar al menos un diálogo en respuesta a una acción
del usuario.

• Investigar y usar TextField de Material Design
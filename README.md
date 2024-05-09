Se trata de una aplicación de visualización de información de películas.

La funcionalidad es sencilla, lee la información de las peliculas mediante Retrofit y la API de 'themoviedb', utilizando dos fuentes diferentes, una de películas en cartelera y otra de películas populares.
La lista de películas obtenida se representa mediante un Recycler View de tipo GridLayout, en el que cada item es un CardView que incluye la imagen del poster de la película, un elemento 'CircularProgressIndicator' que indica la puntuación obtenida de los usuarios que votan a través de la Web, y un botón 'Favoritos'para añadir cada item a películas favoritas, que se guardan en una base de datos Room. Cada CardView se puede pulsar y accedes a información más detallada de la película seleccionada.

Consta de tres fragments: 
  1. Portada con tres botones para acceder a cada lista de películas (Cartelera, populares y favoritas)
  2. Home que incluye el Recyclerview con las películas obtenidas bien de Retrofit o de Room. Consta de tres botones para visualizar la lista deseada (Cartelera, populares y favoritas).
     Se usa el mismo fragment para las tres listas, actualizando la lista según el botón que se pulse. El botón seleccionado cambia de color para que visualmente sepamos en que lista estamos posicionados.
  3. PeliculaInfo que incluye información de la película seleccionada. Consta de la imagen del poster de la película, el título y una breve descripción.

La aplicación fué diseñada utilizando la arquitectura MVVM.

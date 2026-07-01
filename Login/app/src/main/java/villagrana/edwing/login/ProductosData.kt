package villagrana.edwing.login


object ProductosData {

    val productos = listOf(
        Producto(
            id = 1,
            nombre = "The Legend of Zelda",
            precio = 1299.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de aventura y exploración en mundo abierto, ideal para quienes disfrutan resolver acertijos y descubrir secretos."
        ),
        Producto(
            id = 2,
            nombre = "Super Mario Odyssey",
            precio = 1199.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de plataformas con mundos coloridos, niveles creativos y una jugabilidad muy divertida."
        ),
        Producto(
            id = 3,
            nombre = "God of War Ragnarok",
            precio = 1399.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de acción y aventura con una historia intensa, combates fuertes y una ambientación basada en mitología nórdica."
        ),
        Producto(
            id = 4,
            nombre = "Halo Infinite",
            precio = 999.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Shooter de ciencia ficción con campaña, multijugador y combates rápidos en escenarios futuristas."
        ),
        Producto(
            id = 5,
            nombre = "Minecraft",
            precio = 699.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de construcción, exploración y supervivencia donde puedes crear prácticamente cualquier cosa."
        ),
        Producto(
            id = 6,
            nombre = "Elden Ring",
            precio = 1499.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de rol y acción en mundo abierto, con combates difíciles, exploración libre y jefes desafiantes."
        ),
        Producto(
            id = 7,
            nombre = "Resident Evil 4",
            precio = 1099.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de terror y acción donde debes sobrevivir enfrentando enemigos, resolviendo acertijos y administrando recursos."
        ),
        Producto(
            id = 8,
            nombre = "FC 26",
            precio = 1299.99,
            imagen = R.drawable.ic_launcher_foreground,
            descripcion = "Juego de fútbol con equipos, torneos, modo carrera y partidas competitivas."
        )
    )

    fun filtrarProductosPorNombre(texto: String): List<Producto> {

        if (texto.isBlank()) {
            return productos
        }

        return productos.filter { producto ->
            producto.nombre.contains(texto, ignoreCase = true)
        }
    }

    fun obtenerProductoPorId(id: Int): Producto? {

        return productos.find { producto ->
            producto.id == id
        }
    }


}
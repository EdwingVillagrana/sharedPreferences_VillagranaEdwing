package villagrana.edwing.login

import android.content.Context

class PreferenceManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "tienda_preferences",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_CARRITO = "carrito"
        private const val KEY_LOGGED_IN = "logged_in"
    }

    // Guarda el estado de login, si todavía usas la práctica del login
    fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_LOGGED_IN, isLoggedIn)
            .apply()
    }

    // Devuelve si el usuario ya inició sesión
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
    }

    // Cierra la sesión
    fun logout() {
        sharedPreferences.edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .apply()
    }

    // Guarda el carrito en SharedPreferences.
    // Se guardan solo los id de los productos separados por coma.
    fun guardarCarrito(productosCarrito: List<Producto>) {
        val idsProductos = productosCarrito.joinToString(separator = ",") { producto ->
            producto.id.toString()
        }

        sharedPreferences.edit()
            .putString(KEY_CARRITO, idsProductos)
            .apply()
    }

    // Recupera los id guardados del carrito
    private fun obtenerIdsCarrito(): List<Int> {
        val idsGuardados = sharedPreferences.getString(KEY_CARRITO, "") ?: ""

        if (idsGuardados.isBlank()) {
            return emptyList()
        }

        return idsGuardados.split(",").mapNotNull { idTexto ->
            idTexto.toIntOrNull()
        }
    }

    // Devuelve la lista de productos del carrito usando los id guardados
    fun obtenerCarrito(): List<Producto> {
        val idsCarrito = obtenerIdsCarrito()

        return idsCarrito.mapNotNull { idProducto ->
            ProductosData.obtenerProductoPorId(idProducto)
        }
    }

    // Limpia el carrito guardado
    fun limpiarCarrito() {
        sharedPreferences.edit()
            .remove(KEY_CARRITO)
            .apply()
    }
}
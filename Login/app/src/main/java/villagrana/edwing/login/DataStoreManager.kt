package villagrana.edwing.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "tienda_datastore"
)

class DataStoreManager(
    private val context: Context
) {

    companion object {
        private val CARRITO_KEY = stringPreferencesKey("carrito")
    }

    val carritoFlow: Flow<List<Producto>> = context.dataStore.data.map { preferences ->

        val idsGuardados = preferences[CARRITO_KEY] ?: ""

        if (idsGuardados.isBlank()) {
            emptyList()
        } else {
            idsGuardados
                .split(",")
                .mapNotNull { idTexto ->
                    idTexto.toIntOrNull()
                }
                .mapNotNull { idProducto ->
                    ProductosData.obtenerProductoPorId(idProducto)
                }
        }
    }

    suspend fun guardarCarrito(productosCarrito: List<Producto>) {

        val idsProductos = productosCarrito.joinToString(separator = ",") { producto ->
            producto.id.toString()
        }

        context.dataStore.edit { preferences ->
            preferences[CARRITO_KEY] = idsProductos
        }
    }

    suspend fun limpiarCarrito() {
        context.dataStore.edit { preferences ->
            preferences.remove(CARRITO_KEY)
        }
    }
}
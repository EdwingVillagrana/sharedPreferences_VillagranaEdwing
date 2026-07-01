package villagrana.edwing.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStoreManager = DataStoreManager(this)

        setContent {
            AppTienda(
                dataStoreManager = dataStoreManager
            )
        }
    }
}

@Composable
fun AppTienda(
    dataStoreManager: DataStoreManager
) {
    var pantallaActual by remember {
        mutableStateOf(PantallaActual.TIENDA)
    }

    var idProductoSeleccionado by remember {
        mutableStateOf<Int?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    val productosCarrito by dataStoreManager.carritoFlow.collectAsState(
        initial = emptyList()
    )

    fun agregarProductoAlCarrito(producto: Producto) {

        val nuevoCarrito = productosCarrito + producto

        coroutineScope.launch {
            dataStoreManager.guardarCarrito(nuevoCarrito)
        }
    }

    when (pantallaActual) {

        PantallaActual.TIENDA -> {
            PantallaTienda(
                onCarritoClick = {
                    pantallaActual = PantallaActual.CARRITO
                },
                onProductoClick = { idProducto ->
                    idProductoSeleccionado = idProducto
                    pantallaActual = PantallaActual.DETALLE
                },
                onAgregarCarritoClick = { producto ->
                    agregarProductoAlCarrito(producto)
                }
            )
        }

        PantallaActual.DETALLE -> {
            PantallaDetalleProducto(
                producto = idProductoSeleccionado?.let { id ->
                    ProductosData.obtenerProductoPorId(id)
                },
                onRegresarClick = {
                    pantallaActual = PantallaActual.TIENDA
                },
                onAgregarCarritoClick = { producto ->
                    agregarProductoAlCarrito(producto)
                }
            )
        }

        PantallaActual.CARRITO -> {
            PantallaCarrito(
                productosCarrito = productosCarrito,
                onRegresarClick = {
                    pantallaActual = PantallaActual.TIENDA
                }
            )
        }
    }
}
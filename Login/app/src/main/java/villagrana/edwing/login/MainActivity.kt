package villagrana.edwing.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferenceManager = PreferenceManager(this)

        setContent {
            AppTienda(
                preferenceManager = preferenceManager
            )
        }
    }
}

@Composable
fun HomeScreen(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido al Home!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onLogoutClick() }) {
            Text("Iniciar sesión")
        }
    }
}

@Composable
fun AppTienda(
    preferenceManager: PreferenceManager
) {
    // Pantalla que se está mostrando actualmente
    var pantallaActual by remember {
        mutableStateOf(PantallaActual.TIENDA)
    }

    // Id del producto seleccionado para ver su detalle
    var idProductoSeleccionado by remember {
        mutableStateOf<Int?>(null)
    }

    // Lista del carrito en memoria
    val productosCarrito = remember {
        mutableStateListOf<Producto>()
    }

    // Cuando la app abre, cargamos el carrito guardado en SharedPreferences
    LaunchedEffect(Unit) {
        productosCarrito.clear()
        productosCarrito.addAll(preferenceManager.obtenerCarrito())
    }

    // Función para agregar productos al carrito
    fun agregarProductoAlCarrito(producto: Producto) {
        productosCarrito.add(producto)

        // Después de agregarlo en memoria, también lo guardamos en SharedPreferences
        preferenceManager.guardarCarrito(productosCarrito)
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

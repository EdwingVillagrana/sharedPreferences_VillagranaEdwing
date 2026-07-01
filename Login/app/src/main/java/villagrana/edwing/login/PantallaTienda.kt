package villagrana.edwing.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PantallaTienda(
    onCarritoClick: () -> Unit,

    onProductoClick: (Int) -> Unit,

    onAgregarCarritoClick: (Producto) -> Unit
) {
    var textoBusqueda by remember { mutableStateOf("") }

    val productosFiltrados = ProductosData.filtrarProductosPorNombre(textoBusqueda)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = onCarritoClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir al carrito")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { nuevoTexto ->
                    textoBusqueda = nuevoTexto
                },
                label = {
                    Text(text = "Buscar producto")
                },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    textoBusqueda = ""
                }
            ) {
                Text(text = "Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productosFiltrados) { producto ->
                ProductoItem(
                    producto = producto,
                    onProductoClick = {
                        onProductoClick(producto.id)
                    },
                    onAgregarCarritoClick = {
                        onAgregarCarritoClick(producto)
                    }
                )
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onProductoClick: () -> Unit,
    onAgregarCarritoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        onProductoClick()
                    }
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
                    .clickable {
                        onProductoClick()
                    }
            ) {

                Text(text = producto.nombre)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "$${producto.precio}")
            }

            Button(
                onClick = onAgregarCarritoClick
            ) {
                Text(text = "Agregar")
            }
        }
    }
}
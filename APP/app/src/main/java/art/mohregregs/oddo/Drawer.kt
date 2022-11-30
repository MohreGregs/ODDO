package art.mohregregs.oddo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class DrawerScreens(val title: String, val route: String){
    object Home: DrawerScreens("Home", "home")
    object Login: DrawerScreens("Login", "login")
    object HomeOrder: DrawerScreens("Welcome", "homeOrder")
    object Order: DrawerScreens("Order", "order")
    object Checkout: DrawerScreens("Checkout", "checkout")
    object CurrentOrders: DrawerScreens("Current Orders", "currentOrders")
}

private val screens = listOf(
    DrawerScreens.Order,
    DrawerScreens.CurrentOrders
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
){
    Column(
        modifier.fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        screens.forEach{screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }
    }
}
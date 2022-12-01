package art.mohregregs.oddo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

sealed class DrawerScreens(val title: Int, val route: String){
    object Home: DrawerScreens(R.string.home, "home")
    object Login: DrawerScreens(R.string.login, "login")
    object HomeOrder: DrawerScreens(R.string.app_name, "homeOrder")
    object Order: DrawerScreens(R.string.order, "order")
    object Checkout: DrawerScreens(R.string.checkout, "checkout")
    object CurrentOrders: DrawerScreens(R.string.current_order, "currentOrders")
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
    val context = LocalContext.current
    Column(
        modifier.fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        screens.forEach{screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = context.getString(screen.title),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }
    }
}
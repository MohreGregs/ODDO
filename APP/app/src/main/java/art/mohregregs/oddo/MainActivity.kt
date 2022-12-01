package art.mohregregs.oddo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import art.mohregregs.oddo.ui.theme.ODDOTheme
import art.mohregregs.oddo.views.*
import art.mohregregs.oddo.views.viewmodel.OrderViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object MainActivity{
        val LocalMutableContext = staticCompositionLocalOf<MutableState<Context>> {
            error("LocalMutableContext not provided")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderViewModel by viewModels<OrderViewModel> ()

        setContent {
            val context = LocalContext.current
            CompositionLocalProvider(
                LocalMutableContext provides remember { mutableStateOf(context) },
            ) {
                CompositionLocalProvider(
                    LocalContext provides LocalMutableContext.current.value,
                ) {
                    ODDOTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                            AppMainScreen(orderViewModel)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun AppMainScreen(
    orderViewModel: OrderViewModel
){
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(navController)},
        drawerContent = { Drawer(
            onDestinationClicked = { route ->
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                navController.navigate(route){
                    launchSingleTop = true
                }
            }
        )}
    ) {
        NavHost(navController = navController, startDestination = DrawerScreens.Home.route){
            composable(DrawerScreens.Home.route){
                Home(navController)
            }

            composable(DrawerScreens.Login.route){
                Login(navController)
            }

            composable(DrawerScreens.HomeOrder.route){
                HomeOrder(navController, orderViewModel)
            }

            composable(DrawerScreens.Order.route){
                Order(navController, orderViewModel)
            }

            composable(DrawerScreens.Checkout.route){
                Checkout(navController)
            }

            composable(DrawerScreens.CurrentOrders.route){
                CurrentOrders(navController)
            }
        }

    }
}
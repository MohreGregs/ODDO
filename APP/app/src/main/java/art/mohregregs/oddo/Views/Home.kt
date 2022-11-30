package art.mohregregs.oddo.Views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens

@Composable
fun Home(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeButton("Login as waiter"){
            navController.navigate(DrawerScreens.Login.route)
        }
        HomeButton("Device for guest"){
            navController.navigate(DrawerScreens.HomeOrder.route)
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp),
        onClick = { onClick() }
    ) {
        Text(text = text, fontSize = 30.sp)
    }
}
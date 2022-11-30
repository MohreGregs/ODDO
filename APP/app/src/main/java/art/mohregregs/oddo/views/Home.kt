package art.mohregregs.oddo.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R

@Composable
fun Home(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeButton(stringResource(R.string.login_as_waiter)){
            navController.navigate(DrawerScreens.Login.route)
        }
        HomeButton(stringResource(R.string.device_for_guest)){
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
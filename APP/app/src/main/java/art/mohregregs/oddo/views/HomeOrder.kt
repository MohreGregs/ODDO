package art.mohregregs.oddo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.R

@Composable
fun HomeOrder(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to", fontSize = 25.sp)
        Text(text = "<restaurant name>", fontSize = 25.sp)

        Row() {
            languages.forEach{language ->
                LanguageButton(language = language)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp),
            onClick = { navController.navigate(DrawerScreens.Order.route) }
        ) {
            Text(text = "Start Ordering")
        }
    }
}

@Composable
fun LanguageButton(language: Languages){
    Button(
        modifier = Modifier
            .width(90.dp)
            .height(50.dp)
            .padding(5.dp),
        onClick = { /*TODO: Load Language*/ }
    ) {
        Image(painter = painterResource(id =  language.flagId), contentDescription = language.name)
    }
}

sealed class Languages(var name: String, var flagId: Int){
    object English: Languages("English", R.drawable.flag_of_the_united_kingdom)
    object German: Languages("German", R.drawable.flag_of_germany)
}

private val languages = listOf(
    Languages.English,
    Languages.German
)
package art.mohregregs.oddo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import art.mohregregs.oddo.DrawerScreens
import art.mohregregs.oddo.MainActivity.MainActivity.LocalMutableContext
import art.mohregregs.oddo.R
import java.util.Locale

@Composable
fun HomeOrder(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.welcome_to), fontSize = 25.sp)
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
            Text(text = stringResource(R.string.start_order))
        }
    }
}

@Composable
fun LanguageButton(language: Languages){
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var mutableContext = LocalMutableContext.current
    Button(
        modifier = Modifier
            .width(90.dp)
            .height(50.dp)
            .padding(5.dp),
        onClick = {
            val locale = Locale(language.locale)
            configuration.setLocale(locale)
            mutableContext.value = context.createConfigurationContext(configuration)
        }
    ) {
        Image(painter = painterResource(id =  language.flagId), contentDescription = context.getString(language.name))
    }
}

sealed class Languages(var name: Int, var flagId: Int, var locale: String){
    object English: Languages(R.string.eglish, R.drawable.flag_of_the_united_kingdom, "en")
    object German: Languages(R.string.german, R.drawable.flag_of_germany, "de")
}

private val languages = listOf(
    Languages.English,
    Languages.German
)
package nl.cqit.legasee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import legasee.composeapp.generated.resources.Res
import legasee.composeapp.generated.resources.compose_multiplatform
import nl.cqit.legasee.components.PersonNode
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        Column {
            Text(
                text = "Ancestry Tree",
                style = MaterialTheme.typography.h2
            )
            Content()
        }
    }
}

@Composable
fun Content() {
    val cc007 = AncestorTree.Person(
        AncestorTree.PersonalInfo(
            "https://avatars.githubusercontent.com/u/5381337",
            "CC007",
            "",
            "01-01-1994",
        ),
        listOf()
    )
    PersonNode(cc007)
}

@Composable
fun DefaultSplashScreen() {
    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}
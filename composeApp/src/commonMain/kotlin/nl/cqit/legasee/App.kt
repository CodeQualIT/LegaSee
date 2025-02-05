package nl.cqit.legasee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import legasee.composeapp.generated.resources.Res
import legasee.composeapp.generated.resources.compose_multiplatform
import nl.cqit.legasee.components.common.PersonNode
import org.jetbrains.compose.resources.painterResource

@Composable
fun App() {
    MaterialTheme {
        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .zIndex(1f),
                text = "Ancestry Tree",
                style = MaterialTheme.typography.h2
            )
            Content()
        }
    }
}

@Composable
fun Content() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        offset += offsetChange
    }
    val cc007 = AncestorTree.Person(
        AncestorTree.PersonalInfo(
            "https://avatars.githubusercontent.com/u/5381337",
            "CC007",
            "",
            "01-01-1994",
        ),
        listOf()
    )
    val xenaphos = AncestorTree.Person(
        AncestorTree.PersonalInfo(
            "https://cdn.discordapp.com/avatars/148991203910746122/ae5f17ed642b28477a1bb093bd669acb.webp?size=128",
            "Xenaphos",
            "",
            "01-01-1995",
        ),
        listOf()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state = state)
    ) {
        PersonNode(cc007)

        PersonNode(xenaphos, IntOffset(0, 200))
    }
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
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import legasee.composeapp.generated.resources.Res
import legasee.composeapp.generated.resources.compose_multiplatform
import nl.cqit.legasee.components.common.PersonNode
import nl.cqit.legasee.components.common.RelationshipEdge
import org.jetbrains.compose.resources.painterResource
import kotlin.text.toInt

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
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
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
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
    val cc007Position = remember { mutableStateOf(IntOffset.Zero) }
    val cc007Size = remember { mutableStateOf(IntSize.Zero) }

    val xenaphos = AncestorTree.Person(
        AncestorTree.PersonalInfo(
            "https://cdn.discordapp.com/avatars/148991203910746122/ae5f17ed642b28477a1bb093bd669acb.webp?size=128",
            "Xenaphos long name here",
            "",
            "01-01-1995",
        ),
        listOf()
    )
    val xenaphosPosition = remember { mutableStateOf(IntOffset.Zero) }
    val xenaphosSize = remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offset.x,
                translationY = offset.y
            )
            .transformable(state = state)
    ) {
        PersonNode(cc007, Modifier
            .offset { IntOffset(0, 0) }
            .onGloballyPositioned { coordinates ->
                cc007Position.value = coordinates.positionInParent().toIntOffset()
                cc007Size.value = coordinates.size
            }
        )
        PersonNode(xenaphos, Modifier
            .offset { IntOffset(0, 400) }
            .onGloballyPositioned { coordinates ->
                xenaphosPosition.value = coordinates.positionInParent().toIntOffset()
                xenaphosSize.value = coordinates.size
            }
        )
        RelationshipEdge(cc007Position, cc007Size, xenaphosPosition, xenaphosSize)
    }
}

private fun Offset.toIntOffset(): IntOffset {
    return IntOffset(x.toInt(), y.toInt())
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
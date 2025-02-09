@file:OptIn(ExperimentalCoroutinesApi::class)

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import legasee.composeapp.generated.resources.Res
import legasee.composeapp.generated.resources.compose_multiplatform
import nl.cqit.legasee.components.common.PersonNode
import nl.cqit.legasee.components.common.RelationshipEdge
import nl.cqit.legasee.services.AvatarGenerator
import org.jetbrains.compose.resources.painterResource

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

    var father by remember { mutableStateOf<AncestorTree.Person?>(null) }
    val fatherPosition = remember { mutableStateOf(IntOffset.Zero) }
    val fatherSize = remember { mutableStateOf(IntSize.Zero) }

    var mother by remember { mutableStateOf<AncestorTree.Person?>(null) }
    val motherPosition = remember { mutableStateOf(IntOffset.Zero) }
    val motherSize = remember { mutableStateOf(IntSize.Zero) }

    var kid by remember { mutableStateOf<AncestorTree.Person?>(null) }
    val kidPosition = remember { mutableStateOf(IntOffset.Zero) }
    val kidSize = remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(Unit) {
        father = AncestorTree.Person(
            AncestorTree.PersonalInfo(
                "https://this-person-does-not-exist.com/${AvatarGenerator.getRandomAvatarUrl(AvatarGenerator.Gender.MALE)}",
                "John Doe",
                "",
                "02-05-1965",
            ),
            listOf()
        )

        mother = AncestorTree.Person(
            AncestorTree.PersonalInfo(
                "https://this-person-does-not-exist.com/${AvatarGenerator.getRandomAvatarUrl(AvatarGenerator.Gender.FEMALE)}",
                "Jaqueline Marianna Doe",
                "",
                "14-08-1967",
            ),
            listOf()
        )

        kid = AncestorTree.Person(
            AncestorTree.PersonalInfo(
                "https://this-person-does-not-exist.com/${AvatarGenerator.getRandomAvatarUrl(AvatarGenerator.Gender.MALE, AvatarGenerator.AgeGroup.ADOLESCENT)}",
                "John Doe Jr.",
                "",
                "12-12-1993",
            ),
            listOf(
                AncestorTree.ParentalFigure(father!!, AncestorTree.ParentalFigureType.Father),
                AncestorTree.ParentalFigure(mother!!, AncestorTree.ParentalFigureType.Mother),
            )
        )
    }

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
        kid?.let {
            PersonNode(
                it, Modifier
                    .offset { IntOffset(350, 400) }
                    .onGloballyPositioned { coordinates ->
                        kidPosition.value = coordinates.positionInParent().toIntOffset()
                        kidSize.value = coordinates.size
                    }
            )
            father?.let {
                PersonNode(
                    it, Modifier
                        .offset { IntOffset(0, 0) }
                        .onGloballyPositioned { coordinates ->
                            fatherPosition.value = coordinates.positionInParent().toIntOffset()
                            fatherSize.value = coordinates.size
                        }
                )
                RelationshipEdge(fatherPosition, fatherSize, kidPosition, kidSize)
            }
            mother?.let {
                PersonNode(
                    it, Modifier
                        .offset { IntOffset(700, 0) }
                        .onGloballyPositioned { coordinates ->
                            motherPosition.value = coordinates.positionInParent().toIntOffset()
                            motherSize.value = coordinates.size
                        }
                )
                RelationshipEdge(motherPosition, motherSize, kidPosition, kidSize)
            }
        }
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
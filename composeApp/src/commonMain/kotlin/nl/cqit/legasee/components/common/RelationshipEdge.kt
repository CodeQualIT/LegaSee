package nl.cqit.legasee.components.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toOffset

@Composable
fun RelationshipEdge(
    person1Position: MutableState<IntOffset>,
    person1Size: MutableState<IntSize>,
    person2Position: MutableState<IntOffset>,
    person2Size: MutableState<IntSize>,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val startPosition = person1Position.value.plus(person1Size.value.width / 2, person1Size.value.height)
        val endPosition = person2Position.value.plus(person2Size.value.width / 2, 0)
        val verticalDistance = endPosition.y - startPosition.y
        val corner1Position = startPosition.plus(0, verticalDistance / 2)
        val corner2Position = endPosition.plus(0, -verticalDistance / 2)

        drawLine(
            start = startPosition.toOffset(),
            end = corner1Position.toOffset(),
            color = Color.Gray,
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
        drawLine(
            start = corner1Position.toOffset(),
            end = corner2Position.toOffset(),
            color = Color.Gray,
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
        drawLine(
            start = corner2Position.toOffset(),
            end = endPosition.toOffset(),
            color = Color.Gray,
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
        // TODO: Draw with smooth corners
    }
}

fun IntOffset.plus(x: Int, y: Int): IntOffset {
    return IntOffset(this.x + x, this.y + y)
}
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
        drawLine(
            start = person1Position.value
                .plus(person1Size.value.width / 2, person1Size.value.height)
                .toOffset(),
            end = person2Position.value
                .plus(person2Size.value.width / 2, 0)
                .toOffset(),
            color = Color.Gray,
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
    }
}

fun IntOffset.plus(x: Int, y: Int): IntOffset {
    return IntOffset(this.x + x, this.y + y)
}
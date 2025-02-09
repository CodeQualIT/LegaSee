package nl.cqit.legasee.components.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import kotlin.math.abs

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
        val horizontalDistance = endPosition.x - startPosition.x
        val verticalDistance = endPosition.y - startPosition.y
        val corner1Position = startPosition.plus(0, verticalDistance / 2)
        val corner2Position = endPosition.plus(0, -verticalDistance / 2)
        if (abs(horizontalDistance) < 32) {
            lineWithStraightCorners(startPosition, corner1Position, corner2Position, endPosition)
        } else {
            lineWithRoundedCorners(startPosition, corner1Position, corner2Position, endPosition)
        }
    }
}

private fun DrawScope.lineWithStraightCorners(
    startPosition: IntOffset,
    corner1Position: IntOffset,
    corner2Position: IntOffset,
    endPosition: IntOffset
) {
    drawLine(
        color = Color.Gray,
        start = startPosition.toOffset(),
        end = corner1Position.toOffset(),
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
}

private fun DrawScope.lineWithRoundedCorners(
    startPosition: IntOffset,
    corner1Position: IntOffset,
    corner2Position: IntOffset,
    endPosition: IntOffset
) {
    drawLine(
        color = Color.Gray,
        start = startPosition.toOffset(),
        end = corner1Position.minus(0, 16).toOffset(),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawArc(
        color = Color.Gray,
        startAngle = 180f,
        sweepAngle = -90f,
        useCenter = false,
        topLeft = corner1Position.minus(0, 32).toOffset(),
        size = IntSize(32, 32).toSize(),
        style = Stroke(4f, cap = StrokeCap.Round),
    )
    drawLine(
        start = corner1Position.plus(16, 0).toOffset(),
        end = corner2Position.minus(16, 0).toOffset(),
        color = Color.Gray,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawArc(
        color = Color.Gray,
        startAngle = -90f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = corner2Position.minus(32, 0).toOffset(),
        size = IntSize(32, 32).toSize(),
        style = Stroke(4f, cap = StrokeCap.Round),
    )
    drawLine(
        start = corner2Position.plus(0, 16).toOffset(),
        end = endPosition.toOffset(),
        color = Color.Gray,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
}

fun IntOffset.plus(x: Int, y: Int): IntOffset {
    return IntOffset(this.x + x, this.y + y)
}

fun IntOffset.minus(x: Int, y: Int): IntOffset {
    return IntOffset(this.x - x, this.y - y)
}
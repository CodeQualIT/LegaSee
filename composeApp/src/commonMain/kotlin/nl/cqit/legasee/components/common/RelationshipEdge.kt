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
        val startPosition =
            person1Position.value.offset(person1Size.value.width / 2, person1Size.value.height)
        val endPosition = person2Position.value.offset(person2Size.value.width / 2, 0)
        val horizontalDistance = endPosition.x - startPosition.x
        val verticalDistance = endPosition.y - startPosition.y
        val corner1Position = startPosition.offset(0, verticalDistance / 2)
        val corner2Position = endPosition.offset(0, -verticalDistance / 2)
        if (abs(horizontalDistance) < 32) {
            lineWithStraightCorners(startPosition, corner1Position, corner2Position, endPosition)
        } else {
            lineWithRoundedCorners(
                startPosition,
                corner1Position,
                corner2Position,
                endPosition,
                horizontalDistance
            )
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
    endPosition: IntOffset,
    horizontalDistance: Int
) {
    val sign = if (horizontalDistance > 0) 1 else -1
    drawLine(
        color = Color.Gray,
        start = startPosition.toOffset(),
        end = corner1Position.minusY(16).toOffset(),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawCorner(
        color = Color.Gray,
        cornerDirection = if (horizontalDistance > 0) CornerDirection.BOTTOM_LEFT else CornerDirection.BOTTOM_RIGHT,
        center = corner1Position.offset(16 * sign, -16),
        radius = 16,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawLine(
        start = corner1Position.plusX(16 * sign).toOffset(),
        end = corner2Position.minusX(16 * sign).toOffset(),
        color = Color.Gray,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawCorner(
        color = Color.Gray,
        cornerDirection = if (horizontalDistance > 0) CornerDirection.TOP_RIGHT else CornerDirection.TOP_LEFT,
        center = corner2Position.offset(-16 * sign, 16),
        radius = 16,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
    drawLine(
        start = corner2Position.plusY(16).toOffset(),
        end = endPosition.toOffset(),
        color = Color.Gray,
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
}

private enum class CornerDirection {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
}

private fun DrawScope.drawCorner(
    color: Color,
    cornerDirection: CornerDirection,
    center: IntOffset,
    radius: Int,
    strokeWidth: Float,
    cap: StrokeCap,
) {
    val topLeft = center.offset(-radius, -radius)
    val startAngle = when (cornerDirection) {
        CornerDirection.TOP_LEFT -> 180f
        CornerDirection.TOP_RIGHT -> 270f
        CornerDirection.BOTTOM_LEFT -> 90f
        CornerDirection.BOTTOM_RIGHT -> 0f
    }
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = topLeft.toOffset(),
        size = IntSize(2 * radius, 2 * radius).toSize(),
        style = Stroke(strokeWidth, cap = cap),
    )
}

fun IntOffset.plusX(x: Int): IntOffset {
    return IntOffset(this.x + x, this.y)
}

fun IntOffset.plusY(y: Int): IntOffset {
    return IntOffset(this.x, this.y + y)
}

fun IntOffset.minusX(x: Int): IntOffset {
    return IntOffset(this.x - x, this.y)
}

fun IntOffset.minusY(y: Int): IntOffset {
    return IntOffset(this.x, this.y - y)
}

fun IntOffset.offset(x: Int, y: Int): IntOffset {
    return IntOffset(this.x + x, this.y + y)
}
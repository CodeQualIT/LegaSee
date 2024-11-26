package nl.cqit.legasee

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    Window(
        onCloseRequest = ::exitApplication,
        title = "LegaSee",
        state = windowState
    ) {
        App()
    }
}
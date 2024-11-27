package nl.cqit.legasee

import androidx.compose.ui.test.*
import kotlin.test.Test

class AppTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun contentShouldContainThePlatform() = runComposeUiTest {
        val greeting = Greeting().greet()
        setContent {
            Content()
        }
        onRoot()
            .onChild()
            .assert(hasText("Compose: $greeting", substring = true))
    }
}
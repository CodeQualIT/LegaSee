package nl.cqit.legasee

@JsName("navigator")
external object Navigator {
    val userAgent: String
}

fun getBrowserVersion(): String {
    val userAgent = Navigator.userAgent
    return parseBrowserVersion(userAgent)
}

fun parseBrowserVersion(userAgent: String): String {
    val browserMap = mapOf(
        "Chromium" to "Chromium",                 // Standalone Chromium
        "OPR" to "Opera",                         // Opera (Chromium-based)
        "OPX" to "Opera GX",                     // Opera GX (Chromium-based)
        "Brave" to "Brave",                       // Brave (Chromium-based)
        "Vivaldi" to "Vivaldi",                   // Vivaldi (Chromium-based)
        "SamsungBrowser" to "Samsung Internet",   // Samsung Internet
        "UCBrowser" to "UC Browser",              // UC Browser
        "YaBrowser" to "Yandex Browser"           // Yandex Browser
    )

    // Generic identifiers that are common to many user agents
    val genericIdentifiers = listOf("Mozilla", "AppleWebKit", "Chrome", "Safari", "Gecko", "Firefox")

    // Split and filter out generic identifiers
    val uniqueParts = userAgent.split(" ").filter { part ->
        genericIdentifiers.none { generic -> part.contains(generic, ignoreCase = true) }
    }

    // Extract potential unique identifiers
    val matches = uniqueParts.mapNotNull { part ->
        Regex("""([a-zA-Z]+)/([\d.]+)""").find(part)?.let { match ->
            match.groups[1]?.value to match.groups[2]?.value // Pair of (Browser Name, Version)
        }
    }

    // If we find unique matches, check if they match any specific browser in the map
    if (matches.isNotEmpty()) {
        val (browserName, version) = matches.first() // Could add prioritization logic here if needed

        // If the browser name is in the map, use the mapped name
        val fullBrowserName = browserMap[browserName] ?: browserName
        return "Browser: $fullBrowserName. Version: $version"
    }

    // Fallback logic for common browsers (like Chrome, Safari, Firefox)
    return when {
        userAgent.contains("Chrome", ignoreCase = true) -> {
            // For Chrome, match its version
            val match = Regex("Chrome/([0-9.]+)").find(userAgent)
            "Browser: Google Chrome. Version: ${match?.groups?.get(1)?.value ?: "Unknown"}"
        }

        userAgent.contains("Safari", ignoreCase = true) -> {
            // For Safari, match its version
            val match = Regex("Version/([0-9.]+)").find(userAgent)
            val webkitMatch = Regex("Safari/([0-9.]+)").find(userAgent)
            "Browser: Apple Safari. Version: ${match?.groups?.get(1)?.value ?: "Unknown"} " +
                    "(WebKit: ${webkitMatch?.groups?.get(1)?.value ?: "Unknown"})"
        }

        userAgent.contains("Firefox", ignoreCase = true) -> {
            // For Firefox, match its version
            val match = Regex("Firefox/([0-9.]+)").find(userAgent)
            "Browser: Mozilla Firefox. Version: ${match?.groups?.get(1)?.value ?: "Unknown"}"
        }

        else -> "Unknown Browser"
    }
}

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm. ${getBrowserVersion()}"
}

actual fun getPlatform(): Platform = WasmPlatform()
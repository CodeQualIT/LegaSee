package nl.cqit.legasee

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

actual fun encodeUrl(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}
package nl.cqit.legasee

import java.net.URLEncoder

actual fun encodeUrl(value: String): String {
    return URLEncoder.encode(value, "UTF_8")
}
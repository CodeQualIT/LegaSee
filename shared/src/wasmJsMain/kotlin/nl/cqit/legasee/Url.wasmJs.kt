package nl.cqit.legasee

@JsName("encodeURIComponent")
external fun encodeURIComponent(uriComponent: String): String

actual fun encodeUrl(value: String): String {
    return encodeURIComponent(value)
}
package nl.cqit.legasee

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package nl.cqit.legasee.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

object AvatarGenerator {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    @Serializable
    data class ImageGeneratorResult(
        val generated: Boolean,
        val scheme: String,
        val src: String,
        val name: String,
    )

    enum class Gender {
        MALE,
        FEMALE
    }

    enum class AgeGroup {
        ADOLESCENT,
        STUDENT,
        YOUNG_ADULT,
        ADULT,
        SENIOR;

        val age: String
            get() = when (this) {
                ADOLESCENT -> "12-18"
                STUDENT -> "19-25"
                YOUNG_ADULT -> "26-35"
                ADULT -> "35-50"
                SENIOR -> "50"
            }
    }

    suspend fun getRandomAvatarUrl(gender: Gender, ageGroup: AgeGroup = AgeGroup.ADULT): String {
        val result: ImageGeneratorResult =
            client.get("https://this-person-does-not-exist.com/new?time=1738879877894&gender=${gender.name.lowercase()}&age=${ageGroup.age}&etnic=all")
                .body()
        return result.src
    }
}
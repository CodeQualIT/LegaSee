package nl.cqit.legasee.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import nl.cqit.legasee.AncestorTree

@Composable
fun PersonNode(person: AncestorTree.Person, modifier: Modifier = Modifier) {
    val imageSize = 60
    Box(
        modifier = Modifier
            .padding(8.dp)
            .then(modifier)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            person.personalInfo.imageURI?.let {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalPlatformContext.current)
                        .data(it)
                        .size(Size(imageSize, imageSize))
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = person.personalInfo.fullName + "\'s portrait",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(imageSize.dp)
                )
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = person.personalInfo.fullName,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = getBirthAndDeath(person),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

private fun getBirthAndDeath(person: AncestorTree.Person): String {
    val birth = getDateAndPlace(
        person.personalInfo.dateOfBirth,
        person.personalInfo.placeOfBirth
    )
    val death = person.personalInfo.dateOfDeath
        ?.takeIf(String::isNotBlank)
        ?.let {
            getDateAndPlace(
                it,
                person.personalInfo.placeOfDeath
            )
        }
        ?: "present"
    return "$birth - $death"
}

private fun getDateAndPlace(date: String, place: String?): String {
    val formattedPlace = place
        ?.takeIf(String::isNotBlank)
        ?.let { " ($it)" }
        ?: ""
    return date + formattedPlace
}

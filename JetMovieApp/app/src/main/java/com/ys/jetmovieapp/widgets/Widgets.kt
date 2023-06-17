package com.ys.jetmovieapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ys.jetmovieapp.model.Movie
import com.ys.jetmovieapp.model.getMovies

@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(4.dp)
            .clickable {
                onItemClick(movie.id)
            },
        shape = RoundedCornerShape(CornerSize(16.dp)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                shadowElevation = 4.dp
            ) {
                AsyncImage(
                    model = movie.images.first(),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = movie.title
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = "Director: ${movie.director}"
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = movie.year
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieRowPreview() {
    MovieRow(movie = getMovies().first())
}
package com.ys.macdonaldclone.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ys.macdonaldclone.R
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme

@Composable
fun Icon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun Icon(
    imagePainter: Painter,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = imagePainter,
        contentDescription = null,
        modifier = modifier
    )
}

@Preview
@Composable
fun IconPreview() {
    MacdonaldCloneTheme {
        Icon(painterResource(id = R.drawable.category_burgers))
    }
}

@Preview
@Composable
fun IconVectorPreview() {
    MacdonaldCloneTheme {
        Icon(imageVector = Icons.Default.Search)
    }
}
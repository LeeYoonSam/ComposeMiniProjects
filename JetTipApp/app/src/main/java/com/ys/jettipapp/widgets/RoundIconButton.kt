package com.ys.jettipapp.widgets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val IconButtonSizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    cardColors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.onPrimary
    ),
    cardElevation: CardElevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }
            .then(
                IconButtonSizeModifier
            ),
        shape = CircleShape,
        colors = cardColors,
        elevation = cardElevation
    ) {
        Icon(
            modifier = IconButtonSizeModifier,
            imageVector = imageVector,
            contentDescription = "plus or minus icon",
            tint = tint
        )
    }
}

@Preview
@Composable
fun RoundIconButtonPreview() {
    RoundIconButton(
        imageVector = Icons.Default.Remove,
        onClick = { Log.d("Icon", "BillForm: Removed") }
    )
}
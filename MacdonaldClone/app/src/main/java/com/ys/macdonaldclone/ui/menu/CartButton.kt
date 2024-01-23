package com.ys.macdonaldclone.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ys.macdonaldclone.ui.components.Icon
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme

@Composable
fun CartButton(
    quantity: Int,
    price: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = if (quantity == 1) {
                        "$quantity item"
                    } else {
                        "$quantity items"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$%.2f".format(price),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "VIEW CART",
                style = MaterialTheme.typography.bodyMedium,
            )
            Icon(imageVector = Icons.Rounded.KeyboardArrowRight)
        }
    }
}

@Preview("MenuCartButton")
@Composable
private fun MenuCartButtonPreview() {
    MacdonaldCloneTheme {
        CartButton(
            quantity = 3,
            price = 0.0,
            onClick = {}
        )
    }
}

@Preview("MenuCartButton • Dark")
@Composable
private fun MenuCartButtonDarkPreview() {
    MacdonaldCloneTheme(darkTheme = true) {
        CartButton(
            quantity = 3,
            price = 0.0,
            onClick = {}
        )
    }
}
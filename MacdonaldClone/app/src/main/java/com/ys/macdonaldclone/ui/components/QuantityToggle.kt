package com.ys.macdonaldclone.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ys.macdonaldclone.ui.theme.MacdonaldCloneTheme

private const val QuantityToggleLabel = "QuantityToggleLabel"
private enum class QuantityToggleState { Zero, NonZero }

@Composable
fun QuantityToggle(
    quantity: Int,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,
) {
    val transition = updateTransition(targetState = if (quantity == 0) QuantityToggleState.Zero else QuantityToggleState.NonZero,
        label = QuantityToggleLabel
    )

    val backgroundColor by transition.animateColor(label = QuantityToggleLabel) { state ->
        when (state) {
            QuantityToggleState.Zero -> MaterialTheme.colorScheme.background
            QuantityToggleState.NonZero -> MaterialTheme.colorScheme.secondary
        }
    }

    val contentColor by transition.animateColor(label = QuantityToggleLabel) { state ->
        when (state) {
            QuantityToggleState.Zero -> MaterialTheme.colorScheme.secondary
            QuantityToggleState.NonZero -> LocalContentColor.current
        }
    }

    val iconSize by transition.animateDp(label = QuantityToggleLabel) { state ->
        when (state) {
            QuantityToggleState.Zero -> 0.dp
            QuantityToggleState.NonZero -> 18.dp
        }
    }

    Surface(
        modifier = Modifier
            .width(90.dp)
            .aspectRatio(2.75f),
        shape = MaterialTheme.shapes.small,
        color = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = if (quantity == 0) onIncrementQuantity else onDecrementQuantity),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = if (quantity == 0) "ADD" else "ADDED",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}

@Preview("QuantityToggle • Zero")
@Composable
private fun QuantityToggleZeroPreview() {
    MacdonaldCloneTheme {
        QuantityToggle(
            quantity = 0,
            onIncrementQuantity = {},
            onDecrementQuantity = {},
        )
    }
}

@Preview("QuantityToggle • NonZero")
@Composable
private fun QuantityToggleNonZeroPreview() {
    MacdonaldCloneTheme {
        QuantityToggle(
            quantity = 1,
            onIncrementQuantity = {},
            onDecrementQuantity = {},
        )
    }
}

@Preview("QuantityToggle • Zero • Dark")
@Composable
private fun QuantityToggleZeroDarkPreview() {
    MacdonaldCloneTheme(darkTheme = true) {
        QuantityToggle(
            quantity = 0,
            onIncrementQuantity = {},
            onDecrementQuantity = {},
        )
    }
}

@Preview("QuantityToggle • NonZero • Dark")
@Composable
private fun QuantityToggleNonZeroDarkPreview() {
    MacdonaldCloneTheme(darkTheme = true) {
        QuantityToggle(
            quantity = 1,
            onIncrementQuantity = {},
            onDecrementQuantity = {},
        )
    }
}
package com.assignment.craftsilicontest.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentations.components.TextComponent

@Composable
public fun ButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, textColor: Color, fontSize: Int, style: TextStyle, onClick: ()-> Unit) {
    Button(
        onClick = {
            onClick()
        },
        border = borderStroke,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ){
        TextComponent(
            text = buttonText, fontSize = fontSize, textStyle = style, textColor = textColor, textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold)
    }
}

@Composable
public fun IconButtonComponent(modifier: Modifier, buttonText: String, borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, textColor: Color, fontSize: Int, style: TextStyle, iconRes: Int, iconSize: Int = 28, colorFilter: ColorFilter? = null, onClick: (() -> Unit)? = null) {
    val rowModifier = Modifier
        .fillMaxWidth().fillMaxHeight()

    Button(
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        border = borderStroke,
        shape = shape,
        modifier = modifier,
        colors = colors
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {

            val iconModifier = Modifier
                .size(iconSize.dp)

            val iconBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.10f)

            val textModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(end = 15.dp)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = iconBoxModifier
            ) {
                ImageComponent(imageModifier = iconModifier, imageRes = iconRes, colorFilter = colorFilter)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = textModifier
            ) {
                TextComponent(
                    text = buttonText,
                    fontSize = fontSize,
                    textStyle = style,
                    textColor = textColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        }
    }
}

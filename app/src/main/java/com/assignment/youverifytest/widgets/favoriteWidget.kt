package com.assignment.youverifytest.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.ImageComponent
import com.assignment.youverifytest.R

@Composable
fun FavoriteProductWidget(iconRes: Int = R.drawable.fav_icon, onFavClicked:(Boolean) -> Unit) {
    val imageBgColor = Color.White
    val imageTintColor = Color.Black
    Box(
        Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(color = imageBgColor),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .clickable {
                if (iconRes == R.drawable.fav_icon) {
                    onFavClicked(true)
                }
                else{
                    onFavClicked(false)
                }
            }
            .size(24.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = imageTintColor))
    }
}
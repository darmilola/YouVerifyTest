package com.assignment.craftsilicontest.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
public fun AsyncImageComponent(imageModifier: Modifier, imageRes: String, colorFilter: ColorFilter? = null, contentScale: ContentScale = ContentScale.Crop) {
        AsyncImage(
            model = imageRes,
            contentDescription = null,
            contentScale = contentScale,
            modifier = imageModifier,
            colorFilter = colorFilter
        )
}

@Composable
public fun ImageComponent(imageModifier: Modifier, imageRes: Int, colorFilter: ColorFilter? = null, contentScale: ContentScale = ContentScale.Crop) {
      Image(
            painter = painterResource(imageRes),
            contentDescription = "Image Component",
            contentScale = contentScale,
            modifier = imageModifier,
            colorFilter = colorFilter
        )
}
package com.assignment.youverifytest.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.assignment.craftsilicontest.component.ImageComponent
import com.assignment.youverifytest.R

@Composable
fun PageBackNavWidget(onBackPressed: (() -> Unit)) {
    val boxModifier = Modifier
            .background(color = Color.Transparent)
            .clickable {
              onBackPressed()
              }
            .size(45.dp)
    Box(modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier.size(30.dp), imageRes = R.drawable.back_arrow, colorFilter = ColorFilter.tint(color = Color.Black))
        }
}
package com.assignment.youverifytest.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.ToggleButton
import com.assignment.youverifytest.domain.Enums.DeliveryMethodEnum
import com.assignment.youverifytest.viewmodels.CartViewModel
import presentations.components.TextComponent

@Composable
fun ProductDeliveryAddressWidget(cartViewModel: CartViewModel,
                                 onPickupSelectedListener:() -> Unit, onMobileSelectedListener:() -> Unit){

    val deliveryMethod =  cartViewModel.deliveryMethod.collectAsState()
    val isRightSelection = deliveryMethod.value == DeliveryMethodEnum.MOBILE.toPath()
    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 15.dp, end = 10.dp)
        .wrapContentHeight()
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = "Delivery Location",
            fontSize = 18,
            textStyle = MaterialTheme.typography.bodyMedium,
            textColor = Color.Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            ToggleButton(shape = RoundedCornerShape(5.dp), isRightSelection = isRightSelection, onLeftClicked = {
                onPickupSelectedListener()
            }, onRightClicked = {
                onMobileSelectedListener()
            },
                rightText = "Home Delivery", leftText = "Pickup")
        }

    }

}
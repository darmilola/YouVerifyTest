package com.assignment.youverifytest.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.ButtonComponent
import com.assignment.youverifytest.domain.Enums.DeliveryMethodEnum
import com.assignment.youverifytest.utils.formatNumber
import com.assignment.youverifytest.viewmodels.CartViewModel
import com.assignment.youverifytest.viewmodels.MainViewModel
import presentations.components.TextComponent

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CheckOutSummaryWidget(cartViewModel: CartViewModel, mainViewModel: MainViewModel, onCardCheckOutStarted:() -> Unit) {

    val deliveryMethod = cartViewModel.deliveryMethod.collectAsState()
    val subtotal = cartViewModel.subtotal.collectAsState()
    val total = cartViewModel.total.collectAsState()
    val deliveryFee = if (cartViewModel.deliveryFee.value == 0L) "Free" else formatNumber(cartViewModel.deliveryFee.value)

    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
        .height(200.dp)
        .fillMaxWidth()

    val buttonStyle = Modifier
        .padding(bottom = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(50.dp)

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = columnModifier) {
         Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
             TextComponent(
                 text = "Sub-total",
                 fontSize = 18,
                 textStyle = MaterialTheme.typography.titleSmall,
                 textColor = Color.Gray,
                 textAlign = TextAlign.Right,
                 fontWeight = FontWeight.Bold,
                 lineHeight = 20,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 textModifier = Modifier.fillMaxWidth(0.50f)
             )
             TextComponent(
                 text = formatNumber(subtotal.value),
                 fontSize = 18,
                 textStyle = MaterialTheme.typography.titleSmall,
                 textColor = Color.Gray,
                 textAlign = TextAlign.Right,
                 fontWeight = FontWeight.Bold,
                 lineHeight = 20,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis,
                 textModifier = Modifier.fillMaxWidth()
             )
         }

        if (deliveryMethod.value == DeliveryMethodEnum.MOBILE.toPath()) {
            Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
                TextComponent(
                    text = "Delivery Fee",
                    fontSize = 18,
                    textStyle = MaterialTheme.typography.titleSmall,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth(0.50f)
                )
                TextComponent(
                    text = "$deliveryFee",
                    fontSize = 18,
                    textStyle = MaterialTheme.typography.titleSmall,
                    textColor = Color.Gray,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textModifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
            TextComponent(
                text = "Total",
                fontSize = 18,
                textStyle = MaterialTheme.typography.titleSmall,
                textColor = Color.Black,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth(0.50f)
            )
            TextComponent(
                text = formatNumber(total.value),
                fontSize = 18,
                textStyle = MaterialTheme.typography.titleSmall,
                textColor = Color.Black,
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                lineHeight = 20,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth()
            )
        }

        ButtonComponent(modifier = buttonStyle, buttonText = "Proceed to Checkout", borderStroke = BorderStroke(1.dp, Color.Black), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color.Black, style = TextStyle()){
            onCardCheckOutStarted()
        }

    }

}
package presentations.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType


@Composable
public fun TextComponent(textModifier: Modifier, text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight?, fontFamily: FontFamily? = null, lineHeight: Int = 10,maxLines: Int = 20, overflow: TextOverflow = TextOverflow.Clip, letterSpacing: Int = 0, textDecoration: TextDecoration? = null) {
    Text(text, fontSize = fontSize.sp, fontFamily = fontFamily, modifier = textModifier, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = lineHeight.sp, overflow = overflow, maxLines = maxLines, letterSpacing = letterSpacing.sp, textDecoration = textDecoration)
}

@Composable
fun TextComponent(text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight, fontFamily: FontFamily? = null,lineHeight: Int = 10, maxLines: Int = 10, overflow: TextOverflow = TextOverflow.Clip,letterSpacing: TextUnit = TextUnit.Unspecified) {
    Text(text, fontSize = fontSize.sp, fontFamily = fontFamily, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = lineHeight.sp, maxLines = maxLines, overflow = overflow, letterSpacing = letterSpacing)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(text: TextFieldValue, readOnly: Boolean = false, modifier: Modifier, textStyle: TextStyle = LocalTextStyle.current,onValueChange: (TextFieldValue) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), color: TextFieldColors = TextFieldDefaults.textFieldColors(
    disabledTextColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
), isSingleLine: Boolean = false, trailingIcon: @Composable (() -> Unit)? = null, isReadOnly: Boolean = false) {

    var mText by remember { mutableStateOf(TextFieldValue(text.text)) }

    TextField(value = mText, modifier = modifier, textStyle = textStyle, onValueChange = { newValue -> mText = newValue }, keyboardOptions = keyboardOptions, colors = color, singleLine = isSingleLine, readOnly = isReadOnly, placeholder = {
        PlaceholderTextComponent("Input Phone Number")
    })
}

@Composable
fun PlaceholderTextComponent(placeholderTile: String, textColor: Color = Color.LightGray, textSize: Float = 18f) {
    val textStyle = TextStyle(
        fontSize = TextUnit(textSize, TextUnitType.Sp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Normal
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
        TextComponent(
            text = placeholderTile,
            fontSize = textSize.toInt(),
            textStyle = textStyle,
            textColor = textColor,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            textModifier = Modifier.wrapContentSize(),
            letterSpacing = 0
        )
    }
}


@Composable
fun MultilinePlaceholderTextComponent(placeholderTile: String, textColor: Color = Color.LightGray, textSize: Float = 18f) {
    val textStyle = TextStyle(
        fontSize = TextUnit(textSize, TextUnitType.Sp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Normal
    )

    TextComponent(
        text = placeholderTile,
        fontSize = textSize.toInt(),
        textStyle = textStyle,
        textColor = textColor,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Normal,
        textModifier = Modifier.wrapContentSize(),
        letterSpacing = 0
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(text: String, readOnly: Boolean = false, modifier: Modifier, textStyle: TextStyle = LocalTextStyle.current, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), color: TextFieldColors = TextFieldDefaults.textFieldColors(
    disabledTextColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
), isSingleLine: Boolean = false, isPasswordField: Boolean = false, isReadOnly: Boolean = false, placeholderText: String, onFocusChange: (Boolean) -> Unit, placeholderTextSize: Float = 18f, maxLines: Int = 1) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    if (isFocused.value){
        onFocusChange(true)
    }else{
        onFocusChange(false)
    }

    val visualTransformation: VisualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None

    BasicTextField(value = text, modifier = modifier, textStyle = textStyle, readOnly = isReadOnly, singleLine = isSingleLine, keyboardOptions = keyboardOptions, visualTransformation = visualTransformation, onValueChange = onValueChange, interactionSource = interactionSource, maxLines = maxLines, decorationBox = { innerTextField ->
        Row(modifier = Modifier.fillMaxWidth()) {
            if (text.trim().isEmpty()) {
                PlaceholderTextComponent(placeholderText, textColor = Color.Gray, textSize = placeholderTextSize)
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            innerTextField()
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultilineTextFieldComponent(text: String, readOnly: Boolean = false, modifier: Modifier, textStyle: TextStyle = LocalTextStyle.current, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), color: TextFieldColors = TextFieldDefaults.textFieldColors(
    disabledTextColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
), isSingleLine: Boolean = false, isPasswordField: Boolean = false, isReadOnly: Boolean = false, placeholderText: String, onFocusChange: (Boolean) -> Unit, placeholderTextSize: Float = 18f, maxLines: Int = 1) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()
    if (isFocused.value){
        onFocusChange(true)
    }else{
        onFocusChange(false)
    }

    val visualTransformation: VisualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None

    BasicTextField(value = text, modifier = modifier, textStyle = textStyle, readOnly = isReadOnly, singleLine = isSingleLine, keyboardOptions = keyboardOptions, visualTransformation = visualTransformation, onValueChange = onValueChange, interactionSource = interactionSource, maxLines = maxLines, decorationBox = { innerTextField ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            if (text.isEmpty()) {
                MultilinePlaceholderTextComponent(placeholderText, textColor = Color.Gray, textSize = placeholderTextSize)
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            innerTextField()
        }
    })
}





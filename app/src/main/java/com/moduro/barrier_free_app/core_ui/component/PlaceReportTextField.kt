package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text2

@Composable
fun PlaceReportTextField(
    value : String,
    onValueChange : (String) -> Unit,
    placeHolder : String
) {

    val typography = LocalbarrierFreeTypographyProvider.current


    TextField(
        modifier =  Modifier.fillMaxWidth(),
        value = value ,
        onValueChange = onValueChange,

        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = placeHolder,
                color = Text2,
                style = typography.H5_SB_5
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Text2,
            unfocusedTextColor = Text2,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}

@Preview
@Composable
fun TextFieldPreview(){

    PlaceReportTextField(
        "",
        {},
        "기본 주소"
    )
}

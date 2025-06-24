package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text2
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4

@Composable
fun StartTextField(
    label: String,
    hint: String,
    text: String,
    onValueChange: (String) -> Unit,
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Background1)
            .height(70.dp)
    ) {
        Text(
            text = label,
            style = typography.H9_M,
            color = Text3,

            modifier = Modifier.padding(start = 13.dp, top = 11.dp)
        )
        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(hint, style = typography.H5_SB_10, color = Text2)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background1,
                unfocusedContainerColor = Background1,
                disabledContainerColor = Background1,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent
            ),
            textStyle = typography.H5_SB_5.copy(color = Text4)
        )
    }

}

@Composable
@Preview
fun StartTextFieldPreview() {
    StartTextField(
        label = "이메일 주소",
        hint = "아이디 또는 이메일 주소",
        text = "",
        onValueChange = {}
    )
}
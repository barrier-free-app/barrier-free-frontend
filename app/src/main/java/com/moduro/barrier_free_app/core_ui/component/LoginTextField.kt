package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Warning

@Composable
fun LoginTextField(
    label: String,
    hint: String,
    text: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String? = null,
    isPassword: Boolean = false
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(color = Background1)
    ) {
        Text(
            text = label,
            style = typography.H9_M,
            color = if (isError) Warning else Text4,
            modifier = Modifier.padding(start = 14.dp, top = 11.dp)
        )
        Box(
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                .background(Background1)
        ) {
            TextField(
                value = text,
                onValueChange = onValueChange,
                placeholder = {
                    Text(hint, style = typography.H5_SB_10, color = Text3)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background1,
                    unfocusedContainerColor = Background1,
                    disabledContainerColor = Background1,
                    focusedIndicatorColor = if (isError) Warning else Transparent,
                    unfocusedIndicatorColor = if (isError) Warning else Transparent,
                    disabledIndicatorColor = Transparent
                ),
                textStyle = typography.H5_SB_5.copy(color = Text4)
            )
        }
    }

}

@Composable
@Preview
fun LoginTextFieldPreview() {
    LoginTextField(
        label = "이메일 주소",
        hint = "아이디 또는 이메일 주소",
        text = "",
        onValueChange = {},
        isError = true
    )
}
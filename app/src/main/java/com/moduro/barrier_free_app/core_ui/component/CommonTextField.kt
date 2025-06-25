package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text2
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5


@Composable
fun ProfilePasswordField(
    hint: String,
    iconRes: Int,
    isVisible: Boolean,
    onVisibilityToggle: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = {
            Text(text = hint, style = typography.H6_M, color = Text2)
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onVisibilityToggle) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Toggle visibility",
                    tint = Text5
                )
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Background1,
            unfocusedContainerColor = Background1,
            disabledContainerColor = Background1,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
        ),
        textStyle = typography.H6_M.copy(color = Text4)
    )
}

@Composable
fun ProfileNicknameField(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    onValueChange: (String) -> Unit,
    onCheckDuplicateClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(hint, style = typography.H6_M, color = Text2)
        },
        trailingIcon = {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Button1,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .height(30.dp)
                    .wrapContentWidth()
                    .clickable { onCheckDuplicateClick() }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "중복 확인",
                        style = typography.H10_M
                    )
                }
            }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Background1,
            unfocusedContainerColor = Background1,
            disabledContainerColor = Background1,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
        ),
        textStyle = typography.H6_M.copy(color = Text4)
    )
}

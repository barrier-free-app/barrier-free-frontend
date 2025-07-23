package com.moduro.barrier_free_app.presentation.map.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenTop(
    search: String,
    onSearchChange: (String) -> Unit,
    onSearchClick : () -> Unit,
    onFilterClick : () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column (
        modifier = Modifier.background(Background2).padding(horizontal = 24.dp).padding(bottom = 15.dp)
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(painter = painterResource(R.drawable.ic_moduro), contentDescription = "로고")
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.height(22.dp).width(22.dp).clickable { onFilterClick() },

                painter = painterResource(R.drawable.map_filter), contentDescription = "필터")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = search,
            onValueChange = onSearchChange,
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                unfocusedTextColor = Text2,
                focusedTextColor = Text2,
                focusedPlaceholderColor = Text2,
                unfocusedPlaceholderColor = Text2,
                containerColor = Color.White,
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(R.drawable.map_search), // ← 돋보기 아이콘
                        contentDescription = "검색",
                        tint = Color.Gray
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun MapScreenTopPreview() {

    MapScreenTop(
        "원하는 장소를 검색하세요.",
        {},
        {},
        {}
    )

}
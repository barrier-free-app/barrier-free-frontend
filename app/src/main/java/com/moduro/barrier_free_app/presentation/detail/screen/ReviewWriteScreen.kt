package com.moduro.barrier_free_app.presentation.detail.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5

@Composable
fun ReviewWriteScreen(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val systemUiController = rememberSystemUiController()

    val placeDetail by viewModel.placeDetail.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val reviewText by viewModel.reviewText.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setSelectedImageUri(uri)
    }

    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }

    if (placeDetail == null) {
        Text(text = "로딩중...")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonTopBar(title = "리뷰 작성하기", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = MainYellow
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("${placeDetail!!.name},", style = typography.H6_M, color = Color.Black)
                Text("어떻게 생각하시나요?", style = typography.H6_M, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..5).forEach { i ->
                        val icon =
                            if (i <= rating) R.drawable.ic_star_selected else R.drawable.ic_star_unselected
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { viewModel.setRating(i) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row {
                Text("사진 첨부하기", style = typography.H5_SB_5, color = Text4)
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    "(최대 1장)",
                    style = typography.H7_M_5,
                    color = Text4,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Button1)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageUri),
                        contentDescription = "선택된 사진",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "사진 추가",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text("상세 리뷰 작성하기", style = typography.H5_SB_5, color = Text4)
            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                shape = RoundedCornerShape(10.dp),
                value = reviewText,
                onValueChange = { viewModel.setReviewText(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(color = Background1),
                placeholder = {
                    Text(
                        "•       욕설,  비방은  자제해주세요.\n•       최대   nnn자까지  작성  가능합니다.",
                        style = typography.H6_M,
                        color = Text3
                    )
                },
                maxLines = 5,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background1,
                    unfocusedContainerColor = Background1,
                    disabledContainerColor = Background1,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = onSubmitClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Text5,
                contentColor = Background1
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(54.dp)
        ) {
            Text("작성 완료", style = typography.H4_SB)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
@Composable
fun ReviewWriteScreenPreview() {
    ReviewWriteScreen(
        onBackClick = {},
        onSubmitClick = {}
    )
}


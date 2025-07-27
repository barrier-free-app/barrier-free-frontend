package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.PlaceReportTextField
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5
import com.moduro.barrier_free_app.presentation.mypage.navigation.MypageNavigator

@Composable
fun PlaceReportRoute(
    navigator: MypageNavigator,
) {

    val mypageViewModel: MypageViewModel = hiltViewModel()

    PlaceReportScreen(
        mypageViewModel = mypageViewModel,
        onBackClick = { navigator.navigateBack() },
        onReportClick = { navigator.navigateBack() }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceReportScreen(
    mypageViewModel : MypageViewModel,
    onBackClick: () -> Unit,
    onReportClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val systemUiController = rememberSystemUiController()

    var placeName by remember { mutableStateOf("") }

    var placeLocation1 by remember { mutableStateOf("") }
    var placeLocation2 by remember { mutableStateOf("") }

    var selectedMultiFilter by remember { mutableStateOf<List<String>>(listOf("전체")) }
    var selectedSingleFilter by remember { mutableStateOf<List<String>>(emptyList()) }

    var placeDescription by remember { mutableStateOf("") }
    var placeHomepageURL by remember { mutableStateOf("") }
    var placeBusinessHours by remember { mutableStateOf("") }
    var placePhoneNumber by remember { mutableStateOf("") }


    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background2)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
            .padding(horizontal = 16.dp),
    ) {
        CommonTopBar(title = "장소 제보하기", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .height(208.dp)
                .fillMaxWidth()
                .padding(bottom = 23.dp)
                .background(color = MainYellow, shape = RoundedCornerShape(10.dp))
        ) {
            Column (
                modifier = Modifier
                .padding(vertical = 20.dp)
                .padding(horizontal = 20.dp)
            ){
                Text("모두로에 없는 장소를 제보해주세요!", style = typography.H5_SB_5)

                Spacer(modifier = Modifier.height(2.dp))

                Text("다른 사람들과 함께 공유해 보아요", style = typography.H5_SB_5)

                Spacer(modifier = Modifier.weight(1f))

                TextField(
                    modifier =  Modifier.fillMaxWidth(),
                    value = placeName,
                    onValueChange = { placeName = it },
                    placeholder = {
                        Text(
                            text = "장소명을 입력해주세요",
                            color = Text3,
                            style = typography.H5_SB_5
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Text3,
                        unfocusedIndicatorColor = Text3,
                    ),
                )

            }

        }

        Spacer(modifier = Modifier.height(25.dp))

        Text("장소의 위치를 알려주세요.", style = typography.H5_SB_5, color = Text4 )

        Spacer(modifier = Modifier.height(21.dp))

        PlaceReportTextField(value = placeLocation1, onValueChange = {placeLocation1 = it} , placeHolder = "기본 주소")

        Spacer(modifier = Modifier.height(10.dp))

        PlaceReportTextField(value = placeLocation2, onValueChange = {placeLocation2 = it}, placeHolder = "상세 주소를 입력해주세요." )

        Spacer(modifier = Modifier.height(42.dp))

        Text("편의 시설 정보를 선택해주세요.", style = typography.H5_SB_5, color = Text4)

        Spacer(modifier = Modifier.height(21.dp))

        PlaceReportFilterContent(
            valueType = 1,
            onValueChange = { selectedMultiFilter = it }
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text("대체 이미지 타입을 선택해주세요.", style = typography.H5_SB_5, color = Text4)

        Spacer(modifier = Modifier.height(21.dp))

        PlaceReportFilterContent(
            valueType = 2,
            onValueChange = { selectedSingleFilter = it }
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text("장소에 대한 한 줄 정보를 입력해주세요.", style = typography.H5_SB_5, color = Text4)

        Spacer(modifier = Modifier.height(21.dp))

        PlaceReportTextField(value = placeDescription, onValueChange = {placeDescription = it} , placeHolder = "예) 배리어프리 시설이 있는 호텔")


        Spacer(modifier = Modifier.height(42.dp))


        Text("장소에 대한 추가 정보를 입력해주세요.", style = typography.H5_SB_5, color = Text4)

        Spacer(modifier = Modifier.height(21.dp))

        PlaceReportTextField(value = placeHomepageURL, onValueChange = {placeHomepageURL = it} , placeHolder = "홈페이지 URL")

        Spacer(modifier = Modifier.height(10.dp))

        PlaceReportTextField(value = placeBusinessHours, onValueChange = {placeBusinessHours = it} , placeHolder = "영업시간")

        Spacer(modifier = Modifier.height(10.dp))


        PlaceReportTextField(value = placePhoneNumber, onValueChange = {placePhoneNumber = it} , placeHolder = "장소 전화번호")

        Spacer(modifier = Modifier.height(55.dp))

        Button(
            onClick = onReportClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Text5,
                contentColor = Background1
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("작성 완료", style = typography.H4_SB)
        }
    }
}

@Preview
@Composable
fun PlaceReportScreenPreview() {

    PlaceReportScreen(
        viewModel(),
        {}, {}
    )
}

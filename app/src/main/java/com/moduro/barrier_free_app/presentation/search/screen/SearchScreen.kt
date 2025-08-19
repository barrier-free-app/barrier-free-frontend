package com.moduro.barrier_free_app.presentation.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moduro.barrier_free_app.core_ui.component.HomePlaceBox
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.presentation.home.screen.MultiSelectChip
import com.moduro.barrier_free_app.presentation.search.navigation.SearchNavigator

@Composable
fun SearchRoute(
    searchValue : String?,
    navigator: SearchNavigator,
) {
    val searchViewModel: SearchViewModel = hiltViewModel()

    SearchScreen(
        searchValue  = searchValue,
        searchViewModel = searchViewModel,
        onBackClick = {navigator.navigateBack()},
        onSearchClick = {},
        onDetailClick = { placeId ->
            navigator.navigateToPlaceDetail(placeId)
        }
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    searchValue : String?,
    searchViewModel: SearchViewModel,
    onBackClick : () -> Unit,
    onSearchClick: () -> Unit,
    onDetailClick: (Long) -> Unit
) {

    var search by remember { mutableStateOf("") }

    val placeList by searchViewModel.placeList.observeAsState(emptyList())
    val isLoading by searchViewModel.isLoading.observeAsState(false)
    val hasNext by searchViewModel.hasNext.observeAsState(true)

    val multiOptions = listOf(0, 1, 2, 3, 4, 5)
    val multiSelected = remember { mutableStateListOf<Int>() }

    LaunchedEffect(Unit) {
        searchViewModel.resetSearch()
        searchViewModel.fetchPlaces(
            keyword = searchValue,
            facilities = null // 처음엔 전체로 검색
        )
        multiSelected.clear()
        multiSelected.add(0)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize().background(Background2)
            .padding(top = 20.dp).padding(horizontal = 16.dp)
    ) {
        item {
            SearchScreenTop(
                search = search,
                onBackClick = { onBackClick() },
                onSearchChange = { search = it },
                onSearchClick = {
                    searchViewModel.resetSearch()
                    searchViewModel.fetchPlaces(
                        keyword = search,
                        facilities = if (multiSelected.contains(0)) null else multiSelected.toList()
                    )
                },
            )

            FlowRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                multiOptions.forEach { option ->
                    MultiSelectChip(
                        type = option,
                        selected = multiSelected.contains(option),
                        onClick = {
                            if (option == 0) {
                                multiSelected.clear()
                                multiSelected.add(0)
                            } else {
                                if (multiSelected.contains(option)) {
                                    multiSelected.remove(option)
                                } else {
                                    multiSelected.add(option)
                                }
                                // '전체' 선택 해제
                                if (multiSelected.contains(0)) {
                                    multiSelected.remove(0)
                                }
                                // 아무것도 없으면 다시 전체
                                if (multiSelected.isEmpty()) {
                                    multiSelected.add(0)
                                }
                            }

                            searchViewModel.resetSearch()
                            searchViewModel.fetchPlaces(
                                keyword = search,
                                facilities = if (multiSelected.contains(0)) null else multiSelected.toList()
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        itemsIndexed(placeList) { index, place ->
            HomePlaceBox(
                place = HomePlaceEntity(
                    placeId = place.placeId,
                    placeType = place.placeType,
                    name = place.name,
                    region = place.region,
                    facility = place.facilities,
                    description = place.description,
                    imageType = place.imageType
                ),
                onClick = { placeId -> onDetailClick(placeId) }
            )
            Spacer(modifier = Modifier.height(9.dp))

            // 페이징 - 리스트 끝에서 가까워지면 다음 페이지 불러오기
            if (index == placeList.lastIndex - 3 && hasNext && !isLoading) {
                LaunchedEffect(Unit) {
                    searchViewModel.fetchPlaces(
                        keyword = search,
                        facilities = if (multiSelected.contains(0)) null else multiSelected.toList()
                    )
                }
            }
        }

        // 로딩 인디케이터 아이템
        if (isLoading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(){

    SearchScreen(
        searchValue = "",
        searchViewModel = viewModel(),
        onBackClick = {},
        onSearchClick = {},
        onDetailClick = {}
    )

}
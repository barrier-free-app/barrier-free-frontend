package com.moduro.barrier_free_app.presentation.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.moduro.barrier_free_app.presentation.search.navigation.SearchNavigator

@Composable
fun SearchRoute(
    navigator: SearchNavigator,
) {
    val searchViewModel: SearchViewModel = hiltViewModel()

    SearchScreen(
        searchViewModel = searchViewModel,
        onBackClick = {navigator.navigateBack()},
        onSearchClick = {},
        onDetailClick = { placeId ->
            navigator.navigateToPlaceDetail(placeId)
        }
    )

}

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onBackClick : () -> Unit,
    onSearchClick: () -> Unit,
    onDetailClick: (Int) -> Unit
) {

    var search by remember { mutableStateOf("") }
    val places = searchViewModel.dummySearchPlaces

    LazyColumn(
        modifier = Modifier
            .fillMaxSize().background(Background2)
            .padding(top = 20.dp).padding(horizontal = 16.dp)
    ) {
        item {
            SearchScreenTop(
                search = search,
                onBackClick = {onBackClick()},
                onSearchChange = { search = it },
                onSearchClick = { onSearchClick() },
            )

            Spacer(modifier = Modifier.height(30.dp))
        }



        items(places) { place ->
            HomePlaceBox(
                place = place,
                onClick = { placeId ->
                    onDetailClick(placeId)
                }
            )
            Spacer(modifier = Modifier.height(9.dp))

        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(){

    SearchScreen(
        searchViewModel = viewModel(),
        onBackClick = {},
        onSearchClick = {},
        onDetailClick = {}
    )

}
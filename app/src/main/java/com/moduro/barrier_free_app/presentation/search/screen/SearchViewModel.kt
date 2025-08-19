package com.moduro.barrier_free_app.presentation.search.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.domain.entity.SearchPlaceEntity
import com.moduro.barrier_free_app.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _placeList = MutableLiveData<List<SearchPlaceEntity>>(emptyList())
    val placeList: LiveData<List<SearchPlaceEntity>> = _placeList

    private val _page = MutableLiveData(0)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(true)
    val hasNext: LiveData<Boolean> = _hasNext

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchPlaces(
        keyword: String? = null,
        facilities: List<Int>? = null
    ) {
        if (_isLoading.value == true || _hasNext.value == false) return

        viewModelScope.launch {
            _isLoading.value = true
            val currentPage = _page.value ?: 0

            val result = searchRepository.getSearchPlaces(keyword, facilities, currentPage)
            result.onSuccess { data ->
                val currentList = _placeList.value ?: emptyList()
                _placeList.value = currentList + data.placeSearchResponses
                _hasNext.value = data.hasNext
                _page.value = currentPage + 1
            }
            _isLoading.value = false
        }
    }

    fun resetSearch() {
        _placeList.value = emptyList()
        _page.value = 0
        _hasNext.value = true
    }
}


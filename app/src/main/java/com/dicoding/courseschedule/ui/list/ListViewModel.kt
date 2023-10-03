package com.dicoding.courseschedule.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryUtil
import com.dicoding.courseschedule.util.SortType

class ListViewModel(private val repository: DataRepository) : ViewModel() {

    private val _sortParams = MutableLiveData<SortType>()

    init {
        _sortParams.value = SortType.TIME
    }

    val courses: LiveData<PagedList<Course>> = Transformations.switchMap(_sortParams) { sortType ->
        val sortedQuery = QueryUtil.sortedQuery(sortType)
        repository.getSortedCourse(sortedQuery)
    }

    fun sort(newValue: SortType) {
        _sortParams.value = newValue
    }

    fun delete(course: Course) {
        repository.delete(course)
    }
}
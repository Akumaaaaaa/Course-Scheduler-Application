package com.dicoding.courseschedule.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import com.dicoding.courseschedule.util.QueryType
import com.dicoding.courseschedule.util.QueryUtil
import com.dicoding.courseschedule.util.SortType
import com.dicoding.courseschedule.util.executeThread

// TODO 4: Implement repository with appropriate dao
class DataRepository(private val dao: CourseDao) {

    fun getNearestSchedule(queryType: QueryType): LiveData<Course?> {
        return dao.getNearestSchedule(QueryUtil.nearestQuery(queryType))
    }

    fun getAllCourse(sortType: SortType): LiveData<PagedList<Course>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(dao.getAll(), config).build()
    }

    fun getSortedCourse(query: SupportSQLiteQuery): LiveData<PagedList<Course>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(dao.getSortedCourse(query), config).build()
    }

    fun getCourse(id: Int): LiveData<Course> {
        return dao.getCourse(id)
    }

    fun getTodaySchedule(): List<Course> {
        return dao.getTodaySchedule(getCurrentDay())
    }

    fun insert(course: Course) = executeThread {
        dao.insert(course)
    }

    fun delete(course: Course) = executeThread {
        dao.delete(course)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        private const val PAGE_SIZE = 10

        fun getInstance(context: Context): DataRepository {
            return instance ?: synchronized(DataRepository::class.java) {
                val database = CourseDatabase.getInstance(context)
                instance ?: DataRepository(database.courseDao()).also { instance = it }
            }
        }
    }
}

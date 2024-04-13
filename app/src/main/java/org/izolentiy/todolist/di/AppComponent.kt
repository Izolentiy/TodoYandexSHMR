package org.izolentiy.todolist.di

import android.content.Context
import androidx.room.Room
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import org.izolentiy.todolist.AppDatabase
import org.izolentiy.todolist.TaskDao
import org.izolentiy.todolist.taskeditor.TaskEditorViewModel
import org.izolentiy.todolist.tasklist.TaskListViewModel
import javax.inject.Scope

@Scope
annotation class AppScope

@Module
interface LocalDataSourceModule {
    companion object {
        @[Provides AppScope]
        fun appDatabase(context: Context): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, "todolist_db")
            .build()

        @[Provides AppScope]
        fun taskDao(appDb: AppDatabase): TaskDao = appDb.taskDao()
    }
}

@AppScope
@Component(modules = [LocalDataSourceModule::class, ViewModelBindsModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

    val viewModelFactory: ViewModelUniversalFactory

    fun inject(taskListViewModel: TaskListViewModel)

    fun inject(taskEditorViewModel: TaskEditorViewModel)
}

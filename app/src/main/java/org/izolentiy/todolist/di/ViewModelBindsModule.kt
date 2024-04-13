package org.izolentiy.todolist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.izolentiy.todolist.taskeditor.TaskEditorViewModel
import org.izolentiy.todolist.tasklist.TaskListViewModel

@Module
interface ViewModelBindsModule {
    @[Binds IntoMap ClassKey(TaskListViewModel::class)]
    fun bindTaskListViewModel(taskListViewModel: TaskListViewModel): ViewModel

    @[Binds IntoMap ClassKey(TaskEditorViewModel::class)]
    fun bindNewTaskViewModel(taskEditorViewModel: TaskEditorViewModel): ViewModel
}
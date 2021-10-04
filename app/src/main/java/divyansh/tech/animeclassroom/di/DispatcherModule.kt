package divyansh.tech.animeclassroom.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
object DispatcherModule {

    @Qualifier
    annotation class DefaultDispatcher

    @Qualifier
    annotation class IODispatcher

    @Qualifier
    annotation class MainDispatcher

    @Qualifier
    annotation class UnconfinedDispatcher

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher():CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
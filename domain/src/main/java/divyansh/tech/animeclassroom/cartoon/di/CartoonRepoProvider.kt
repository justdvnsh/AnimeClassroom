package divyansh.tech.animeclassroom.cartoon.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import divyansh.tech.animeclassroom.cartoon.CartoonDataSource
import divyansh.tech.animeclassroom.cartoon.CartoonDefaultRepo

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartoonRepoProvider {

    @Provides
    @Binds
    @ViewModelScoped
    abstract fun bindCartoonRepo(repo: CartoonDefaultRepo): CartoonDataSource
}
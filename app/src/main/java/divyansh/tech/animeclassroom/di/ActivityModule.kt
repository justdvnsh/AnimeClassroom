package divyansh.tech.animeclassroom.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import divyansh.tech.animeclassroom.favorites.FavoritesFragment
import divyansh.tech.animeclassroom.home.HomeFragment
import divyansh.tech.animeclassroom.manga.MangaFragment
import divyansh.tech.animeclassroom.settings.SettingsFragment
import divyansh.tech.animeclassroom.shop.ShopFragment

/*
* Activity container for all the activity modules
* */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    /*
    * Provide HomeFragment
    * @returns -> Instance of HomeFragment
    * */
    @Provides
    @ActivityScoped
    fun provideHomeFragment(): HomeFragment =
        HomeFragment()

    /*
    * Provides Favorites Fragment
    * @returns -> Instance of Favorites Fragment
    * */
    @Provides
    @ActivityScoped
    fun provideFavoritesFragment(): FavoritesFragment =
        FavoritesFragment()

    /*
    * Provides MangaFragment
    * @returns -> Instance of MangaFragment
    * */
    @Provides
    @ActivityScoped
    fun providesMangaFragment(): MangaFragment =
        MangaFragment()

    /*
    * Provides ShopFragment
    * @returns -> Instance of ShopFragment
    * */
    @Provides
    @ActivityScoped
    fun provideShopFragment(): ShopFragment =
        ShopFragment()

    /*
    * Provides SettingsFragment
    * @returns -> Instance of SettingsFragment
    * */
    @Provides
    @ActivityScoped
    fun provideSettingsFragment(): SettingsFragment =
        SettingsFragment()
}
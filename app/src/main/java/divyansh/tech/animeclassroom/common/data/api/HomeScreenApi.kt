package divyansh.tech.animeclassroom.common.data.api

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET

/*
* Interface for all the home screen
* */
interface HomeScreenApi {

    /*
    * Function to fetch popular animes and recent releases
    * */
    @GET("/popular.html")
    suspend fun fetchHomeScreenData(): ResponseBody

    /*
    * Function to fetch popular movies
    * */
    @GET("/anime-movies.html")
    suspend fun fetchPopularMovies(): ResponseBody

    /*
    * Function to fetch new seasons
    * */
    @GET("/new-season.html")
    suspend fun fetchNewSeasons(): ResponseBody

    /*
    * Fetch genres
    * */
    @GET("")
    suspend fun fetchGenres(): ResponseBody
}
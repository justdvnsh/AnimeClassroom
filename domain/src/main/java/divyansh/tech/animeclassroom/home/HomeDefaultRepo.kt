package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val POPULAR_ANIME="PopularAnime"
const val POPULAR_MOVIE="PopularMovies"
const val RECENT_RELEASE="RecentRelease"
const val NEW_SEASON="NewSeason"

/*
* Main default repo, to fetch data both from remote and locally
* */
class HomeDefaultRepo @Inject constructor(
    private val remoteRepo: HomeRemoteRepo,
    private val localRepo: HomeLocalRepo
) {

    /*
    * Call the function to fetch popular animes
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parsePopularAnimes(): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getPopularAnimes()
            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, POPULAR_ANIME)
            localRepo.getAllPopularAnime()
            emit(response)
        }
    }

    /*
    * Call the function to fetch recent releases
    * @returns Flow<ResultWrapper<*>>
    * */
    suspend fun parseRecentReleases(): Flow<ResultWrapper<*>> {

        return flow {
            val response = remoteRepo.getRecentReleases()

            // TODO: Add function to check if local data already exists or not

            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, RECENT_RELEASE)
            localRepo.getAllRecentReleases()
            emit(response)
        }
    }

    /*
    * Call the function to fetch popular movies
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parsePopularMovies(): Flow<ResultWrapper<*>> {
        return flow {
            val response = remoteRepo.getPopularMovies()

            // TODO: Add function to check if local data already exists or not

            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, POPULAR_MOVIE)
            localRepo.getAllPopularMovies()
            emit(response)
        }
    }

    /*
    * Call the function to fetch new seasons
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parseNewSeasons(): Flow<ResultWrapper<*>> {
        return flow {
            val response = remoteRepo.getNewSeasons()

            // TODO: Add function to check if local data already exists or not 

            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, NEW_SEASON)
            localRepo.getAllNewSeasons()
            emit(response)
        }
    }

    /*
    * Call the function to fetch new seasons
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parseGenres(): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getGenres()
            emit(response)
        }
    }
}
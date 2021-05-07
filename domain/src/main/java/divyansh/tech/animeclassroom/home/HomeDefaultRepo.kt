package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.api.HomeScreenApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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
            emit(response)
        }
    }

    /*
    * Call the function to fetch recent releases
    * @returns Flow<ResultWrapper<*>>
    * */
    suspend fun parseRecentReleases(): Flow<ResultWrapper<*>> {
        // TODO: Add local data first.
        return flow {
            val response = remoteRepo.getRecentReleases()
            emit(response)
        }
    }

    /*
    * Call the function to fetch popular movies
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parsePopularMovies(): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getPopularMovies()
            emit(response)
        }
    }

    /*
    * Call the function to fetch new seasons
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parseNewSeasons(): Flow<ResultWrapper<*>> {
        //TODO: Add local data first. (caching purpose)
        return flow {
            val response = remoteRepo.getNewSeasons()
            emit(response)
        }
    }
}
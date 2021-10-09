package divyansh.tech.animeclassroom.home

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import divyansh.tech.animeclassroom.utils.HomeTypes

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
        return flow {

            val localData=localRepo.getAllPopularAnime()

            if(localData.isNotEmpty()){
                val response= arrayListOf<AnimeModel>()
                for (data in localData)
                    response.add(convertOfflineAnimeModel(data))
                emit(ResultWrapper.Success(response))
            } else {
                val response = remoteRepo.getPopularAnimes()
                for(anime in (response.data as ArrayList<AnimeModel>))
                    localRepo.saveAnimeDataOffline(anime, HomeTypes.POPULAR_ANIME.name)
                emit(response)
            }

        }
    }

    /*
    * Call the function to fetch recent releases
    * @returns Flow<ResultWrapper<*>>
    * */
    suspend fun parseRecentReleases(): Flow<ResultWrapper<*>> {

        return flow {

            val localData=localRepo.getAllRecentReleases()

            if(localData.isNotEmpty()){
                val response= arrayListOf<AnimeModel>()
                for (data in localData)
                    response.add(convertOfflineAnimeModel(data))
                emit(ResultWrapper.Success(response))
            } else {
                val response = remoteRepo.getRecentReleases()

                for(anime in (response.data as ArrayList<AnimeModel>))
                    localRepo.saveAnimeDataOffline(anime, HomeTypes.RECENT_RELEASE.name)
                emit(response)
            }

        }
    }

    /*
    * Call the function to fetch popular movies
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parsePopularMovies(): Flow<ResultWrapper<*>> {
        return flow {

            val localData=localRepo.getAllPopularMovies()

            if(localData.isNotEmpty()){
                val response= arrayListOf<AnimeModel>()
                for (data in localData)
                    response.add(convertOfflineAnimeModel(data))
                emit(ResultWrapper.Success(response))
            } else {
                val response = remoteRepo.getPopularMovies()

                for(anime in (response.data as ArrayList<AnimeModel>))
                    localRepo.saveAnimeDataOffline(anime, HomeTypes.POPULAR_MOVIES.name)
                emit(response)
            }

        }
    }

    /*
    * Call the function to fetch new seasons
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parseNewSeasons(): Flow<ResultWrapper<*>> {
        return flow {

            val localData=localRepo.getAllNewSeasons()

            if(localData.isNotEmpty()){
                val response= arrayListOf<AnimeModel>()
                for (data in localData)
                    response.add(convertOfflineAnimeModel(data))
                emit(ResultWrapper.Success(response))
            } else {
                val response = remoteRepo.getNewSeasons()

                for(anime in (response.data as ArrayList<AnimeModel>))
                    localRepo.saveAnimeDataOffline(anime, HomeTypes.NEW_SEASON.name)
                emit(response)
            }

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

    fun convertOfflineAnimeModel(offlineAnimeModel: OfflineAnimeModel):AnimeModel{
        return AnimeModel(
            name = offlineAnimeModel.name,
            imageUrl = offlineAnimeModel.imageUrl,
            releaseDate = offlineAnimeModel.releaseDate,
            animeUrl = offlineAnimeModel.animeUrl,
            episodeNumber = offlineAnimeModel.episodeNumber,
            episodeUrl = offlineAnimeModel.episodeUrl,
            genre = offlineAnimeModel.genre)
    }

}
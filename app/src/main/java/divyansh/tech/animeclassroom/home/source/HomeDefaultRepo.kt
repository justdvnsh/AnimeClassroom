package divyansh.tech.animeclassroom.home.source

import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.home.AnimeModel
import divyansh.tech.animeclassroom.common.data.home.OfflineAnimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import divyansh.tech.animeclassroom.common.utils.HomeTypes

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
            val responseLocal= arrayListOf<AnimeModel>()
            if(localData.isNotEmpty()){
                for (data in localData) responseLocal.add(convertToAnimeModel(data))
            }

            val response = remoteRepo.getPopularAnimes()
            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, HomeTypes.POPULAR_ANIME.name)
            emit(ResultWrapper.Success(responseLocal))
        }
    }

    /*
    * Call the function to fetch recent releases
    * @returns Flow<ResultWrapper<*>>
    * */
    suspend fun parseRecentReleases(): Flow<ResultWrapper<*>> {

        return flow {

            val localData=localRepo.getAllRecentReleases()
            val responseLocal= arrayListOf<AnimeModel>()
            if(localData.isNotEmpty()){
                for (data in localData) responseLocal.add(convertToAnimeModel(data))
            }
            val response = remoteRepo.getRecentReleases()

            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, HomeTypes.RECENT_RELEASE.name)
            emit(ResultWrapper.Success(responseLocal))
        }
    }

    /*
    * Call the function to fetch popular movies
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parsePopularMovies(): Flow<ResultWrapper<*>> {
        return flow {

            val localData=localRepo.getAllPopularMovies()
            val responseLocal= arrayListOf<AnimeModel>()
            if(localData.isNotEmpty()){
                for (data in localData) responseLocal.add(convertToAnimeModel(data))
            }
            val response = remoteRepo.getPopularMovies()
            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, HomeTypes.POPULAR_MOVIES.name)
            emit(ResultWrapper.Success(responseLocal))

        }
    }

    /*
    * Call the function to fetch new seasons
    * @returns: Flow<ResultWrapper<*>>
    * */
    suspend fun parseNewSeasons(): Flow<ResultWrapper<*>> {
        return flow {

            val localData=localRepo.getAllNewSeasons()
            val responseLocal= arrayListOf<AnimeModel>()
            if(localData.isNotEmpty()){
                for (data in localData) responseLocal.add(convertToAnimeModel(data))
            }
            val response = remoteRepo.getNewSeasons()
            for(anime in (response.data as ArrayList<AnimeModel>))
                localRepo.saveAnimeDataOffline(anime, HomeTypes.NEW_SEASON.name)
            emit(ResultWrapper.Success(responseLocal))

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

    fun convertToAnimeModel(offlineAnimeModel: OfflineAnimeModel): AnimeModel {
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
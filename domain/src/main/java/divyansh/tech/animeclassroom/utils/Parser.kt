package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.Exception

/*
* Parser object
* */
object Parser {

    /*
    * Parse popular Animes
    * @param response: Response Body from the popular.html page
    * @returns ResultWrapper<*>
    *  */
    fun parsePopularAnimeJson(response: String): ResultWrapper<*> {
        return try {
            val list: MutableList<AnimeModel> = mutableListOf()
            val jsoup = Jsoup.parse(response)
            val popularAnimes = jsoup?.getElementsByClass("last_episodes")?.first()?.select("ul")?.first()?.select("li")
            popularAnimes?.forEach {
                val url = it?.getElementsByClass("img")?.first()?.select("a")?.first()?.select("img")?.attr("src")
                val name = it?.getElementsByClass("img")?.first()?.select("a")?.attr("title")
                val released = it?.getElementsByClass("released")?.get(0)?.text().toString()
                val animeUrl = it?.getElementsByClass("img")?.first()?.select("a")?.attr("href")

                val animeModel = AnimeModel(
                    name = name.toString(),
                    imageUrl = url.toString(),
                    releaseDate = released.substringAfter(": "),
                    animeUrl = animeUrl.toString()
                )
                list.add(animeModel)
            }

            ResultWrapper.Success(list.toList())
        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }

    /*
    * parse the recent release animes
    * @param response: Response from the popular.html page
    * @returns ResultWrapper<*>
    * */
    suspend fun parseRecentReleaseJson(response: String): ResultWrapper<*> {
        return try {
            val list: MutableList<AnimeModel> = mutableListOf<AnimeModel>()
            val jsoup = Jsoup.parse(response)
            val recentReleases = jsoup?.getElementsByClass("menu_recent")?.first()?.select("ul")?.first()?.select("li")
            recentReleases?.forEach {
                val name = it?.select("a")?.attr("title")
                val episodeUrl = it?.select("a")?.attr("href")
                val imageUrl = it?.getElementsByClass("thumbnail-recent")?.first()?.attr("style")
                val episodeNumber = it?.getElementsByClass("time_2")?.get(0)?.text()

                val animeMetaModel = AnimeModel(
                    name = name.toString(),
                    imageUrl = imageUrl.toString().substringAfter("('").substringBefore("')"),
                    episodeUrl = episodeUrl.toString(),
                    episodeNumber = episodeNumber.toString()
                )

                list.add(animeMetaModel)
            }
            ResultWrapper.Success(list.toList())
        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }

    /*
    * parse the genres
    * @param response: Response from the home page
    * @returns ResultWrapper<*>
    * */
    suspend fun parseGenresAnimeJson(response: String): ResultWrapper<*> {
        return try {
            val list: MutableList<GenreModel> = mutableListOf<GenreModel>()
            val jsoup = Jsoup.parse(response)
            val recentReleases = jsoup?.getElementsByClass("genre")?.first()?.select("ul")?.first()?.select("li")
            recentReleases?.forEach {
                val title = it?.child(0)?.select("a")?.first()?.attr("title")
                val url = it?.child(0)?.select("a")?.first()?.attr("href")

                val genreModel = GenreModel(
                        genreTitle = title.toString(),
                        genreUrl = url.toString()
                )

                list.add(genreModel)
            }
            ResultWrapper.Success(list.toList())
        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }

    /*
    * parse the anime details
    * @param response: Response from the anime page
    * @returns ResultWrapper<*>
    * */
    suspend fun parseAnimeDetails(response: String): ResultWrapper<*> {
        return try {
            val jsoup = Jsoup.parse(response)
            val imageUrl = jsoup?.getElementsByClass("anime_info_body_bg")?.first()?.select("img")?.first()?.absUrl("src")
            val name = jsoup?.getElementsByClass("anime_info_body_bg")?.first()?.select("h1")?.first()?.text()
            val lists = jsoup?.getElementsByClass("type")
            lateinit var type: String
            lateinit var releaseTime: String
            lateinit var status: String
            lateinit var plotSummary: String
            val genre: ArrayList<GenreModel> = ArrayList()
            lists?.forEachIndexed { index, element ->
                when(index){
                    0-> type = element.text()
                    1-> plotSummary = element.text()
                    2-> genre.addAll(getGenreList(element.select("a")))
                    3-> releaseTime = element.text()
                    4-> status = element.text()
                }
            }
            val episodeInfo = jsoup.getElementById("episode_page")
            val episodeList = episodeInfo.select("a").last()
            val endEpisode = episodeList.attr("ep_end")
            val alias = jsoup.getElementById("alias_anime").attr("value")
            val id = jsoup.getElementById("movie_id").attr("value")
            Log.i("HOME-NAME", name.toString())
            val model = AnimeDetailModel(
                name = name.toString(),
                imageUrl = imageUrl.toString(),
                type = formatInfoValues(type),
                releaseDate = formatInfoValues(releaseTime),
                status = formatInfoValues(status),
                genre = genre,
                plotSummary = plotSummary,
                endEpisode = endEpisode.toInt()
            )

            return ResultWrapper.Success(model)
        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }

    private fun filterGenreName(genreName: String): String{
        return if(genreName.contains(',')){
            genreName.substring(genreName.indexOf(',')+1)
        }else{
            genreName
        }
    }

    private fun getGenreList(genreHtmlList: Elements): ArrayList<GenreModel>{
        val genreList = ArrayList<GenreModel>()
        genreHtmlList.forEach {
            val genreUrl = it.attr("href")
            val genreName = it.text()

            genreList.add(
                GenreModel(
                    genreUrl = genreUrl,
                    genreTitle = filterGenreName(genreName)
                )
            )

        }

        return genreList
    }

    private fun formatInfoValues(infoValue: String): String{
        return infoValue.substring(infoValue.indexOf(':')+1, infoValue.length)
    }

    private fun getCategoryUrl(url: String): String {
        return try{
            var categoryUrl =  url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'))
            categoryUrl = "/category/$categoryUrl"
            categoryUrl
        }catch (exception: StringIndexOutOfBoundsException){
//            Timber.e("Image URL: $url")
            ""
        }

    }
}
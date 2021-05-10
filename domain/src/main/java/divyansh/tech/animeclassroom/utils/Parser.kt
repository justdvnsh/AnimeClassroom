package divyansh.tech.animeclassroom.utils

import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import org.jsoup.Jsoup
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

        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }
}
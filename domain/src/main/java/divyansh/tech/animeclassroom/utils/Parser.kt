package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeMetaModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import okhttp3.Response
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

                val animeModel = AnimeModel(
                    name = name.toString(),
                    imageUrl = url.toString(),
                    releaseDate = released.substringAfter(": ")
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
            val list: MutableList<AnimeMetaModel> = mutableListOf<AnimeMetaModel>()
            val jsoup = Jsoup.parse(response)
            val recentReleases = jsoup?.getElementsByClass("menu_recent")?.first()?.select("ul")?.first()?.select("li")
            recentReleases?.forEach {
                val name = it?.select("a")?.attr("title")
                val episodeUrl = it?.select("a")?.attr("href")
                val imageUrl = it?.getElementsByClass("thumbnail-recent")?.first()?.attr("style")

                val animeMetaModel = AnimeMetaModel(
                    name = name.toString(),
                    imageUrl = imageUrl.toString().substringAfter("('").substringBefore("')"),
                    episodeUrl = episodeUrl.toString()
                )

                list.add(animeMetaModel)
            }
            ResultWrapper.Success(list.toList())
        } catch (e: Exception) {
            ResultWrapper.Error(message = e.localizedMessage, data = null)
        }
    }
}
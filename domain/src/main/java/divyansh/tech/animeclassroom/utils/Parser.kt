package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
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
    * */
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
}
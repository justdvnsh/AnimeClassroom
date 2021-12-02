package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.mangaModels.Chapters
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.regex.Pattern

/*
* The util file to parse manga
* */
object CartoonParser {

    /*
    * Parse the Home page data
    * */
    fun parseCartoons(response: String): ResultWrapper<ArrayList<Cartoons>> {
        return try {
            val cartoonList = ArrayList<Cartoons>()
            val jsoup = Jsoup.parse(response)
            val list = jsoup.getElementsByClass("image-holder")
            list.forEach {
                cartoonList.add(
                    Cartoons(
                        name = it.select("a").first().select("img").attr("alt"),
                        imageUrl = it.select("a").first().select("img").attr("data-echo"),
                        cartoonUrl = it.select("a").first().attr("href")
                    )
                )
            }
            ResultWrapper.Success(cartoonList)
        } catch (e: Exception) {
            Log.i("MANGA EX -> ", e.stackTraceToString())
            ResultWrapper.Error("Something went wrong", null)
        }
    }

    fun parseStreamingUrl(response: String): ResultWrapper<PlayerScreenModel> {
        val jsoup = Jsoup.parse(response)
        val title = jsoup.getElementsByTag("title").text()
        val script = jsoup.getElementsByTag("script")
        val url = script.filter {
            it.toString().contains("file")
        }
        Log.i("SCRIPT -> ", url.toString())
        val pattern = Pattern.compile("https?.*?\\.mp4")
        val matcher = pattern.matcher(url.toString())
        var string = ""
        return try {
            while (matcher.find()) {
                Log.e("Matcher -> ", matcher.group((0)).toString())
                if (matcher.group(0)!!.contains("mp4")) {
                    string = matcher.group(0)
                    break
                }
            }
            Log.i("STREAMING URL", string)
            ResultWrapper.Success(PlayerScreenModel(
                animeName = title,
                streamingUrl = string,
                nextEpisodeUrl = null,
                previousEpisodeUrl = null,
                mirrorLinks = null
            ))
        } catch (npe: NullPointerException) {
            ResultWrapper.Error(npe.localizedMessage, null)
        }
    }
}
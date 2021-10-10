package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.Manga
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/*
* The util file to parse manga
* */
object MangaParser {

    /*
    * Parse the Home page data
    * */
    fun parseHomePageData(response: String): ResultWrapper<*> {
        return try {
            val mangaList = ArrayList<Manga>()
            val jsoup = Jsoup.parse(response)
            val latestUpdates =
                jsoup.getElementById("latest_update").select("a")
//            Log.i("LATEST-UPDATES", latestUpdates.toString())
            for (i in 0 until latestUpdates.size - 1 step 3) {
                if (latestUpdates[i].select("img").first() == null) continue
                val imageUrl = latestUpdates[i].select("img").first().attr("data-src")
                val name = latestUpdates[i + 1].attr("title")
                val mangaUrl = latestUpdates[i + 1].attr("href")
//                val chapterNum = latestUpdates[i + 2].text()
//                val chapterUrl = latestUpdates[i + 2].attr("href")
                Log.i("IMAGE", imageUrl)
                Log.i("NAME", name)
                Log.i("MANGA", mangaUrl)
                val model = Manga(
                    name = name,
                    imageUrl = imageUrl,
                    mangaUrl = mangaUrl
                )
                mangaList.add(model)
            }
            Log.i("MANGA-LIST -> ", mangaList.toString())
            ResultWrapper.Success(mangaList)
        } catch (e: Exception) {
            Log.i("MANGA EX -> ", e.stackTraceToString())
            ResultWrapper.Error("Something went wrong", null)
        }
    }

    /*
    * Parse the Featured Titles
    * */
    fun parseFeaturedTitles(response: String): ResultWrapper<*> {
        return try {
            val jsoup = Jsoup.parse(response)
            val featuredTitles = jsoup.getElementsByClass("hled_titles_own_carousel")
            Log.i("FEATURED TITLES -> ", featuredTitles.toString())
            ResultWrapper.Success(featuredTitles)
        } catch (e: Exception) {
            ResultWrapper.Error("Something went wrong", null)
        }
    }
}
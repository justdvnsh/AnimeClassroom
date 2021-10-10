package divyansh.tech.animeclassroom.utils

import android.util.Log
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.mangaModels.Chapters
import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.models.home.GenreModel
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
            for (i in 0 until latestUpdates.size - 1 step 3) {
                val imageUrl = latestUpdates[i].select("img").first().attr("data-src")
                val name = latestUpdates[i + 1].attr("title")
                val chapterNum = latestUpdates[i + 2].text()
                val chapterUrl = latestUpdates[i + 2].attr("href")
                val model = Manga(
                    name = name.toString(),
                    imageUrl = imageUrl.toString(),
                    chapterNum = chapterNum.toString(),
                    chapterUrl = chapterUrl.toString()
                )
                mangaList.add(model)
            }
            ResultWrapper.Success(mangaList)
        } catch (e: Exception) {
            ResultWrapper.Error("Something went wrong", null)
        }
    }

    /*
    * get manga details
    * */
    fun parseMangaDetails(response: String): ResultWrapper<MangaDetail> {
        return try {
            val jsoup = Jsoup.parse(response)
            val manga = jsoup.getElementById("content")
            val name = manga.getElementsByClass("mx-1").first().text()
            Log.i("MANGA DETAIL NAME -> ", name)
            val imageUrl = manga.select("img").first().attr("src")
            Log.i("MANGA DETAIL IMAGE -> ", imageUrl)
            val listGenre = ArrayList<GenreModel>()
            manga.getElementsByClass("badge badge-secondary").forEach {
                listGenre.add(
                    GenreModel(
                        it.text(), it.attr("href")
                    )
                )
            }
            Log.i("MANGA DETAIL GENRE -> ", listGenre.toString())
            val desc = manga.getElementsByClass("col-lg-9 col-xl-10").lastIndex
            val summary = manga.getElementsByClass("col-lg-9 col-xl-10")[desc].text()
            Log.i("MANGA DETAIL DEC -> ", summary)
            val chapterList = manga.getElementsByClass("text-truncate").select("a")
            Log.i("MANGA DETAIL CHAPNA -> ", chapterList.toString())
            val chapters = ArrayList<Chapters>()
            chapterList.forEachIndexed { index, element ->
                chapters.add(
                    Chapters(
                        chapterName = element.select("a").first().text(),
                        chapterUrl = element.select("a").first().attr("href")
                    )
                )
            }
            Log.i("MANGA DETAIL CHAP -> ", chapters.toString())
            val model = MangaDetail(
                name = name,
                imageUrl = imageUrl,
                genreModel = listGenre,
                summary = summary,
                chapters = chapters
            )
            ResultWrapper.Success(model)
        } catch (e: Exception) {
            ResultWrapper.Error("Something went wrong", null)
        }
    }

    /*
    * Fetch chapter items
    * */
    fun parseChapterItems(response: String): ResultWrapper<ArrayList<String>> {
        return try {
            val jsoup = Jsoup.parse(response)
            val list = ArrayList<String>()
            val items = jsoup.getElementsByClass("reader-image-wrapper")
            Log.i("MANGA ITEM -> ", items.toString())
            for (i in 0 until items.size - 1) {
                list.add(items[i].select("img").attr("data-src"))
            }
            Log.i("MANGA ITEM -> ", list.toString())
            ResultWrapper.Success(list)
        } catch (e: Exception) {
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
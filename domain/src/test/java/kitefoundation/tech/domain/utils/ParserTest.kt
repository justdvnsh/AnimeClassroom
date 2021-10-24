package kitefoundation.tech.domain.utils

import divyansh.tech.animeclassroom.ResultWrapper.Success
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.utils.Parser.parseAnimeDetails
import divyansh.tech.animeclassroom.utils.Parser.parseEpisodeDetails
import divyansh.tech.animeclassroom.utils.Parser.parseGenresAnimeJson
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
import divyansh.tech.animeclassroom.utils.Parser.parseRecentReleaseJson
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class ParserTest {

    @Test
    fun parseAnimeDetailsTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("animeDerails.html")
        val response = html.let { parseAnimeDetails(it, true) }

        assertEquals(
            ((response as Success).data as AnimeDetailModel).imageUrl,
            "https://gogocdn.net/cover/jujutsu-kaisen-tv-dub.png"
        )
        assertEquals(
            ((response).data as AnimeDetailModel).name,
            "Jujutsu Kaisen (TV) (Dub)"
        )
        assertEquals(((response).data as AnimeDetailModel).releaseDate, " 2020")
        assertEquals(((response).data as AnimeDetailModel).endEpisode, 24)
        assertEquals(((response).data as AnimeDetailModel).status, " Completed")
        assertEquals(((response).data as AnimeDetailModel).type, " Fall 2020 Anime")
        assertEquals(((response).data as AnimeDetailModel).isSaved, true)
    }

    @Test(expected = Exception::class)
    fun parseAnimeDetailsExceptionTest(): Unit = runBlocking {
        val html = ""
        html.let { parseAnimeDetails(it, true) }
    }

    @Test
    fun parsePopularAnimeJsonTest() {
        val html = FileUtil.readFileFromResources("popularAnime.html")
        val response = html.let { parsePopularAnimeJson(it) }

        assertEquals(response.data?.size, 2)

        assertEquals(response.data?.first()?.name, "Jujutsu Kaisen (TV)")
        assertEquals(
            response.data?.first()?.imageUrl,
            "https://gogocdn.net/cover/jujutsu-kaisen-tv.png"
        )
        assertEquals(response.data?.first()?.releaseDate, "2020")
        assertEquals(response.data?.first()?.animeUrl, "/category/jujutsu-kaisen-tv")
        assertNull(response.data?.first()?.episodeUrl)
        assertNull(response.data?.first()?.episodeNumber)
        assertNull(response.data?.first()?.genre)

        assertEquals(response.data?.last()?.name, "Jujutsu Kaisen (TV) (Dub)")
        assertEquals(
            response.data?.last()?.imageUrl,
            "https://gogocdn.net/cover/jujutsu-kaisen-tv-dub.png"
        )
        assertEquals(response.data?.last()?.releaseDate, "2020")
        assertEquals(response.data?.last()?.animeUrl, "/category/jujutsu-kaisen-tv-dub")
        assertNull(response.data?.last()?.episodeUrl)
        assertNull(response.data?.last()?.episodeNumber)
        assertNull(response.data?.last()?.genre)
    }

    @Test
    fun parsePopularAnimeJsonEmptyTest() {
        val html = FileUtil.readFileFromResources("animeDerails.html")
        val response = html.let { parsePopularAnimeJson(it) }

        assertEquals(response.data?.size, 0)
    }

    @Test
    fun parsePopularAnimeJsonExceptionTest() {
        val html = FileUtil.readFileFromResources("popularAnimeErro.html")
        val response = html.let { parsePopularAnimeJson(it) }

        assertNull(response.data)
        assertEquals(response.message, "Index 0 out of bounds for length 0")
    }

    @Test
    fun parseRecentReleaseJsonTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("popularAnime.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertEquals((response.data as ArrayList<*>).size, 60)

        assertEquals(
            ((response.data as ArrayList<*>).first() as AnimeModel).name,
            "Assault Lily: Fruits"
        )
        assertEquals(
            ((response.data as ArrayList<*>).first() as AnimeModel).imageUrl,
            "https://gogocdn.net/cover/assault-lily-fruits.png"
        )
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).releaseDate)
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).animeUrl)
        assertEquals(
            ((response.data as ArrayList<*>).first() as AnimeModel).episodeUrl,
            "/assault-lily-fruits-episode-7"
        )
        assertEquals(
            ((response.data as ArrayList<*>).first() as AnimeModel).episodeNumber,
            "Episode 7"
        )
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).genre)

        assertEquals(
            ((response.data as ArrayList<*>).last() as AnimeModel).name,
            "Yu☆Gi☆Oh!: Sevens"
        )
        assertEquals(
            ((response.data as ArrayList<*>).last() as AnimeModel).imageUrl,
            "https://gogocdn.net/cover/yugioh-sevens.png"
        )
        assertNull(((response.data as ArrayList<*>).last() as AnimeModel).releaseDate)
        assertNull(((response.data as ArrayList<*>).last() as AnimeModel).animeUrl)
        assertEquals(
            ((response.data as ArrayList<*>).last() as AnimeModel).episodeUrl,
            "/yugioh-sevens-episode-69"
        )
        assertEquals(
            ((response.data as ArrayList<*>).last() as AnimeModel).episodeNumber,
            "Episode 69"
        )
        assertNull(((response.data as ArrayList<*>).last() as AnimeModel).genre)
    }

    @Test
    fun parseRecentReleaseJsonEmptyTest(): Unit = runBlocking {
        val html = ""
        val response = html.let { parseRecentReleaseJson(it) }

        assertTrue((response.data as Collection<*>).isNullOrEmpty())
    }

    @Test
    fun parseRecentReleaseJsonExceptionTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("popularAnimeErro.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertNull(response.data)
        assertEquals(response.message, "Index 0 out of bounds for length 0")
    }

    @Test
    fun parseGenresAnimeJsonTest(): Unit = runBlocking  {
        val html = FileUtil.readFileFromResources("popularAnime.html")
        val response = html.let { parseGenresAnimeJson(it) }

        assertEquals((response.data as ArrayList<*>).size, 48)
        assertEquals(((response.data as ArrayList<*>).first() as GenreModel).genreTitle, "Action")
        assertEquals(((response.data as ArrayList<*>).first() as GenreModel).genreUrl, "/genre/action")
        assertEquals(((response.data as ArrayList<*>).last() as GenreModel).genreTitle, "Yuri")
        assertEquals(((response.data as ArrayList<*>).last() as GenreModel).genreUrl, "/genre/yuri")
    }

    @Test
    fun parseGenresAnimeJsonEmptyTest(): Unit = runBlocking {
        val html = ""
        val response = html.let { parseRecentReleaseJson(it) }

        assertTrue((response.data as Collection<*>).isNullOrEmpty())
    }

    @Test
    fun parseGenresAnimeJsonExceptionTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("popularAnimeErro.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertNull(response.data)
        assertEquals(response.message, "Index 0 out of bounds for length 0")
    }

    @Test
    fun parseEpisodeDetailsTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("episodeDetails.html")
        val response = html.let { parseEpisodeDetails(it) }

        assertEquals(
            (response.data as PlayerScreenModel).animeName,
            "Boruto: Naruto Next Generations Episode 1 English Subbed"
        )
        assertEquals(
            (response.data as PlayerScreenModel).streamingUrl,
            "https://gogoplay1.com/embedplus?id=OTc2MzI=&token=ZRzkyD8ewZngaN3RModR3g&expires=1635031246"
        )
        assertNull((response.data as PlayerScreenModel).mirrorLinks)
        assertEquals(
            (response.data as PlayerScreenModel).nextEpisodeUrl,
            "/boruto-naruto-next-generations-episode-2"
        )
        assertEquals((response.data as PlayerScreenModel).previousEpisodeUrl, "null")

    }

    @Test
    fun parseEpisodeDetailsEmptyTest(): Unit = runBlocking {
        val html = ""
        val response = html.let { parseEpisodeDetails(it) }

        assertEquals((response.data as PlayerScreenModel).animeName, "null")
        assertEquals((response.data as PlayerScreenModel).streamingUrl, "https:null")
        assertNull((response.data as PlayerScreenModel).mirrorLinks)
        assertEquals((response.data as PlayerScreenModel).nextEpisodeUrl, "null")
        assertEquals((response.data as PlayerScreenModel).previousEpisodeUrl, "null")
    }

    object FileUtil {
        @Throws(IOException::class)
        fun readFileFromResources(fileName: String): String {
            var inputStream: InputStream? = null
            try {
                inputStream = getInputStreamFromResource(fileName)
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var str: String? = reader.readLine()
                while (str != null) {
                    builder.append(str)
                    str = reader.readLine()
                }
                return builder.toString()
            } finally {
                inputStream?.close()
            }
        }

        private fun getInputStreamFromResource(fileName: String) =
            javaClass.classLoader?.getResourceAsStream(fileName)
    }
}
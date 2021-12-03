package kitefoundation.tech.domain.utils

import divyansh.tech.animeclassroom.ResultWrapper.Success
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
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
        val html = FileUtil.readFileFromResources("animes.html")
        val response = html.let { parseAnimeDetails(it, true) }

        assertEquals(
            "https://gogocdn.net/cover/jujutsu-kaisen-tv-dub.png",
            ((response as Success).data as AnimeDetailModel).imageUrl
        )
        assertEquals(
            "Jujutsu Kaisen (TV) (Dub)",
            ((response).data as AnimeDetailModel).name
        )
        assertEquals(" 2020", ((response).data as AnimeDetailModel).releaseDate)
        assertEquals(24, ((response).data as AnimeDetailModel).endEpisode)
        assertEquals(" Completed", ((response).data as AnimeDetailModel).status)
        assertEquals(" Fall 2020 Anime", ((response).data as AnimeDetailModel).type)
        assertEquals(true, ((response).data as AnimeDetailModel).isSaved)
    }

    @Test(expected = Exception::class)
    fun parseAnimeDetailsExceptionTest(): Unit = runBlocking {
        val html = ""
        html.let { parseAnimeDetails(it, true) }
    }

    @Test
    fun parsePopularAnimeJsonTest() {
        val html = FileUtil.readFileFromResources("animes.html")
        val response = html.let { parsePopularAnimeJson(it) }

        assertEquals(2, response.data?.size)

        assertEquals("Jujutsu Kaisen (TV)", response.data?.first()?.name)
        assertEquals(
            "https://gogocdn.net/cover/jujutsu-kaisen-tv.png",
            response.data?.first()?.imageUrl
        )
        assertEquals("2020", response.data?.first()?.releaseDate)
        assertEquals("/category/jujutsu-kaisen-tv", response.data?.first()?.animeUrl)
        assertNull(response.data?.first()?.episodeUrl)
        assertNull(response.data?.first()?.episodeNumber)
        assertNull(response.data?.first()?.genre)

        assertEquals("Jujutsu Kaisen (TV) (Dub)", response.data?.last()?.name)
        assertEquals(
            "https://gogocdn.net/cover/jujutsu-kaisen-tv-dub.png",
            response.data?.last()?.imageUrl
        )
        assertEquals("2020", response.data?.last()?.releaseDate)
        assertEquals("/category/jujutsu-kaisen-tv-dub", response.data?.last()?.animeUrl)
        assertNull(response.data?.last()?.episodeUrl)
        assertNull(response.data?.last()?.episodeNumber)
        assertNull(response.data?.last()?.genre)
    }

    @Test
    fun parsePopularAnimeJsonEmptyTest() {
        val html = ""
        val response = html.let { parsePopularAnimeJson(it) }

        assertEquals(response.data?.size, 0)
    }

    @Test
    fun parsePopularAnimeJsonExceptionTest() {
        val html = FileUtil.readFileFromResources("animesErro.html")
        val response = html.let { parsePopularAnimeJson(it) }

        assertNull(response.data)
        assertEquals("Index 0 out of bounds for length 0", response.message)
    }

    @Test
    fun parseRecentReleaseJsonTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("animes.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertEquals(60, (response.data as ArrayList<*>).size)

        assertEquals(
            "Assault Lily: Fruits",
            ((response.data as ArrayList<*>).first() as AnimeModel).name
        )
        assertEquals(
            "https://gogocdn.net/cover/assault-lily-fruits.png",
            ((response.data as ArrayList<*>).first() as AnimeModel).imageUrl
        )
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).releaseDate)
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).animeUrl)
        assertEquals(
            "/assault-lily-fruits-episode-7",
            ((response.data as ArrayList<*>).first() as AnimeModel).episodeUrl
        )
        assertEquals(
            "Episode 7",
            ((response.data as ArrayList<*>).first() as AnimeModel).episodeNumber
        )
        assertNull(((response.data as ArrayList<*>).first() as AnimeModel).genre)

        assertEquals(
            "Yu☆Gi☆Oh!: Sevens",
            ((response.data as ArrayList<*>).last() as AnimeModel).name
        )
        assertEquals(
            "https://gogocdn.net/cover/yugioh-sevens.png",
            ((response.data as ArrayList<*>).last() as AnimeModel).imageUrl
        )
        assertNull(((response.data as ArrayList<*>).last() as AnimeModel).releaseDate)
        assertNull(((response.data as ArrayList<*>).last() as AnimeModel).animeUrl)
        assertEquals(
            "/yugioh-sevens-episode-69",
            ((response.data as ArrayList<*>).last() as AnimeModel).episodeUrl
        )
        assertEquals(
            "Episode 69",
            ((response.data as ArrayList<*>).last() as AnimeModel).episodeNumber
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
        val html = FileUtil.readFileFromResources("animesErro.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertNull(response.data)
        assertEquals("Index 0 out of bounds for length 0", response.message)
    }

    @Test

    fun parseGenresAnimeJsonTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("animes.html")
        val response = html.let { parseGenresAnimeJson(it) }

        assertEquals(48, (response.data as ArrayList<*>).size)
        assertEquals("Action", ((response.data as ArrayList<*>).first() as GenreModel).genreTitle)
        assertEquals(
            "/genre/action",
            ((response.data as ArrayList<*>).first() as GenreModel).genreUrl
        )
        assertEquals(
            "/genre/action",
            ((response.data as ArrayList<*>).first() as GenreModel).genreUrl
        )
        assertEquals("Yuri", ((response.data as ArrayList<*>).last() as GenreModel).genreTitle)
        assertEquals("/genre/yuri", ((response.data as ArrayList<*>).last() as GenreModel).genreUrl)
    }

    @Test
    fun parseGenresAnimeJsonEmptyTest(): Unit = runBlocking {
        val html = ""
        val response = html.let { parseRecentReleaseJson(it) }

        assertTrue((response.data as Collection<*>).isNullOrEmpty())
    }

    @Test
    fun parseGenresAnimeJsonExceptionTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("animesErro.html")
        val response = html.let { parseRecentReleaseJson(it) }

        assertNull(response.data)
        assertEquals("Index 0 out of bounds for length 0", response.message)
    }

    @Test
    fun parseEpisodeDetailsTest(): Unit = runBlocking {
        val html = FileUtil.readFileFromResources("animes.html")
        val response = html.let { parseEpisodeDetails(it) }

        assertEquals(
            "Boruto: Naruto Next Generations Episode 1 English Subbed",
            (response.data as PlayerScreenModel).animeName
        )
        assertEquals(
            "https://gogoplay1.com/embedplus?id=OTc2MzI=&token=ZRzkyD8ewZngaN3RModR3g&expires=1635031246",
            (response.data as PlayerScreenModel).streamingUrl
        )
        assertNull((response.data as PlayerScreenModel).mirrorLinks)
        assertEquals(
            "/boruto-naruto-next-generations-episode-2",
            (response.data as PlayerScreenModel).nextEpisodeUrl
        )
        assertEquals("null", (response.data as PlayerScreenModel).previousEpisodeUrl)

    }

    @Test
    fun parseEpisodeDetailsEmptyTest(): Unit = runBlocking {
        val html = ""
        val response = html.let { parseEpisodeDetails(it) }

        assertEquals("null", (response.data as PlayerScreenModel).animeName)
        assertEquals("https:null", (response.data as PlayerScreenModel).streamingUrl)
        assertNull((response.data as PlayerScreenModel).mirrorLinks)
        assertEquals("null", (response.data as PlayerScreenModel).nextEpisodeUrl)
        assertEquals("null", (response.data as PlayerScreenModel).previousEpisodeUrl)
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
package kitefoundation.tech.domain.utils

import divyansh.tech.animeclassroom.ResultWrapper.Success
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.utils.Parser.parseAnimeDetails
import divyansh.tech.animeclassroom.utils.Parser.parsePopularAnimeJson
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
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
        assertEquals(response.data?.first()?.releaseDate, "2020")

        assertEquals(response.data?.last()?.name, "Jujutsu Kaisen (TV) (Dub)")
        assertEquals(response.data?.last()?.releaseDate, "2020")
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
package kitefoundation.tech.domain.utils

import divyansh.tech.animeclassroom.ResultWrapper.Success
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.utils.Parser.parseAnimeDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class ParserTest {

    var html: String? = null

    @Before
    fun setUp() {
        html = FileUtil.readFileFromResources("response.html")
    }

    @Test
    fun parseAnimeDetailsTest(): Unit = runBlocking {
        val response = html?.let { parseAnimeDetails(it, true) }

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
    fun parseAnimeDetailsException(): Unit = runBlocking {
        html = ""
        html?.let { parseAnimeDetails(it, true) }
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
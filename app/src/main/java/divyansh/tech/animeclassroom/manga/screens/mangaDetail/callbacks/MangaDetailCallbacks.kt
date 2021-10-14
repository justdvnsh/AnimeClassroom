package divyansh.tech.animeclassroom.manga.screens.mangaDetail.callbacks

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.MangaDetailFragment
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.MangaDetailFragmentDirections
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.MangaDetailViewModel
import divyansh.tech.animeclassroom.mangaModels.Chapters

class MangaDetailCallbacks(
    private val mangaDetailViewModel: MangaDetailViewModel
): AnimeClickCallback {

    fun onChapterClick(chapters: Chapters, mangaName: String) {
        val action = MangaDetailFragmentDirections.actionMangaDetailFragmentToMangaPlayerFragment(
            chapterName = chapters.chapterName,
            chapterUrl = chapters.chapterUrl,
            mangaName = mangaName
        )
        mangaDetailViewModel.changeNavigation(action)
    }

    override fun onAnimeClicked(animeUrl: String) {}

    override fun onEpisodeClicked(episodeUrl: String) {}

    override fun onGenreClicked(genreUrl: String) {
        val action = MangaDetailFragmentDirections.actionMangaDetailFragmentToGenreFragment(genreUrl)
        mangaDetailViewModel.changeNavigation(action)
    }

    override fun onMangaClicked(mangaUrl: String) {}
}
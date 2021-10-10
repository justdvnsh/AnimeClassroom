package divyansh.tech.animeclassroom.manga.callbacks

import divyansh.tech.animeclassroom.manga.MangaFragmentDirections
import divyansh.tech.animeclassroom.manga.MangaViewModel
import divyansh.tech.animeclassroom.mangaModels.Manga

class MangaClickCallback(
    private val viewModel: MangaViewModel
) {

    fun onMangaClick(manga: Manga) {
        val action = MangaFragmentDirections.actionMangaFragmentToMangaDetailFragment(manga.mangaUrl)
        viewModel.changeNavigation(action)
    }
}
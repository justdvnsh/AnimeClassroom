package divyansh.tech.animeclassroom.manga.screens.mangaDetail.epoxy

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.animeDetail.callbacks.FavoriteClickCallback
import divyansh.tech.animeclassroom.mangaModels.Chapters
import divyansh.tech.animeclassroom.mangaModels.MangaDetail
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

@EpoxyModelClass(layout = R.layout.fragment_manga_details_header)
abstract class EpoxyMangaDetailHeaderModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var manga: MangaDetail

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.manga, manga)
    }
}

@EpoxyModelClass(layout = R.layout.fragment_manga_detail_plot_summary)
abstract class EpoxyMangaDetailPlotSummaryModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var mangaDetail: MangaDetail

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.manga, mangaDetail)
    }
}

@EpoxyModelClass(layout = R.layout.recycler_chapter_item)
abstract class EpoxyMangaDetailChapterModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var chapter: Chapters

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.chapter, chapter)
    }
}
package divyansh.tech.animeclassroom.manga.screens.mangaDetail.epoxy

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.callbacks.MangaDetailCallbacks
import divyansh.tech.animeclassroom.common.data.Chapters
import divyansh.tech.animeclassroom.common.data.MangaDetail

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

    @EpoxyAttribute
    lateinit var mangaName: String

    @EpoxyAttribute
    lateinit var chapterClick: MangaDetailCallbacks

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.chapter, chapter)
        binding.setVariable(BR.mangaName, mangaName)
        binding.setVariable(BR.chapterClick, chapterClick)
    }
}
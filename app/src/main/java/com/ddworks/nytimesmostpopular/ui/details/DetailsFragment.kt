package com.ddworks.nytimesmostpopular.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.extensions.applyArgs
import co.zsmb.rainbowcake.koin.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.requireArguments
import co.zsmb.rainbowcake.navigation.extensions.requireString
import com.ddworks.nytimesmostpopular.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : RainbowCakeFragment<DetailsViewState, DetailsViewModel> {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_details

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @Deprecated(message = "Use newInstance instead", replaceWith = ReplaceWith("DetailsFragment.newInstance()"))
    constructor()

    companion object {
        private const val ARG_NEWS_ID = "ARG_NEWS_ID"

        @Suppress("DEPRECATION")
        fun newInstance(transactionId: String): DetailsFragment {
            return DetailsFragment().applyArgs {
                putString(ARG_NEWS_ID, transactionId)
            }
        }
    }

    private lateinit var newsId: String

    private fun initArguments() {
        newsId = requireArguments().requireString(ARG_NEWS_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        initArguments()
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkInternetConnection()
        viewModel.loadById(newsId)
    }

    override fun render(viewState: DetailsViewState) {
        progress_circular.visibility = View.INVISIBLE
        cL_layout.visibility = View.INVISIBLE
        when (viewState) {
            is Loading -> {
                progress_circular.visibility = View.VISIBLE
            }
            is NoConnection -> {
                Toast.makeText(this.context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show()
            }
            is NewsLoaded -> {
                cL_layout.visibility = View.VISIBLE
                Picasso.get().load(viewState.data.picture).into(ivImageOfNewsDetail)
                tvTitle.text = viewState.data.title
                tvBy.text = viewState.data.byline
                tvDate.text = viewState.data.published_date
                bOpenCrome.setOnClickListener {
                    if(viewModel.checkInternetConnection())
                        openInCrome(viewState.data.url)
                }
            }
        }
    }

    private fun openInCrome(url: String){
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(url))
            .setPackage(getString(R.string.cromepackage))
        startActivity(intent)
    }
}

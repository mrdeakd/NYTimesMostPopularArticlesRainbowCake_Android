package com.ddworks.nytimesmostpopular.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.koin.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.ddworks.nytimesmostpopular.R
import com.ddworks.nytimesmostpopular.domain.DomainNews
import com.ddworks.nytimesmostpopular.ui.details.DetailsFragment
import com.ddworks.nytimesmostpopular.util.NewsAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : RainbowCakeFragment<MainViewState, MainViewModel>(), NewsAdapter.NewsItemListener {

    override fun onItemClick(newsId: Int) {
        viewModel.openDetail(newsId)
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_main

    private val adapter = NewsAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        swipe_layout.setOnRefreshListener {
            viewModel.checkInternetConnection()
            viewModel.loadAll()
            swipe_layout.isRefreshing = false
        }
        rv_items.adapter = adapter
        rv_items.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem =  menu.findItem(R.id.action_search)
        searchItem.expandActionView()
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.changeSearchString(newText)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkInternetConnection()
        viewModel.loadAll()
    }

    override fun render(viewState: MainViewState) {
        progress_circular.visibility = View.INVISIBLE
        rv_items.visibility = View.INVISIBLE
        when (viewState) {
            is Loading -> {
                progress_circular.visibility = View.VISIBLE
            }
            is NoConnection -> {
                Toast.makeText(this.context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show()
            }
            is NewsLoaded -> {
                rv_items.visibility = View.VISIBLE
                val list = mutableListOf<DomainNews>()
                for(item in viewState.dataList){
                    if(item.title.toLowerCase().contains(viewState.searchString.toLowerCase()))
                        list.add(item)
                }
                adapter.submitList(list)
            }
            is NewsDetailedLoaded -> {
                navigator?.add(DetailsFragment.newInstance(viewState.newsId.toString()))
                viewModel.setStateToLoaded()
            }
        }
    }

}

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
import com.ddworks.nytimesmostpopular.ui.details.DetailsFragment
import com.ddworks.nytimesmostpopular.ui.login.LoginFragment
import com.ddworks.nytimesmostpopular.util.NewsAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.*
import androidx.appcompat.app.AlertDialog
import com.ddworks.nytimesmostpopular.util.SortOptions.SORT_BY_ABC
import com.ddworks.nytimesmostpopular.util.SortOptions.SORT_BY_DATE


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
            viewModel.loadAll()
        }
        rvItems.apply {
            adapter = this@MainFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem =  menu.findItem(R.id.action_search)
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
        val signOutItem =  menu.findItem(R.id.sign_out)
        signOutItem.setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()
            navigator!!.replace(LoginFragment())
            true
        }
        val sortOutItem =  menu.findItem(R.id.sort_out)
        sortOutItem.setOnMenuItemClickListener {
            showDialog()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAll()
    }

    override fun render(viewState: MainViewState) {
        swipe_layout.isRefreshing = false
        progress_circular.visibility = View.INVISIBLE
        rvItems.visibility = View.INVISIBLE
        when (viewState) {
            is Loading -> {
                progress_circular.visibility = View.VISIBLE
            }
            is NoConnection -> {
                Toast.makeText(this.context, getString(R.string.NoConnection), Toast.LENGTH_LONG).show()
            }
            is NewsLoaded -> {
                rvItems.visibility = View.VISIBLE
                adapter.submitList(viewState.dataList)
            }
            is NewsDetailedLoaded -> {
                navigator?.add(DetailsFragment.newInstance(viewState.newsId.toString()))
                viewModel.setStateToDetailPageLoaded()
            }
            is NewsSearching ->{
                rvItems.visibility = View.VISIBLE
                adapter.submitList(viewState.dataList)
            }
            is NewsFilter ->{
                rvItems.visibility = View.VISIBLE
                adapter.submitList(viewState.dataList)
                rvItems.smoothScrollToPosition(0)
            }
        }
    }

    lateinit var levelDialog: AlertDialog

    val items = arrayOf<CharSequence>(SORT_BY_DATE, SORT_BY_ABC)

    private fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select the sort item")
        builder.setSingleChoiceItems(items, -1) { dialog, item ->
            viewModel.sortBy(items[item].toString())
            dialog.dismiss()
        }
        levelDialog = builder.create()
        levelDialog.show()
    }
}

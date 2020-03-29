package com.riyas.topstories.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riyas.topstories.R
import com.riyas.topstories.databinding.FragmentHomeBinding
import com.riyas.topstories.model.AppConstants
import com.riyas.topstories.model.StoryResponse
import com.riyas.topstories.view.adapters.StoriesAdapter
import com.riyas.topstories.view_model.StoriesViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class HomeFragment : BaseFragment() {
    val mStoriesViewModel: StoriesViewModel by viewModels()
    lateinit var mAdapter: StoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        mBaseViewModel = mStoriesViewModel

        super.onCreate(savedInstanceState)
        mAdapter = StoriesAdapter(this, mStoriesViewModel.getList())
        mStoriesViewModel.fetchTopStoryIds()
        mStoriesViewModel.mTopStoryIds?.observe(this, Observer {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    if (20 <= it?.data?.size!!) {
                        mStoriesViewModel.loadInitialData()

                    } else {
                        mStoriesViewModel.loadStories(0, it.data?.size)

                    }
                }catch (ex:Exception){
                    val listener = object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            mStoriesViewModel.loadInitialData(true)

                        }
                    }
                    showDialog(ex.localizedMessage,getString(R.string.retry),listener)
                }

            }
        })
        mStoriesViewModel.mDatatState?.observe(this, Observer {
            when (it) {
                AppConstants.STORIES_LOADED -> {
                    srl_main.isRefreshing = false
                    mAdapter.removeLoaderFooter()
                    mAdapter.setDeviceList(mStoriesViewModel.getList())
                }
                AppConstants.STORIES_LOADING -> {
                    if (mAdapter.mData == null || mAdapter.mData?.size!! <= 0) {
                        srl_main.isRefreshing = true

                    } else {
                        mAdapter.addLoaderFooter()
                    }
                    // mAdapter.setDeviceList(mStoriesViewModel.getList())
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ) as FragmentHomeBinding


        binding.viewModel = mStoriesViewModel
        // Inflate the layout for this fragment
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_main.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)

        rv_main.adapter = mAdapter
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = mStoriesViewModel.getTotalItemCount()
                if (mAdapter.itemCount >= totalItemCount) {
                    rv_main.removeOnScrollListener(this)
                    return
                }
                if (mStoriesViewModel.mDatatState.value == AppConstants.STORIES_LOADING) {
                    return
                }
                val listItemTotalCount = mAdapter.itemCount
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentLastItem = layoutManager.findLastVisibleItemPosition()
                if (listItemTotalCount == currentLastItem + 1) {
                    Log.d("MyTAG", "Load new list")
                    mStoriesViewModel.loadStories(mStoriesViewModel.mLastLoadedIndex, 20)

                    //rv_main.removeOnScrollListener(scrollListener)
                }
            }
        }
        rv_main.addOnScrollListener(scrollListener)
        srl_main.setOnRefreshListener {
            mStoriesViewModel.loadInitialData(true)
        }
    }

    fun goToDetails(story: StoryResponse) {
        if (!mStoriesViewModel.checkConnection(activity!!)) {
            networkFailure()
            return
        }
        //  HomeFragment
        val action = HomeFragmentDirections.goToDetailsFragment(story)
        findNavController().navigate(action)
    }

}
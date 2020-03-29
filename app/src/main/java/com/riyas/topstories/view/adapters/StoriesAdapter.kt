package com.riyas.topstories.view.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.riyas.topstories.R
import com.riyas.topstories.databinding.RvLoadingBinding
import com.riyas.topstories.databinding.RvStoriesBinding
import com.riyas.topstories.model.StoryResponse
import com.riyas.topstories.view.fragments.HomeFragment


class StoriesAdapter(val fr: HomeFragment, var mData: ArrayList<StoryResponse?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_TYPE_FOOTER = 1;
    val VIEW_TYPE_ITEM = 2
    val dummyId = "-123"
    val dummyType = "----footer---"
    var dummyFooterObject: StoryResponse

    init {
        dummyFooterObject = StoryResponse()
        dummyFooterObject.id = dummyId
        dummyFooterObject.type = dummyType
    }

    internal fun setDeviceList(deviceList: ArrayList<StoryResponse?>?) {
        mData = deviceList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            VIEW_TYPE_FOOTER -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    inflater,
                    R.layout.rv_loading,
                    parent,
                    false
                ) as RvLoadingBinding
                binding.fragment = fr
                return LoadingViewHolder(
                    binding
                )
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    inflater,
                    R.layout.rv_stories,
                    parent,
                    false
                ) as RvStoriesBinding
                binding.fragment = fr
                return StoriesViewHolder(
                    binding
                )
            }
        }


    }

    override fun getItemViewType(position: Int): Int {

        val story = mData?.get(position)
        if (isFooterObject(story)) {
            return VIEW_TYPE_FOOTER
        } else {
            return VIEW_TYPE_ITEM
        }

    }

    private fun isFooterObject(story:StoryResponse?): Boolean {

        return story?.id.equals(dummyFooterObject.id)&&story?.type.equals(dummyFooterObject.type)
    }

    override fun getItemCount(): Int {
        if (null == mData)
            return 0
        else {

            return mData!!.size

        }
    }

    //   var isFooterEnabled = false
    fun addLoaderFooter() {
        // isFooterEnabled = true
        mData?.add(dummyFooterObject)
        notifyDataSetChanged()
    }

    fun removeLoaderFooter() {
        //  isFooterEnabled = false
        mData?.remove(dummyFooterObject)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mData != null) {
            if (holder is StoriesViewHolder) {
                val current = mData!![position]

                holder.bind(current)
            }
        } else {
            // Covers the case of data not being ready yet.
            // holder.tv_projectName?.text = "No Projects"
        }
    }

    class StoriesViewHolder(val binding: RvStoriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModel: StoryResponse?) {
            binding.data = dataModel
            binding.executePendingBindings()
        }
    }

    class LoadingViewHolder(val binding: RvLoadingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataModel: StoryResponse?) {
            binding.data = dataModel
            binding.executePendingBindings()
        }
    }


}

package com.riyas.topstories.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.riyas.topstories.R
import com.riyas.topstories.databinding.FragmentDetailsBinding
import com.riyas.topstories.view_model.StoriesViewModel


class DetailsFragment:BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: DetailsFragmentArgs by navArgs()
       val  story = args.story
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        ) as FragmentDetailsBinding


        binding.view = this
        binding.data=story
        // Inflate the layout for this fragment
        return binding.root
    }

    fun close(){
        findNavController().navigate(R.id.goBackToHomeFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}
package com.ravikantsharma.custombottomnavigationview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ravikantsharma.custombottomnavigationview.R
import com.ravikantsharma.custombottomnavigationview.databinding.FragmentCommonBinding

class ChapterFragment private constructor() : Fragment() {
    private var _binding: FragmentCommonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_common, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCommonBinding.bind(view)

        binding.apply {
            tvCommon.text = "Chapter Fragment"
            commonLayout.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun getInstance(): ChapterFragment {
            return ChapterFragment()
        }
    }
}
package com.kraos.querycalendar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.FragmentTestOverlappingBinding

/**
 * @author kraos
 * @date 2023/3/1
 */
class TestOverlappingFragment : Fragment(){

    private val binding: FragmentTestOverlappingBinding by lazy {
        DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_test_overlapping,
            null,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}
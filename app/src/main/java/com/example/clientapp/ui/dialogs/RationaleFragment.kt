package com.example.clientapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentRationaleBinding
import com.example.clientapp.ui.MainActivity.Companion.RATIONALE_KEY
import com.example.clientapp.ui.MainActivity.Companion.RESULT_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RationaleFragment : BottomSheetDialogFragment() {
    private val binding: FragmentRationaleBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_rationale, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.rationaleButton.setOnClickListener {
            setFragmentResult(
                RATIONALE_KEY,
                bundleOf(RESULT_KEY to true),
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = "rationale_tag"
    }
}

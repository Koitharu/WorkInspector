package org.koitharu.workinspector.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koitharu.workinspector.R
import org.koitharu.workinspector.databinding.FragmentWorkListBinding
import org.koitharu.workinspector.ui.util.collectInLifecycle

internal class WorkDetailsFragment : Fragment(R.layout.fragment_work_list) {
    private val viewModel by viewModel<WorkDetailsViewModel>()
    private var binding: FragmentWorkListBinding? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = WorkDetailsAdapter()
        binding =
            FragmentWorkListBinding.bind(view).apply {
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = listAdapter
            }
        viewModel.workers.collectInLifecycle(viewLifecycleOwner) {
            listAdapter.submitList(it)
            binding?.textViewHolder?.isVisible = it.isEmpty()
        }
        viewModel.onError.collectInLifecycle(viewLifecycleOwner) { e ->
            Snackbar.make(
                requireView(),
                e.message ?: getString(R.string.error_generic),
                Snackbar.LENGTH_SHORT,
            ).show()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val ARG_WORKER_CLASS_NAME = "workerClassName"
    }
}

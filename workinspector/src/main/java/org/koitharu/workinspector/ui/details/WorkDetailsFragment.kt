package org.koitharu.workinspector.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koitharu.workinspector.IsolatedKoinComponent
import org.koitharu.workinspector.R
import org.koitharu.workinspector.databinding.WiFragmentWorkListBinding
import org.koitharu.workinspector.ui.util.collectInLifecycle
import org.koitharu.workinspector.ui.util.showErrorDialog

internal class WorkDetailsFragment : Fragment(R.layout.wi_fragment_work_list),
    IsolatedKoinComponent {

    private val viewModel by viewModel<WorkDetailsViewModel>()
    private var binding: WiFragmentWorkListBinding? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = WorkDetailsAdapter()
        binding =
            WiFragmentWorkListBinding.bind(view).apply {
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter = listAdapter
            }
        viewModel.workers.collectInLifecycle(viewLifecycleOwner) {
            listAdapter.submitList(it)
            binding?.textViewHolder?.isVisible = it.isEmpty()
        }
        viewModel.onError.collectInLifecycle(viewLifecycleOwner) { e ->
            showErrorDialog(requireContext(), e)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = arguments?.getString(ARG_WORKER_CLASS_NAME)?.substringAfterLast('.')
            ?: getString(R.string.wi_lib_name)
    }

    companion object {
        const val ARG_WORKER_CLASS_NAME = "workerClassName"
    }
}

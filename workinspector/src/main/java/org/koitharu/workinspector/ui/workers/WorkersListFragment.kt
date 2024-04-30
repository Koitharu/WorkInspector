package org.koitharu.workinspector.ui.workers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koitharu.workinspector.R
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import org.koitharu.workinspector.databinding.FragmentWorkListBinding
import org.koitharu.workinspector.ui.util.collectInLifecycle

internal class WorkersListFragment : Fragment(R.layout.fragment_work_list), OnWorkerClickListener {
    private val viewModel by viewModel<WorkersListViewModel>()
    private var binding: FragmentWorkListBinding? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = WorkersAdapter(this)
        binding =
            FragmentWorkListBinding.bind(view).apply {
                recyclerView.adapter = listAdapter
            }
        viewModel.workers.collectInLifecycle(viewLifecycleOwner) {
            listAdapter.submitList(it)
            binding?.textViewHolder?.isVisible = it.isEmpty()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onWorkerClick(
        view: View,
        worker: WorkerInfo,
    ) {
    }
}

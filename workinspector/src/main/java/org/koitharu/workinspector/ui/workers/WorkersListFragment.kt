package org.koitharu.workinspector.ui.workers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koitharu.workinspector.R
import org.koitharu.workinspector.data.workers.model.WorkerInfo
import org.koitharu.workinspector.databinding.FragmentWorkListBinding
import org.koitharu.workinspector.ui.details.WorkDetailsFragment
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

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.lib_name)
    }

    override fun onWorkerClick(
        view: View,
        worker: WorkerInfo,
    ) {
        val detailsFragment =
            WorkDetailsFragment().apply {
                val args = Bundle(1)
                args.putString(WorkDetailsFragment.ARG_WORKER_CLASS_NAME, worker.workerClassName)
                arguments = args
            }
        parentFragmentManager.commit {
            replace(R.id.container, detailsFragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }
}

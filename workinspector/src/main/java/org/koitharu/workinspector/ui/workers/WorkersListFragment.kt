package org.koitharu.workinspector.ui.workers

import android.os.Bundle
import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koitharu.workinspector.IsolatedKoinComponent
import org.koitharu.workinspector.R
import org.koitharu.workinspector.databinding.WiFragmentWorkListBinding
import org.koitharu.workinspector.ui.details.WorkDetailsFragment
import org.koitharu.workinspector.ui.util.collectInLifecycle
import org.koitharu.workinspector.ui.util.showErrorDialog

internal class WorkersListFragment : Fragment(R.layout.wi_fragment_work_list),
    IsolatedKoinComponent,
    OnWorkerClickListener,
    OnApplyWindowInsetsListener {

    private val viewModel by viewModel<WorkersListViewModel>()
    private var binding: WiFragmentWorkListBinding? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = WorkersAdapter(this)
        binding = WiFragmentWorkListBinding.bind(view).apply {
            ViewCompat.setOnApplyWindowInsetsListener(root, this@WorkersListFragment)
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

    override fun onApplyWindowInsets(
        v: View,
        insets: WindowInsetsCompat,
    ): WindowInsetsCompat {
        binding?.recyclerView?.run {
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            updatePadding(
                left = systemBarsInsets.left,
                right = systemBarsInsets.right,
                bottom = systemBarsInsets.bottom + paddingTop,
            )
        }
        return WindowInsetsCompat.CONSUMED
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.wi_lib_name)
    }

    override fun onWorkerClick(
        view: View,
        worker: WorkerItem,
    ) {
        val detailsFragment =
            WorkDetailsFragment().apply {
                val args = Bundle(1)
                args.putString(WorkDetailsFragment.ARG_WORKER_CLASS_NAME, worker.className)
                arguments = args
            }
        parentFragmentManager.commit {
            replace(R.id.container, detailsFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            setReorderingAllowed(true)
        }
    }
}

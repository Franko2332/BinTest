package ru.kh.bintest.ui.requestbinhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kh.bintest.app
import ru.kh.bintest.databinding.RequestBinHistoryFragmentBinding
import ru.kh.bintest.domain.appstate.BinHistoryAppState
import ru.kh.bintest.domain.dbentity.RequestedBinEntity
import ru.kh.bintest.domain.repo.RoomRepo
import javax.inject.Inject

class RequestBinHistoryFragment : Fragment() {
    @Inject
    lateinit var repo: RoomRepo
    private val _adapter = RequestedBinEntityAdapter()
    private var binding: RequestBinHistoryFragmentBinding? = null
    private val _binding get() = binding!!
    private val viewModel: BinsHistoryViewModel by viewModels {
        BinsHistoryViewModel.provideFactory(repo, this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RequestBinHistoryFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireContext().app.appComponent.inject(this)
        init()
    }

    private fun init() {
        viewModel._liveData.observe(viewLifecycleOwner) { render(it) }
        viewModel.getData()
        _binding.fabDeleteHistory.setOnClickListener {
            viewModel.deleteHistory()
        }
    }

    fun showProgress() {
        _binding.progressBar.visibility = View.VISIBLE
        _binding.binHistoryRecyclerView.visibility = View.GONE
    }

    fun hideProgress() {
        _binding.progressBar.visibility = View.GONE
        _binding.binHistoryRecyclerView.visibility = View.VISIBLE
    }

    private fun showData(data: List<RequestedBinEntity>) {
        hideProgress()
        _binding.binHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            _adapter.setData(data)
            adapter = _adapter
        }

    }

    private fun showEmpty(){
        _adapter.removeAll()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_SHORT).show()
    }

    fun render(appState: BinHistoryAppState) {
        when (appState) {
            is BinHistoryAppState.Loading -> showProgress()
            is BinHistoryAppState.DeleteAll -> showEmpty()
            is BinHistoryAppState.Success -> showData(appState.data)
            is BinHistoryAppState.Error -> showError(appState.error)
        }
    }
}
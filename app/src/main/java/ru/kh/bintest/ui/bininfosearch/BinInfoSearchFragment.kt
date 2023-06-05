package ru.kh.bintest.ui.bininfosearch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.kh.bintest.R
import ru.kh.bintest.app
import ru.kh.bintest.databinding.BinFragmentBinding
import ru.kh.bintest.domain.appstate.BinInfoAppState
import ru.kh.bintest.domain.entity.BinEntity
import ru.kh.bintest.domain.repo.Repo
import ru.kh.bintest.domain.repo.RoomRepo
import javax.inject.Inject

class BinInfoSearchFragment : Fragment() {
    @Inject
    lateinit var repo: Repo

    @Inject
    lateinit var roomRepo: RoomRepo
    private val viewModel: BinInfoViewModel by viewModels {
        BinInfoViewModelFactory.provideFactory(repo, roomRepo, this)
    }

    private lateinit var _binding: BinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.bin_fragment, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        requireContext().app.appComponent.inject(this)
        _binding.viewModel = viewModel
        _binding.tilFindByCardNumber.setStartIconOnClickListener {
            viewModel.getData(_binding.editTextFindByCardNumber.text.toString())
        }
        viewModel._binInfoAppStateLiveData.observe(viewLifecycleOwner, Observer { render(it) })
    }


    private fun showProgressBar() {
        _binding.tilFindByCardNumber.isErrorEnabled = false
        _binding.nestedScrollView.visibility = View.GONE
        _binding.progressBar.visibility = View.VISIBLE
    }

    private fun showBinLenghtInvalidError() {
        hideProgressBar()
        _binding.tilFindByCardNumber.error = "invalid number of characters"
    }

    private fun hideProgressBar() {
        _binding.progressBar.visibility = View.GONE
    }


    private fun showData(binEntity: BinEntity) {
        _binding.apply {
            progressBar.visibility = View.GONE
            nestedScrollView.visibility = View.VISIBLE
            tvSchemeNetwork.text = binEntity.scheme
            tvBrand.text = binEntity.brand
            tvLength.text = binEntity.number.length.toString()
            tvLuhn.text = binEntity.number.luhn.toString()
            tvType.text = binEntity.type
            tvPrepaide.text = binEntity.prepaid.toString()
            tvEmoji.text = binEntity.country.emoji
            tvCountry.text = binEntity.country.name
            tvLatitude.text = binEntity.country.latitude.toString()
            tvLongitude.text = binEntity.country.longitude.toString()
            tvBankName.text = binEntity.bank.name + ", "
            tvBankCity.text = binEntity.bank.city
            tvBankUrl.text = binEntity.bank.url
            tvBankPhoneNumber.text = binEntity.bank.phone
        }


    }

    private fun showError(error: Throwable) {
        hideProgressBar()
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

    private fun render(appState: BinInfoAppState) {
        when (appState) {
            is BinInfoAppState.Loading -> showProgressBar()
            is BinInfoAppState.BinLenghtInvalid -> showBinLenghtInvalidError()
            is BinInfoAppState.Success -> showData(appState.binEntity)
            is BinInfoAppState.Error -> showError(appState.error)
        }
    }

}

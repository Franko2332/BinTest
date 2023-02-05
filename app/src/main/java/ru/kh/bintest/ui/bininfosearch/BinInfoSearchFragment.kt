package ru.kh.bintest.ui.bininfosearch


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.util.*
import javax.inject.Inject

class BinInfoSearchFragment : Fragment() {
    @Inject
    lateinit var repo: Repo

    @Inject
    lateinit var roomRepo: RoomRepo
    private val viewModel: BinInfoViewModel by viewModels {
        BinInfoViewModel.provideFactory(repo, roomRepo, this)
    }
    private val observer: Observer<BinInfoAppState> by lazy { Observer { render(it) } }
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
        _binding.coordinatesLinearLayout.setOnClickListener { openMap() }
        _binding.tvBankPhoneNumber.setOnClickListener {
            Log.e("PHONE NUMBER", _binding.tvBankPhoneNumber.text.toString())
            callPhone()
        }
        _binding.tvBankUrl.setOnClickListener { openBrowser() }
        _binding.tilFindByCardNumber.setStartIconOnClickListener {
            viewModel.getData(_binding.editTextFindByCardNumber.text.toString())
        }
        viewModel._binInfoAppStateLiveData.observe(viewLifecycleOwner, observer)
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

    private fun openMap() {
        if (!_binding.tvLatitude.text.isEmpty() && !_binding.tvLatitude.text.isEmpty()) {
            val uri = String.format(
                Locale.ENGLISH, "geo:%s,%s",
                _binding.tvLatitude.text, _binding.tvLongitude.text
            )
            Log.e("URI", uri)
            Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
                requireContext().startActivity(this)
            }
        }
    }

    private fun openBrowser() {
        if (!_binding.tvBankUrl.text.isEmpty()) {
            val uri = String.format(
                Locale.ENGLISH, "http://%s",
                _binding.tvBankPhoneNumber
            )
            Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
                requireContext().startActivity(this)
            }
        }
    }

    private fun callPhone() {
        if (!_binding.tvBankPhoneNumber.text.isEmpty()) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE), 400
                )
            } else {
                val uri = String.format(
                    Locale.ENGLISH,
                    "tel:%s", _binding.tvBankPhoneNumber.text.toString()
                )
                Log.e("PHONE", uri)
                Intent(Intent.ACTION_CALL, Uri.parse(uri)).apply {
                    requireContext().startActivity(this)
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            400 -> if (grantResults.isNotEmpty()
                && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            ) {
                callPhone()
            } else {
                Log.d("TAG", "Call Permission Not Granted");
            }
        }
    }
}

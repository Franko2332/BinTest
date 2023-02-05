package ru.kh.bintest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.kh.bintest.R
import ru.kh.bintest.databinding.ActivityMainBinding
import ru.kh.bintest.ui.bininfosearch.BinInfoSearchFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val _binding: ActivityMainBinding get() = binding!!
    private val fragmentsMap = HashMap<String, Fragment>()
    private val fragmentManager by lazy { supportFragmentManager }

    companion object{
        private const val BIN_INFO_FRAGMENT_TAG = "BIN_INFO_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        init()
        showBinInfoSearchFragment()
    }

    private fun init(){
        fragmentsMap[BIN_INFO_FRAGMENT_TAG] = BinInfoSearchFragment()
    }

    private fun showBinInfoSearchFragment(){
        fragmentsMap[BIN_INFO_FRAGMENT_TAG]?.let {
            fragmentManager.beginTransaction()
                .add(R.id.container, it, null)
                .commit()
        }
    }
}
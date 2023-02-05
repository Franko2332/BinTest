package ru.kh.bintest.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import ru.kh.bintest.R
import ru.kh.bintest.app
import ru.kh.bintest.databinding.ActivityMainBinding
import ru.kh.bintest.ui.bininfosearch.BinInfoSearchFragment
import ru.kh.bintest.ui.requestbinhistory.RequestBinHistoryFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var binding: ActivityMainBinding? = null
    private val _binding: ActivityMainBinding get() = binding!!
    private val fragmentsMap = HashMap<String, Fragment>()
    private val fragmentManager by lazy { supportFragmentManager }

    companion object{
        private const val BIN_INFO_FRAGMENT_TAG = "BIN_INFO_FRAGMENT"
        private const val BIN_HISTORY_FRAGMENT_TAG = "BIN_HISTORY_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        init()
        showBinInfoSearchFragment()
    }

    private fun init(){
        _binding.bnv.setOnItemSelectedListener(this)
        fragmentsMap[BIN_INFO_FRAGMENT_TAG] = BinInfoSearchFragment()
        fragmentsMap[BIN_HISTORY_FRAGMENT_TAG] = RequestBinHistoryFragment()
        fragmentManager.beginTransaction().add(R.id.container,
            fragmentsMap[BIN_INFO_FRAGMENT_TAG]!!, null).commit()
    }

    private fun showBinInfoSearchFragment(){
        fragmentsMap[BIN_INFO_FRAGMENT_TAG]?.let {
            fragmentManager.beginTransaction()
                .replace(R.id.container, it, null)
                .commit()
        }
    }

    private fun showBinHistoryFragment(){
        fragmentsMap[BIN_HISTORY_FRAGMENT_TAG]?.let {
            fragmentManager.beginTransaction()
                .replace(R.id.container, RequestBinHistoryFragment(), null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.app.database.close()
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_item -> showBinInfoSearchFragment()
            R.id.history_item -> showBinHistoryFragment()
        }
        return true
    }
}
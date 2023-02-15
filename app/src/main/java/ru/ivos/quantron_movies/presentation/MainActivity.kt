package ru.ivos.quantron_movies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.databinding.ActivityMainBinding
import ru.ivos.quantron_movies.presentation.fragments.HomeFragment
import ru.ivos.quantron_movies.presentation.fragments.ProfileFragment
import ru.ivos.quantron_movies.presentation.fragments.SearchFragment
import ru.ivos.quantron_movies.utils.replaceFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            setContentView(binding.root)
            val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.fragmentContainer) as NavHostFragment
            navController = navHostFragment.navController
            binding.bnvMain.setupWithNavController(navController)
        }
    }
}
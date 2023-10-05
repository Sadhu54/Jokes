package com.jokesapp.presentation.jokes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jokesapp.core.ResponseState
import com.jokesapp.databinding.ActivityMainBinding
import com.jokesapp.presentation.jokes.adapter.JokeAdapter
import com.jokesapp.utils.CustomItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JokesActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    private val jokeViewModel by viewModels<JokeViewmodel>()
    private val jokeAdapter by lazy { JokeAdapter() }
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var apiCallRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        mBinding.run {
            rvJokes.run {
                adapter=jokeAdapter
                itemAnimator = CustomItemAnimator()
            }

        }
        setContentView(mBinding.root)
        setObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(apiCallRunnable!!)
    }

    // fun to set view-model
    private fun setObserver()
    {
        mBinding.run {
            jokeViewModel.run {
                // Define the API call logic inside the Runnable
                apiCallRunnable = object : Runnable {
                    override fun run() {
                        getJoke()
                        handler.postDelayed(
                            this,
                            (60 * 1000).toLong()
                        ) // 60,000 milliseconds = 1 minute
                    }
                }

                handler.post(apiCallRunnable!!)

                getJokeFromDB()

                lifecycleScope.launch {
                    getJokeFromDB.collect{
                        it?.let { list->
                            jokeAdapter.submitJokeList(it)
                        }

                    }
                }

                lifecycleScope.launch {
                    getJoke.collect{
                        it?.let {state->
                            when(state)
                            {
                                is ResponseState.Success->{
                                    state.data?.let { joke->
                                        jokeAdapter.addJoke(state.data)
                                    }
                                }

                                // handle loading or error here

                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.farroos.movieapp_newfeatured.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        val adapter = HomeAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        viewModel.loading.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.nowPlaying.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        viewModel.errorStatus.observe(viewLifecycleOwner){
            if (it){
                Snackbar.make(binding.root, "Load Data Gagal", Snackbar.LENGTH_LONG).show()
            }
        }

        /*binding.homeToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.profile -> {

                }
            }
        }*/


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
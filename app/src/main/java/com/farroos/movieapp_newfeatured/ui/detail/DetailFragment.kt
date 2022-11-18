package com.farroos.movieapp_newfeatured.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.farroos.movieapp_newfeatured.utils.urlImage
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetail(args.movieId)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingContainer.visibility = View.VISIBLE
            } else {
                binding.loadingContainer.visibility = View.GONE
            }
        }

        viewModel.detail.observe(viewLifecycleOwner) {

            Glide.with(binding.imgBackdrop)
                .load(urlImage + it?.backdropPath)
                .error(R.drawable.ic_broken)
                .into(binding.imgBackdrop)

            Glide.with(binding.imgDetail)
                .load(urlImage + it?.posterPath)
                .error(R.drawable.ic_broken)
                .into(binding.imgDetail)

            binding.txtJudul.text = it?.originalTitle
            binding.txtOverview.text = it?.overview
        }

        viewModel.errorStatus.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBar()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
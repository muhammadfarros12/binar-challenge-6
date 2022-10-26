package com.farroos.movieapp_newfeatured.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farroos.movieapp_newfeatured.data.remote.resource.Status
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()

        viewModel.getStatus().observe(viewLifecycleOwner) { status ->
            if (status) {
                binding.loading.visibility = View.GONE
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                binding.loading.visibility = View.GONE
            }
        }

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )

            viewModel.loginStatus.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {
                            viewModel.saveUserDataStore(true, it.data.id)
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "User tidak dapat ditemukan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
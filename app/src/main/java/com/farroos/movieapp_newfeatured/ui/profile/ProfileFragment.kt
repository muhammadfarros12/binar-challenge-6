package com.farroos.movieapp_newfeatured.ui.profile

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.farroos.movie.data.resource.Status
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = User()

        viewModel.getIdUser().observe(viewLifecycleOwner) {
            viewModel.userData(it)
        }

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            when (user.status) {
                Status.SUCCESS -> {
                    if (user.data != null) {
                        userData.id = user.data.id
                        userData.password = user.data.password

                        userData.fullname = user.data.fullname
                        binding.edtFullname.setText(user.data.fullname)
                        userData.username = user.data.username
                        binding.edtUsername.setText(user.data.username)
                        userData.address = user.data.address
                        binding.edtAddress.setText(user.data.address)
                        userData.email = user.data.email
                        binding.edtEmail.setText(user.data.email)

                        userData.image = user.data.image
                        val uriImage = Uri.parse(user.data.image)
                        binding.imgLogo.setImageURI(uriImage)
                        Glide.with(binding.root).load(user.data.image)
                            .circleCrop()
                            .into(binding.imgLogo)

                    } else {
                        Snackbar.make(binding.root, "User Tidak ditemukan", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnLogout.setOnClickListener {
            val dialog = AlertDialog.Builder(view.context)
            dialog.setTitle("Logout")
            dialog.setMessage("Apakah Anda Yakin Ingin Logout?")
            dialog.setPositiveButton("Yakin") { _, _ ->
                viewModel.clearDataUser()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            dialog.setNegativeButton("Batal") { listener, _ ->
                listener.dismiss()
            }
            dialog.show()
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(
                    userData
                )
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
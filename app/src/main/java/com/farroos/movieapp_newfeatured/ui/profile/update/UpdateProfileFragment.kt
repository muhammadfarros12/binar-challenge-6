package com.farroos.movieapp_newfeatured.ui.profile.update

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.databinding.FragmentUpdateProfileBinding
import com.farroos.movieapp_newfeatured.ui.register.RegisterFragment
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding as FragmentUpdateProfileBinding

    private val viewModel: UpdateProfileViewModel by viewModel()

    private var saveImageInternalStorage: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = arguments?.getParcelable<User>("user")

        if (userData != null) {
            binding.apply {
                edtUsername.setText(userData.username)
                edtFullname.setText(userData.fullname)
                edtEmail.setText(userData.email)
                edtAddress.setText(userData.address)

                saveImageInternalStorage = Uri.parse(userData.image)
                binding.imgProfile.setImageURI(saveImageInternalStorage)
                Glide.with(binding.root)
                    .load(userData.image)
                    .circleCrop()
                    .into(binding.imgProfile)

            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.txtUpdatePhoto.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(view.context)
            pictureDialog.setTitle("Select Action")
            val pictureDialogsItems = arrayOf(
                "Select Photo From Gallery",
                "Capture photo from camera"
            )
            pictureDialog.setItems(pictureDialogsItems) { _, which ->
                when (which) {
                    0 -> openGalleryForImage()
                    1 -> capturePhoto()
                }
            }
            pictureDialog.show()
        }

        binding.btnUpdate.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val fullname = binding.edtFullname.text.toString()
            val email = binding.edtEmail.text.toString()
            val address = binding.edtAddress.text.toString()
            val id = userData?.id

            val image = saveImageInternalStorage.toString()

            Log.d("update image", image)

            val user = User(
                username = username,
                fullname = fullname,
                email = email,
                address = address,
                id = id!!,
                image = image
            )

            viewModel.update(user)
        }

        viewModel.saved.observe(viewLifecycleOwner) {
            val check = it.getContentIffNotHandled() ?: return@observe
            if (check) {
                val dialog = AlertDialog.Builder(view.context)
                dialog.setTitle("Update User")
                dialog.setMessage("Apakah anda yakin ingin mengubah data ini?")
                dialog.setPositiveButton("Yakin") { _, _ ->
                    Snackbar.make(binding.root, "User berhasil diupdate", Snackbar.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment)
                }
                dialog.setNegativeButton("Batal") { listener, _ ->
                    listener.dismiss()
                }
            } else {
                Snackbar.make(binding.root, "User gagal diupdate", Snackbar.LENGTH_SHORT).show()
            }
        }


    }


    private fun openGalleryForImage() {
        Dexter.withActivity(requireActivity()).withPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
                    )
                    val fm: Fragment = this@UpdateProfileFragment
                    fm.startActivityForResult(galleryIntent, RegisterFragment.GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun capturePhoto() {
        Dexter.withActivity(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val fm: Fragment = this@UpdateProfileFragment
                    fm.startActivityForResult(galleryIntent, RegisterFragment.CAMERA)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }

        }).onSameThread().check()
    }

    private fun saveImageInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireContext())
        // path to /data/data/yourapp/app_data/imageDir
        val file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        // create imageDir
        val path = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(path)
            //Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Uri.parse(file.absolutePath)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                val image: Bitmap = data?.extras?.get("data") as Bitmap
                saveImageInternalStorage = saveImageInternalStorage(image)
                binding.imgProfile.setImageBitmap(image)
            } else if (requestCode == GALLERY) {
                val imageGallery = data?.data
                try {
                    val selectedImage = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver, imageGallery
                    )
                    saveImageInternalStorage(selectedImage)
                    binding.imgProfile.setImageBitmap(selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showRationalDialogForPermissions() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext()).setMessage(
            "" +
                    "It Looks like you have turned off permission required" +
                    "for this feature. It can be enabled under the" +
                    "Applications Settings"
        )
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val GALLERY = 1
        const val CAMERA = 2
        const val IMAGE_DIRECTORY = "UserImage"
    }

}
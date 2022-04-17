package com.samzuhalsetiawan.projek

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.samzuhalsetiawan.projek.data.UserData
import com.samzuhalsetiawan.projek.databinding.FragmentSettingBinding

class SettingFragment : Fragment(R.layout.fragment_setting) {

    companion object {
        const val NAMA_APLIKASI = "nama_aplikasi"
        const val NAMA_JASA_MOTOR = "nama_jasa_motor"
        const val NAMA_JASA_MOBIL = "nama_jasa_mobil"
        const val NAMA_DRIVER = "nama_driver"
        const val NAMA_MOTOR = "nama_motor"
        const val PLAT_MOTOR = "plat_motor"
        const val HARGA_JASA_MOTOR = "harga_jasa_motor"
        const val HARGA_JASA_MOBIL = "harga_jasa_mobil"
    }

    private lateinit var binding: FragmentSettingBinding
    private val sharePref by lazy { requireContext().getSharedPreferences("Projek", Context.MODE_PRIVATE) }

    val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.settingProfilePicture.setImageURI(it)
        UserData.userProfile = it
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.settingNamaAplikasi.editText?.setText(sharePref.getString(NAMA_APLIKASI, requireContext().resources.getString(R.string.nama_aplikasi)))
        binding.settingNamaJasaMotor.editText?.setText(sharePref.getString(NAMA_JASA_MOTOR, requireContext().resources.getString(R.string.motor)))
        binding.settingNamaJasaMobil.editText?.setText(sharePref.getString(NAMA_JASA_MOBIL, requireContext().resources.getString(R.string.mobil)))
        binding.settingHargaMotor.editText?.setText(sharePref.getString(HARGA_JASA_MOTOR, requireContext().resources.getString(R.string.harga_motor)))
        binding.settingHargaMobil.editText?.setText(sharePref.getString(HARGA_JASA_MOBIL, requireContext().resources.getString(R.string.harga_mobil)))
        binding.settingNamaDriver.editText?.setText(sharePref.getString(NAMA_DRIVER, requireContext().resources.getString(R.string.nama_driver)))
        binding.settingNamaMotor.editText?.setText(sharePref.getString(NAMA_MOTOR, requireContext().resources.getString(R.string.nama_motor)))
        binding.settingPlatMotor.editText?.setText(sharePref.getString(PLAT_MOTOR, requireContext().resources.getString(R.string.plat_motor)))
        UserData.userProfile?.let {
            binding.settingProfilePicture.setImageURI(it)
        } ?: binding.settingProfilePicture.setImageResource(UserData.userDefaultProfileResId)

        binding.btnSimpan.setOnClickListener {
            val sharePrefEditor = sharePref.edit()
            sharePrefEditor.putString(NAMA_APLIKASI, binding.settingNamaAplikasi.editText?.text.toString())
            sharePrefEditor.putString(NAMA_JASA_MOTOR, binding.settingNamaJasaMotor.editText?.text.toString())
            sharePrefEditor.putString(NAMA_JASA_MOBIL, binding.settingNamaJasaMobil.editText?.text.toString())
            sharePrefEditor.putString(HARGA_JASA_MOTOR, binding.settingHargaMotor.editText?.text.toString())
            sharePrefEditor.putString(HARGA_JASA_MOBIL, binding.settingHargaMobil.editText?.text.toString())
            sharePrefEditor.putString(NAMA_MOTOR, binding.settingNamaMotor.editText?.text.toString())
            sharePrefEditor.putString(NAMA_DRIVER, binding.settingNamaDriver.editText?.text.toString())
            sharePrefEditor.putString(PLAT_MOTOR, binding.settingPlatMotor.editText?.text.toString())
            when {
                sharePrefEditor.commit() -> {
                    Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnGantiFoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

    }

}
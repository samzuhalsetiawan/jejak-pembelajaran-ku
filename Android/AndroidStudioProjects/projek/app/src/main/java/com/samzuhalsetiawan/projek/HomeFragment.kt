package com.samzuhalsetiawan.projek

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.samzuhalsetiawan.projek.data.UserData
import com.samzuhalsetiawan.projek.databinding.AlertdialogDriverDitemukanBinding
import com.samzuhalsetiawan.projek.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharePref = requireContext().getSharedPreferences("Projek", Context.MODE_PRIVATE)

        binding.clOrderContainer.outlineProvider = object : ViewOutlineProvider() {
            private val curveRadius = 75f
            override fun getOutline(view: View?, outline: Outline?) {
                view?.let {
                    outline?.setRoundRect(0, 0, view.width, (view.height+curveRadius).toInt(), curveRadius)
                }
            }
        }
        binding.clOrderContainer.clipToOutline = true

        binding.btnOrder.setOnClickListener {

            val viewDriverDitemukan = layoutInflater
                .inflate(R.layout.alertdialog_driver_ditemukan, binding.root, false)

            val viewLoadingDialog = layoutInflater
                .inflate(R.layout.alertdialog_mencari_driver, binding.root, false)

            val dialogDriverDitemukanBinding = AlertdialogDriverDitemukanBinding.bind(viewDriverDitemukan)
            dialogDriverDitemukanBinding.tvDriverName.text =
                sharePref.getString(SettingFragment.NAMA_DRIVER, requireContext().resources.getString(R.string.nama_driver)).toString()
            dialogDriverDitemukanBinding.tvNamaMotor.text =
                sharePref.getString(SettingFragment.NAMA_MOTOR, requireContext().resources.getString(R.string.nama_motor)).toString()
            dialogDriverDitemukanBinding.tvPlatMotor.text =
                sharePref.getString(SettingFragment.PLAT_MOTOR, requireContext().resources.getString(R.string.plat_motor)).toString()
            UserData.userProfile?.let {
                dialogDriverDitemukanBinding.ivProfilePicture.setImageURI(it)
            } ?: dialogDriverDitemukanBinding.ivProfilePicture.setImageResource(UserData.userDefaultProfileResId)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(viewDriverDitemukan)
                .create()

            val loadingDialog = AlertDialog.Builder(requireContext())
                .setView(viewLoadingDialog)
                .setCancelable(false)
                .create()

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loadingDialog.show()
            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismiss()
                dialog.show()
            }, 2000)
        }

        binding.clOrderProjek.setOnClickListener {
            it.setBackgroundResource(R.drawable.order_background)
            binding.clOrderProcar.setBackgroundResource(R.drawable.order_background_white)
        }
        binding.clOrderProcar.setOnClickListener {
            it.setBackgroundResource(R.drawable.order_background)
            binding.clOrderProjek.setBackgroundResource(R.drawable.order_background_white)
        }

        binding.tvProjek.text = sharePref.getString(SettingFragment.NAMA_JASA_MOTOR, requireContext().resources.getString(R.string.motor))
        binding.tvHargaProjek.text = sharePref.getString(SettingFragment.HARGA_JASA_MOTOR, requireContext().resources.getString(R.string.harga_motor))
        binding.tvProcar.text = sharePref.getString(SettingFragment.NAMA_JASA_MOBIL, requireContext().resources.getString(R.string.mobil))
        binding.tvHargaProcar.text = sharePref.getString(SettingFragment.HARGA_JASA_MOBIL, requireContext().resources.getString(R.string.harga_mobil))

    }

}
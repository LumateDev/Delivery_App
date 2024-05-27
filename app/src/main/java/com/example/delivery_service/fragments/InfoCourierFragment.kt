package com.example.delivery_service.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.delivery_service.ActivityInterface
import com.example.delivery_service.R
import com.example.delivery_service.databinding.FragmentCourierBinding
import com.example.delivery_service.databinding.FragmentInfoCourierBinding
import com.example.delivery_service.models.Courier
import java.util.Date

class InfoCourierFragment : Fragment() {

    private lateinit var vehicleType: Array<String>

    companion object {
        private var INSTANCE: InfoCourierFragment? = null

        fun getInstance(): InfoCourierFragment {
            if (INSTANCE == null) INSTANCE = InfoCourierFragment()
            return INSTANCE ?: throw Exception("InfoCourierFragment не создан!")
        }
    }

    private lateinit var viewModel: InfoCourierViewModel
    private lateinit var _binding: FragmentInfoCourierBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoCourierBinding.inflate(inflater, container, false)
        vehicleType = resources.getStringArray(R.array.vehicleType)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(InfoCourierViewModel::class.java)


        viewModel.deliveryDepartment.observe(viewLifecycleOwner){
            binding.tvDeliveryDepartmentInfo.text = it.deliveryDepartmentName
        }

        viewModel.courier.observe(viewLifecycleOwner){
            binding.tvFirstNameInfo.text = it.firstName
            binding.tvLastNameInfo.text = it.lastName
            binding.tvMiddleNameInfo.text = it.middleName
            binding.tvEmailInfo.text = it.email
            binding.tvPhoneNumverInfo.text = it.phoneNumber
            binding.tvStartDateInfo.text = Date(it.startDate).toString()
            binding.tvVehicleTypeInfo.text = vehicleType[it.vehicleType]
            binding.tvLicenseNumberInfo.text = it.licenseNumber
            binding.tvUndeliveredOrdersCountInfo.text = it.undeliveredOrdersCount.toString()
        }



        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as ActivityInterface).updateTitle("Информация о курьере")
    }
}
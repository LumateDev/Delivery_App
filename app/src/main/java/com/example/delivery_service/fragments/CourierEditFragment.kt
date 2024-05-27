package com.example.delivery_service.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.delivery_service.ActivityInterface
import com.example.delivery_service.R
import com.example.delivery_service.databinding.FragmentCourierEditBinding
import com.example.delivery_service.models.Courier
import java.text.SimpleDateFormat

class CourierEditFragment : Fragment() {

    private var courier: Courier? = null
    private lateinit var vehicleArray: Array<String>

    companion object {
        fun newInstance(courier: Courier? = null): CourierEditFragment {
            return CourierEditFragment().apply { this.courier = courier }
        }
    }

    private lateinit var viewModel: CourierEditViewModel
    private lateinit var _binding: FragmentCourierEditBinding
    val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourierEditBinding.inflate(inflater, container, false)

        vehicleArray = resources.getStringArray(R.array.vehicleType)
        val adapterVehicle = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, vehicleArray)

        binding.spinnerVehicleType.adapter = adapterVehicle


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.courier = this.courier
        if (viewModel.courier != null)
            courierForm()

        binding.cvStartDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            // Добавляем 1 к месяцу, чтобы скорректировать отсчет с 0
            val customDate = dateFormat.parse("${year}-${month + 1}-${dayOfMonth}")
            viewModel.date = customDate!!.time
        }

        binding.btnSave.setOnClickListener {

            if (binding.etLastName.text.isNullOrEmpty() ||
                binding.etFirstName.text.isNullOrEmpty() ||
                binding.etMiddleName.text.isNullOrEmpty() ||
                binding.etPhone.text.isNullOrEmpty() ||
                binding.etEmail.text.isNullOrEmpty() ||
                binding.etLicenseNumber.text.isNullOrEmpty() ||
                !binding.etUndeliveredOrderCount.text.toString().matches("\\d+".toRegex()) ||
                !binding.etEmail.text.toString().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex())) {

                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля корректно", Toast.LENGTH_SHORT).show()

            }
            else{
                if (viewModel.courier == null){
                    viewModel.appendCourier(
                        binding.etLastName.text.toString(),
                        binding.etFirstName.text.toString(),
                        binding.etMiddleName.text.toString(),
                        binding.etPhone.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.spinnerVehicleType.selectedItemPosition,
                        viewModel.date,
                        binding.etLicenseNumber.text.toString(),
                        binding.etUndeliveredOrderCount.text.toString().toInt()
                    )
                }
                else {
                    viewModel.updateCourier(
                        binding.etLastName.text.toString(),
                        binding.etFirstName.text.toString(),
                        binding.etMiddleName.text.toString(),
                        binding.etPhone.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.spinnerVehicleType.selectedItemPosition,
                        viewModel.date,
                        binding.etLicenseNumber.text.toString(),
                        binding.etUndeliveredOrderCount.text.toString().toInt()
                    )
                }
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }


        }
        binding.btnCancel.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun courierForm() {
        binding.etFirstName.setText(viewModel.courier!!.firstName)
        binding.etLastName.setText(viewModel.courier!!.lastName)
        binding.etMiddleName.setText(viewModel.courier!!.middleName)
        binding.etPhone.setText(viewModel.courier!!.phoneNumber)
        binding.spinnerVehicleType.setSelection(viewModel.courier!!.vehicleType)
        binding.cvStartDate.setDate(viewModel.courier!!.startDate)
        binding.etEmail.setText(viewModel.courier!!.email)
        binding.etLicenseNumber.setText(viewModel.courier!!.licenseNumber)
        binding.etUndeliveredOrderCount.setText(viewModel.courier!!.undeliveredOrdersCount.toString())
        binding.btnSave.text = "Изменить"
    }

    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(CourierEditViewModel::class.java)
        (requireContext() as ActivityInterface).updateTitle("Отдел доставки ${viewModel.deliveryDepartment!!.deliveryDepartmentName}")
        super.onAttach(context)
    }
}
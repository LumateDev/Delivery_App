package com.example.delivery_service.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.delivery_service.ActivityInterface
import com.example.delivery_service.MainActivity
import com.example.delivery_service.R
import com.example.delivery_service.databinding.FragmentDepartmentBinding
import com.example.delivery_service.models.DeliveryDepartment
import com.example.delivery_service.repository.DataRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DepartmentFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE: DepartmentFragment? = null

        fun getInstance(): DepartmentFragment {
            if (INSTANCE == null) INSTANCE = DepartmentFragment()
            return INSTANCE ?: throw Exception("DepartmentFragment не создан!")
        }
    }

    private lateinit var viewModel: DepartmentViewModel
    private lateinit var _binding: FragmentDepartmentBinding
    private var tabPosition: Int = 0
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepartmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DepartmentViewModel::class.java)
        DataRepository.getInstance().currentDeliveryDepartment.observe(viewLifecycleOwner){
            val ma = (requireActivity() as ActivityInterface)
            ma.updateTitle("Отдел доставки \"${viewModel.department?.deliveryDepartmentName}\"")
        }
        viewModel.departmentList.observe(viewLifecycleOwner) {
            createUI(it)
        }
    }

    private inner class DepartmentPageAdapter(fa: FragmentActivity, private val departmentList: List<DeliveryDepartment>?): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return (departmentList?.size ?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return CourierFragment.newInstance(departmentList!![position])
        }
    }

    private fun createUI(departmentList: List<DeliveryDepartment>) {
        binding.tlDeliveryDepartments.clearOnTabSelectedListeners()
        binding.tlDeliveryDepartments.removeAllTabs()

        for (i in 0  until  (departmentList.size)) {
            binding.tlDeliveryDepartments.addTab(binding.tlDeliveryDepartments.newTab().apply {
                text = departmentList.get(i).deliveryDepartmentName
            })
        }

        val adapter = DepartmentPageAdapter(requireActivity(), viewModel.departmentList.value)
        binding.vpCouriers.adapter = adapter
        TabLayoutMediator(binding.tlDeliveryDepartments, binding.vpCouriers, true, true) {
                tab, pos ->
            tab.text = departmentList.get(pos).deliveryDepartmentName
        }.attach()
        tabPosition = 0
        if (viewModel.department != null)
            tabPosition = if (viewModel.getDepartmentListPosition >= 0)
                viewModel.getDepartmentListPosition
            else
                0
        viewModel.setCurrentDepartment(tabPosition)
        binding.tlDeliveryDepartments.selectTab(binding.tlDeliveryDepartments.getTabAt(tabPosition), true)

        binding.tlDeliveryDepartments.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentDepartment(departmentList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun append() {
        appendDeliveryDepartment()
    }

    override fun edit() {
        if (viewModel.department != null)
            updateDeliveryDepartment(viewModel.department!!)
    }

    override fun delete() {
        if (viewModel.department != null)
            deleteDeliveryDepartment(viewModel.department!!)
    }

    private fun appendDeliveryDepartment() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.departments_form, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etDepName)
        AlertDialog.Builder(requireContext())
            .setTitle("Информация об отделе доставки")

            .setView(mDialogView)


            .setPositiveButton("Добавить") { _, _ ->
                if (inputName.text.isNotBlank() && !inputName.text.matches(Regex("^\\d+$"))) {
                    viewModel.appendDepartment(inputName.text.toString())

                } else {
                    // Поле пусто или содержит только цифры, показываем уведомление
                    Toast.makeText(context, "Поле заполнено неверно", Toast.LENGTH_SHORT).show()


                }
            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun updateDeliveryDepartment(department: DeliveryDepartment) {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.departments_form, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etDepName)
        inputName.setText(department.deliveryDepartmentName)
        AlertDialog.Builder(requireContext())
            .setTitle("Информация об отделе доставки")
            .setView(mDialogView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Изменить") { _, _ ->
                if (inputName.text.isNotBlank()) {
                    department.deliveryDepartmentName = inputName.text.toString()
                    viewModel.updateDepartment(department)
                }
            }

            .setCancelable(true)
            .create()
            .show()
    }

    private fun deleteDeliveryDepartment(department: DeliveryDepartment) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите отдел доставки ${department.deliveryDepartmentName}?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteDepartment(department)
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }
}
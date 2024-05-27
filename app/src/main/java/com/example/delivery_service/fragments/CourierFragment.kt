package com.example.delivery_service.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_service.ActivityInterface
import com.example.delivery_service.MainActivity
import com.example.delivery_service.R
import com.example.delivery_service.databinding.FragmentCourierBinding
import com.example.delivery_service.models.Courier
import com.example.delivery_service.models.DeliveryDepartment

class CourierFragment : Fragment() {

    private lateinit var deliveryDepartment: DeliveryDepartment
    private lateinit var vehicleType: Array<String>

    companion object {
        private var INSTANCE: CourierFragment? = null

        fun getInstance(): CourierFragment {
            if (INSTANCE == null) INSTANCE = CourierFragment()
            return INSTANCE ?: throw Exception("CourierFragment не создан!")
        }
        fun newInstance(deliveryDepartment: DeliveryDepartment): CourierFragment {
            return CourierFragment().apply { this.deliveryDepartment = deliveryDepartment }
        }
    }

    private lateinit var viewModel: CourierViewModel
    private lateinit var _binding: FragmentCourierBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourierBinding.inflate(inflater, container, false)

        binding.rvCouriers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        vehicleType = resources.getStringArray(R.array.vehicleType)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.set_Department(deliveryDepartment)
        viewModel.courierList.observe(viewLifecycleOwner) {
            binding.rvCouriers.adapter = CouriersAdapter(it)
        }

        binding.fabAdd.setOnClickListener {

            (requireContext() as ActivityInterface).setFragment(MainActivity.courierEdit, null)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.rvCouriers.adapter = CouriersAdapter(viewModel.courierList.value ?: listOf())
    }

    private inner class CouriersAdapter(private val items: List<Courier>): RecyclerView.Adapter<CouriersAdapter.ItemHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CouriersAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.courier_info_elem, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: CouriersAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.courierList.value!![position])
        }

        private var lastView: View? = null
        private fun updateCurrentView(view: View) {
            lastView?.findViewById<ConstraintLayout>(R.id.clCourier)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.ligthGray))
            lastView?.findViewById<LinearLayout>(R.id.llButtons)?.visibility = View.GONE
            view.findViewById<ConstraintLayout>(R.id.clCourier)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.darkGray))
            view?.findViewById<LinearLayout>(R.id.llButtons)?.visibility = View.VISIBLE
            lastView = view
        }

        private inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            private var clCourier = itemView.findViewById<ConstraintLayout>(R.id.clCourier)
            private var tvFIO = itemView.findViewById<TextView>(R.id.tvFIO)
            private var tvUndeliveredOrders = itemView.findViewById<TextView>(R.id.tvUnderliveredOrdersCount)
            private var tvTitleFIO = itemView.findViewById<TextView>(R.id.tvTitle)
            private var tvTitleOrdersCount = itemView.findViewById<TextView>(R.id.tvTitle2)
            private var ibInfo = itemView.findViewById<ImageButton>(R.id.ibInfo)
            private var ibUpdate = itemView.findViewById<ImageButton>(R.id.ibUpdate)
            private var ibDelete = itemView.findViewById<ImageButton>(R.id.ibDelete)

            fun bind(courier: Courier) {
                if (courier == viewModel.couier)
                    updateCurrentView(itemView)

                tvFIO.text = courier.getFIO()
                tvUndeliveredOrders.text = courier.undeliveredOrdersCount.toString()

                val clickItem = View.OnClickListener {
                    viewModel.setCurrentCourier(courier)
                    updateCurrentView(itemView)
                }

                val longClickName = View.OnLongClickListener {
                    it.callOnClick()
                    viewModel.sortedByName()
                    updateCurrentView(itemView)
                    true
                }

                val longClickUndeliveredOrdersCount = View.OnLongClickListener {
                    it.callOnClick()
                    viewModel.sortedByUndeliveredOrders()
                    updateCurrentView(itemView)
                    true
                }

                clCourier.setOnClickListener(clickItem)
                clCourier.setOnLongClickListener{
                    it.callOnClick()
                    val toast = Toast.makeText(requireContext(), "Тип доставки: ${vehicleType[courier.vehicleType]}", Toast.LENGTH_SHORT)
                    toast.show()
                    true
                }
                tvFIO.setOnLongClickListener(longClickName)
                tvTitleFIO.setOnLongClickListener(longClickName)

                tvUndeliveredOrders.setOnLongClickListener(longClickUndeliveredOrdersCount)
                tvTitleOrdersCount.setOnLongClickListener(longClickUndeliveredOrdersCount)

                ibInfo.setOnClickListener {
                    (requireActivity() as ActivityInterface).setFragment(MainActivity.infoCourierId)
                }

                ibUpdate.setOnClickListener {
                    (requireContext() as ActivityInterface).setFragment(MainActivity.courierEdit, courier)
                }

                ibDelete.setOnClickListener {
                    deleteCourier()
                }
            }
        }
    }

    private fun deleteCourier(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить курьера ${viewModel.couier?.getFIO() ?: ""}?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteCourier()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(CourierViewModel::class.java)
        super.onAttach(context)
    }
}
package com.example.delivery_service

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.delivery_service.fragments.CourierEditFragment
import com.example.delivery_service.fragments.DepartmentFragment
import com.example.delivery_service.fragments.InfoCourierFragment
import com.example.delivery_service.models.Courier

class MainActivity : AppCompatActivity(), ActivityInterface {

    companion object {
        const val deliveryDeparmentsId = 0
        const val courierEdit = 1
        const val infoCourierId = 2
    }

    interface Edit {
        fun append()
        fun edit()
        fun delete()
    }

    private var _miCreateDeliveryDepartment: MenuItem? = null
    private var _miUpdateDeliveryDepartment: MenuItem? = null
    private var _miDeleteDeliveryDepartment: MenuItem? = null
    private var currentFragmentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                when (currentFragmentId) {
                    deliveryDeparmentsId -> {
                        finish()
                    }
                    courierEdit -> {
                        currentFragmentId = deliveryDeparmentsId
                    }
                    infoCourierId -> {
                        currentFragmentId = deliveryDeparmentsId

                    }
                    else -> {}
                }
                updateMenuView()
            }
            else {
                finish()
            }
        }
        setFragment(deliveryDeparmentsId)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .addToBackStack(null)
            .commit()
        updateMenuView()

    }

    private fun updateMenuView() {
        _miCreateDeliveryDepartment?.isVisible = currentFragmentId == deliveryDeparmentsId
        _miUpdateDeliveryDepartment?.isVisible = currentFragmentId == deliveryDeparmentsId
        _miDeleteDeliveryDepartment?.isVisible = currentFragmentId == deliveryDeparmentsId
    }

    override fun updateTitle(newTitle: String) {
        title = newTitle
    }

    override fun setFragment(fragmentId: Int, courier: Courier?) {
        currentFragmentId = fragmentId
        when (fragmentId) {
            deliveryDeparmentsId -> { setFragment(DepartmentFragment.getInstance()) }
            courierEdit -> {setFragment(CourierEditFragment.newInstance(courier))}
            infoCourierId -> {setFragment(InfoCourierFragment.getInstance())}
        }
        updateMenuView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miAppendDeliveryDepartment -> {
                val fedit: Edit = DepartmentFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateDeliveryDepartment-> {
                val fedit: Edit = DepartmentFragment.getInstance()
                fedit.edit()
                true
            }
            R.id.miDeleteDeliveryDepartment -> {
                val fedit: Edit = DepartmentFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miCreateDeliveryDepartment = menu?.findItem(R.id.miAppendDeliveryDepartment)
        _miUpdateDeliveryDepartment = menu?.findItem(R.id.miUpdateDeliveryDepartment)
        _miDeleteDeliveryDepartment = menu?.findItem(R.id.miDeleteDeliveryDepartment)
        updateMenuView()
        return true
    }
}
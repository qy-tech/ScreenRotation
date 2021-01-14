package com.qytech.hdmirotation.ui.main

import android.content.Context
import android.os.Bundle
import android.os.ServiceManager
import android.os.SystemProperties
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qytech.hdmirotation.R
import com.qytech.hdmirotation.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val TAG = "qytech::HDMIRotation"
    }

    private lateinit var viewModel: MainViewModel
    private var wm: IWindowManager? = null
    private lateinit var dataBinding: MainFragmentBinding
    private lateinit var adapter: ArrayAdapter<CharSequence>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = MainFragmentBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initWindowManager()
        adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hdmi_rotation,
            android.R.layout.simple_spinner_item
        );
        dataBinding.spinner.adapter = adapter
        dataBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                freezeRotation(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun initWindowManager() {
        try {
            wm = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
     /**
      * Rotation constant: 0 degree rotation (natural orientation)
      */
     public static final int ROTATION_0 = 0;

     /**
      * Rotation constant: 90 degree rotation.
      */
     public static final int ROTATION_90 = 1;

     /**
      * Rotation constant: 180 degree rotation.
      */
     public static final int ROTATION_180 = 2;

     /**
      * Rotation constant: 270 degree rotation.
      */
     public static final int ROTATION_270 = 3;
    * */
    private fun freezeRotation(rotation: Int) {
        try {
            val rotationValue = when (rotation) {
                Surface.ROTATION_0 -> "0"
                Surface.ROTATION_90 -> "90"
                Surface.ROTATION_180 -> "180"
                Surface.ROTATION_270 -> "270"
                else -> "0"
            }
            SystemProperties.set("persist.sys.orientation", rotationValue)
            wm?.freezeRotation(rotation)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
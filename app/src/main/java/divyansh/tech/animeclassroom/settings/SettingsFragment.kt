package divyansh.tech.animeclassroom.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.databinding.FragmentHomeBinding
import divyansh.tech.animeclassroom.databinding.FragmentSearchBinding
import divyansh.tech.animeclassroom.generated.callback.OnClickListener
import kotlinx.android.synthetic.main.fragment_settings.*

//Todo: Use FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        ui_mode.setOnClickListener{showUIDialog()}
    }

    private fun showUIDialog() {
        val uiModes = resources.getStringArray(R.array.ui_mode)
        val checkedItem = 0

        val alertDialogBuilder =  AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Choose ui Mode")
        alertDialogBuilder.setSingleChoiceItems(uiModes, checkedItem){ dialog, checked ->
            setUiMode(checked)
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create().apply {
            setCancelable(true)
            show()
        }
    }

    private fun setUiMode(checked: Int) {
        when(checked){
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
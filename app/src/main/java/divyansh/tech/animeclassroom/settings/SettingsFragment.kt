package divyansh.tech.animeclassroom.settings

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.C
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.Constants
import divyansh.tech.animeclassroom.common.setUIMode
import divyansh.tech.animeclassroom.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment(){

    @Inject
    lateinit var sharedPreference: SharedPreferences

    private lateinit var _settingsFragmentBinding: FragmentSettingsBinding
    val binding: FragmentSettingsBinding get() = _settingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _settingsFragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.uiMode.setOnClickListener{showUIDialog()}
    }

    private fun showUIDialog() {
        val uiModes = resources.getStringArray(R.array.ui_modes)
        val checkedItem = sharedPreference.getInt(C.THEME, C.UI_MODE.DARK_MODE.value)

        val alertDialogBuilder =  AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Choose ui Mode")
        alertDialogBuilder.setSingleChoiceItems(uiModes, checkedItem){ dialog, checked ->
            changeTheme(checked)
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create().apply {
            setCancelable(true)
            show()
        }
    }

    private fun changeTheme(checked: Int) {
        with(sharedPreference.edit()){
            putInt(C.THEME, checked)
            apply()
        }
        setUIMode(checked)
    }
}
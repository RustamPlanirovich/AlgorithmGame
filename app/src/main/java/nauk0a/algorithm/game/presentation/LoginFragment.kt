package nauk0a.algorithm.game.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import nauk0a.algorithm.game.R
import nauk0a.algorithm.game.databinding.FragmentLoginBinding
import nauk0a.algorithm.game.presentation.viewmodels.LoginViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatWatcher =
            MaskFormatWatcher(MaskImpl.createNonTerminated(PredefinedSlots.RUS_PHONE_NUMBER))
        formatWatcher.installOn(binding.loginEt)

        binding.passEt.doAfterTextChanged {
            if (isValidPassword(it.toString())){
                binding.etPasswordLayout.isErrorEnabled = false

            } else {
                binding.etPasswordLayout.isErrorEnabled = true
                binding.etPasswordLayout.error = getString(R.string.pass_error_message)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    internal fun isValidPassword(password: String): Boolean {
        if (password.length < 7) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                .firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                .firstOrNull() == null) return false
        if (password.filter { it.isLetterOrDigit() }.firstOrNull() == null) return false
        return true
    }
}
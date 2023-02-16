package ru.ivos.quantron_movies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.databinding.FragmentProfileBinding
import ru.ivos.quantron_movies.utils.AUTH
import ru.ivos.quantron_movies.utils.gone
import ru.ivos.quantron_movies.utils.initFirebase
import ru.ivos.quantron_movies.utils.visible

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private var email: String = ""
    private var name: String = ""

    private lateinit var emailLogin: EditText
    private lateinit var passwordLogin: EditText
    private lateinit var emailRegister: EditText
    private lateinit var passwordRegister: EditText
    private lateinit var llAbout: LinearLayout
    private lateinit var llLoginRegister: LinearLayout
    private lateinit var llLogin: LinearLayout
    private lateinit var llRegister: LinearLayout
    private lateinit var btnRegister: AppCompatButton
    private lateinit var btnLogin: AppCompatButton
    private lateinit var btnGoLogin: AppCompatButton
    private lateinit var btnBackLogin: AppCompatButton
    private lateinit var btnGoRegister: AppCompatButton
    private lateinit var btnBackRegister: AppCompatButton
    private lateinit var clProfilePage: ConstraintLayout
    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var etName: EditText
    private lateinit var btnOpenFavorites: AppCompatButton
    private lateinit var btnLogOut: AppCompatButton
    private lateinit var tvSeeProfile: TextView
    private lateinit var tvSeeAbout: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initFirebase()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        if(AUTH.currentUser != null) {
            clProfilePage.visible()
            llAbout.gone()
            tvSeeProfile.gone()
            tvSeeAbout.visible()
        }

        setupClickListeners()

        tvEmail.text = AUTH.currentUser?.email.toString()

        name = binding.etName.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        emailLogin = binding.etEmailLogin
        passwordLogin = binding.etPasswordLogin
        emailRegister = binding.etEmailRegister
        passwordRegister = binding.etPasswordRegister
        llAbout = binding.llAbout
        llLoginRegister = binding.llLoginRegister
        llLogin = binding.llLogin
        llRegister = binding.llRegister
        btnRegister = binding.btnRegister
        btnLogin = binding.btnLogin
        btnGoLogin = binding.btnGoLogin
        btnBackLogin = binding.btnBackLogin
        btnGoRegister = binding.btnGoRegister
        btnBackRegister = binding.btnBackRegister
        clProfilePage = binding.clProfilePage
        ivAvatar = binding.ivAvatar
        tvName = binding.tvName
        tvEmail = binding.tvEmail
        etName = binding.etName
        btnOpenFavorites = binding.btnOpenFavorites
        btnLogOut = binding.btnLogOut
        tvSeeProfile = binding.tvSeeProfile
        tvSeeAbout = binding.tvSeeAbout
    }

    private fun setupClickListeners() {
        btnRegister.setOnClickListener {
            llLoginRegister.gone()
            llAbout.gone()
            llRegister.visible()
        }
        btnLogin.setOnClickListener {
            llLoginRegister.gone()
            llAbout.gone()
            llLogin.visible()
        }
        btnBackRegister.setOnClickListener {
            llRegister.gone()
            llAbout.visible()
            llLoginRegister.visible()
        }
        btnBackLogin.setOnClickListener {
            llLogin.gone()
            llAbout.visible()
            llLoginRegister.visible()
        }
        btnGoRegister.setOnClickListener {
            register()
        }
        btnGoLogin.setOnClickListener {
            login()
        }
        tvSeeAbout.setOnClickListener {
            clProfilePage.gone()
            llAbout.visible()
            llLoginRegister.gone()
            tvSeeProfile.visible()
        }
        tvSeeProfile.setOnClickListener {
            clProfilePage.visible()
            llAbout.gone()
            tvSeeProfile.gone()
            tvSeeAbout.visible()
        }
        btnLogOut.setOnClickListener {
            AUTH.signOut()
            clProfilePage.gone()
            llAbout.visible()
            llLoginRegister.visible()
        }
        btnOpenFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }
    }

    private fun register() {
        llRegister.visible()

        if (emailRegister.text.isEmpty() || passwordRegister.text.isEmpty()) {
            Toast.makeText(requireContext(), "Fill the fields", Toast.LENGTH_LONG).show()
        }
        val inputEmail = emailRegister.text.toString().trim()
        val inputPassword = passwordRegister.text.toString().trim()

        email = inputEmail
        tvEmail.text = email

        AUTH.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    llRegister.gone()
                    clProfilePage.visible()
                } else {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
    }

    private fun login() {
        llLogin.visible()

        if (emailLogin.text.isEmpty() || passwordLogin.text.isEmpty()) {
            Toast.makeText(requireContext(), "Fill the fields", Toast.LENGTH_LONG).show()
        }
        val inputEmail = emailLogin.text.toString().trim()
        val inputPassword = passwordLogin.text.toString().trim()

        email = inputEmail
        tvEmail.text = email

        AUTH.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    llLogin.gone()
                    clProfilePage.visible()
                } else {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
    }
}
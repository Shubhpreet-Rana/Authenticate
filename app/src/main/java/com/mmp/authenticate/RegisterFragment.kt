package com.mmp.authenticate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mmp.authenticate.databinding.FragmentRegisterBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val emailPttren: Pattern = Pattern.compile("\".+@.+\\\\.[a-z]+\"")
    private var mAuth: FirebaseAuth? = null
    private var mUser: FirebaseUser? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth?.currentUser
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            emailRegister.setOnClickListener {
                var Email = email.text.toString()
                var Password = password.text.toString()
                var ConfermPassword = confermPassword.text.toString()



                if (!isValid(Email)) {
                    email.setError("Please Enter Correct Email")


                } else if (Password.length < 6) {
                    Toast.makeText(context, Email + " " + Password, Toast.LENGTH_SHORT).show()
                    password.setError("Please Enter password . Character must be more than 6")
                    password.requestFocus();


                } else if (!Password.equals(ConfermPassword)) {
                    confermPassword.setError("Password Does't Match")
                }
                else{
                    mAuth?.createUserWithEmailAndPassword(Email, Password)?.addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(context, "Registration Complete", Toast.LENGTH_SHORT)
                                .show()
                        } else
                            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT)
                                .show()

                    }

                }
            }
        }
    }


    private fun isValid(email: String) : Boolean{
        var expression: String = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        var inputStr: CharSequence = email;
        var pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        var matcher: Matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        return false;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

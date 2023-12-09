package com.example.bancaria// com.example.bancaria.MainActivity.kt

// MainActivity.kt
import android.content.DialogInterface

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bancaria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIniciarSesion.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val contrasena = binding.etContrasena.text.toString()

            if (validarCredenciales(usuario, contrasena)) {
                iniciarSesion(usuario)
                mostrarDashboard()
            } else {
                mostrarMensajeError()
            }
        }
    }

    private fun validarCredenciales(usuario: String, contraseña: String): Boolean {
        // Aquí puedes implementar la lógica real de validación de credenciales
        return usuario == "usuario" && contraseña == "contraseña"
    }

    private fun iniciarSesion(usuario: String) {

    }

    private fun mostrarDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun mostrarMensajeError() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Error de inicio de sesión")
        builder.setMessage("Las credenciales ingresadas no son válidas. Por favor, inténtalo de nuevo.")

        builder.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        builder.show()
    }
}


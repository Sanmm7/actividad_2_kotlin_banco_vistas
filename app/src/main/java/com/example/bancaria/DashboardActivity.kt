package com.example.bancaria

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var btnRecargar: Button
    private lateinit var btnRetirar: Button
    private lateinit var btnTransferir: Button

    private var saldoActual = 5000 // ejemplo, debes obtener esto de tus datos reales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_layout)

        val tvSaldo: TextView = findViewById(R.id.tvSaldo)
        btnTransferir = findViewById(R.id.btnTransferir)
        btnRetirar = findViewById(R.id.btnRetirar)
        val btnCerrarSesion: Button = findViewById(R.id.btnCerrarSesion)

        actualizarSaldoEnVista()

        btnTransferir.setOnClickListener {
            mostrarDialogoTransferir()
        }

        btnRetirar.setOnClickListener {
            mostrarDialogoRetiro()
        }

        btnCerrarSesion.setOnClickListener {
            mostrarMensajeCerrarSesion()
        }

        btnRecargar = findViewById(R.id.btnRecargar)

        btnRecargar.setOnClickListener {
            mostrarDialogoRecarga()
        }
    }

    private fun mostrarMensajeCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                cerrarSesion()
                mostrarMensajeExito()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun cerrarSesion() {
        // Puedes agregar lógica para limpiar el estado de la sesión si es necesario
    }

    private fun mostrarMensajeExito() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sesión cerrada exitosamente")
            .setMessage("Tu sesión se cerró correctamente.")
            .setPositiveButton("Aceptar") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun mostrarDialogoRecarga() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recargar Saldo")
        val input = EditText(this)
        input.hint = "Ingrese la cantidad a recargar"
        builder.setView(input)

        builder.setPositiveButton("Recargar") { _: DialogInterface, _: Int ->
            val cantidadRecargar = input.text.toString().toIntOrNull()
            if (cantidadRecargar != null) {
                recargarSaldo(cantidadRecargar)
                mostrarMensajeRecargaExitosa()
            } else {
                mostrarMensajeErrorRecarga()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun recargarSaldo(cantidad: Int) {
        // Suma la cantidad ingresada al saldo actual
        saldoActual += cantidad
        actualizarSaldoEnVista()
    }

    private fun mostrarMensajeRecargaExitosa() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recarga exitosa")
            .setMessage("La recarga se realizó correctamente.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarMensajeErrorRecarga() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error en recarga")
            .setMessage("Por favor, ingrese una cantidad válida.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarDialogoRetiro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Retirar Saldo")
        val input = EditText(this)
        input.hint = "Ingrese la cantidad a retirar"
        builder.setView(input)

        builder.setPositiveButton("Retirar") { _: DialogInterface, _: Int ->
            val cantidadRetirar = input.text.toString().toIntOrNull()
            if (cantidadRetirar != null && cantidadRetirar <= saldoActual) {
                retirarSaldo(cantidadRetirar)
                mostrarMensajeRetiroExitoso()
            } else {
                mostrarMensajeErrorRetiro()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun retirarSaldo(cantidad: Int) {
        saldoActual -= cantidad
        actualizarSaldoEnVista()
    }

    private fun mostrarMensajeRetiroExitoso() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Retiro exitoso")
            .setMessage("El retiro se realizó correctamente.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarMensajeErrorRetiro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error en retiro")
            .setMessage("La cantidad a retirar no es válida o excede el saldo actual.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarDialogoTransferir() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Transferir Saldo")

        val inputCuenta = EditText(this)
        inputCuenta.hint = "Ingrese el número de cuenta del destinatario"
        val inputCantidad = EditText(this)
        inputCantidad.hint = "Ingrese la cantidad a transferir"

        builder.setView(inputCuenta)
        builder.setView(inputCantidad)

        builder.setPositiveButton("Transferir") { _: DialogInterface, _: Int ->
            val numeroCuenta = inputCuenta.text.toString()
            val cantidadTransferir = inputCantidad.text.toString().toIntOrNull()

            if (cantidadTransferir != null && cantidadTransferir <= saldoActual) {

                saldoActual -= cantidadTransferir
                actualizarSaldoEnVista()
                mostrarMensajeTransferenciaExitosa(numeroCuenta)
            } else {
                mostrarMensajeErrorTransferencia()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun mostrarMensajeTransferenciaExitosa(numeroCuenta: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Transferencia exitosa")
            .setMessage("Se ha transferido el saldo correctamente al número de cuenta: $numeroCuenta.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun mostrarMensajeErrorTransferencia() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error en transferencia")
            .setMessage("La cantidad a transferir no es válida o excede el saldo actual.")
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun actualizarSaldoEnVista() {
        val tvSaldo: TextView = findViewById(R.id.tvSaldo)
        tvSaldo.text = "Saldo: $$saldoActual"
    }
}

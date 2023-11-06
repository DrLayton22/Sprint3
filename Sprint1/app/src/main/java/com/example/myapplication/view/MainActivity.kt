package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private lateinit var nickNameEditText: EditText
    private lateinit var serverAddressEditText: EditText
    private lateinit var connectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a las vistas
        nickNameEditText = findViewById(R.id.nickNameText)
        serverAddressEditText = findViewById(R.id.serverAddressText)
        connectButton = findViewById(R.id.buttonConnect)

        // Configurar el botón Connect
        connectButton.setOnClickListener {
            val nickName = nickNameEditText.text.toString()
            val serverAddress = serverAddressEditText.text.toString()

            if (nickName.isNotEmpty()) {
                // Validar la dirección IP en un hilo secundario
                Thread {
                    if (isValidIPAddress(serverAddress)) {
                        runOnUiThread {
                            // Datos válidos, abrir la ventana de mensajes
                            openMessageWindow(nickName, serverAddress)
                        }
                    } else {
                        runOnUiThread {
                            // Datos no válidos, mostrar un mensaje de error
                            Snackbar.make(it, "Nombre de usuario no válido o dirección IP incorrecta", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }.start()
            } else {
                // Nombre de usuario vacío, mostrar un mensaje de error
                Snackbar.make(it, "Nombre de usuario no válido", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isValidIPAddress(ipAddress: String): Boolean {
        return try {
            val address = InetAddress.getByName(ipAddress)
            address is Inet4Address // Asegurarse de que sea una dirección IPv4
        } catch (e: UnknownHostException) {
            false
        }
    }

    private fun openMessageWindow(nickName: String, serverAddress: String) {
        // Crear un Intent para abrir la actividad MessagesWindow
        val intent = Intent(this, MessagesWindow::class.java)

        // Pasar los datos del nombre de usuario y la dirección del servidor al Intent
        intent.putExtra("nickName", nickName)
        intent.putExtra("serverAddress", serverAddress)

        // Iniciar la nueva actividad
        startActivity(intent)
    }

}

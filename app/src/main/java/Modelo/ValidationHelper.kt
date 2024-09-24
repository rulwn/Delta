package Modelo

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class ValidationHelper {

    fun validarContraseña(pass: String): String?{
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"

        return when {
            pass.isBlank() -> "La contraseña no puede estar vacía"
            !pass.matches(regex) -> "Error"
            else -> null
        }
    }

    fun validarNombreApellido(nombre: String): String? {
        val regex = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]*(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]*)*$".toRegex()
        return when {
            nombre.isBlank() -> "El nombre no puede estar vacío."
            nombre.contains("  ") -> "No se permiten dobles espacios."
            !nombre.matches(regex) -> "El nombre debe comenzar con mayúscula y no contener caracteres especiales."
            else -> null
        }
    }

    fun validarCorreo(correo: String): String? {
        // Expresión regular para aceptar correos más complejos con dominios de segundo nivel
        val regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?".toRegex()

        return when {
            correo.isBlank() -> "El correo no puede estar vacío."
            !correo.matches(regex) -> "El correo ingresado no es válido."
            else -> null
        }
    }

    fun validarTelefono(telefono: String): String? {
        val regex = "^\\d{4}-\\d{4}$".toRegex()
        return when {
            telefono.isBlank() -> "El teléfono no puede estar vacío."
            !telefono.matches(regex) -> "El teléfono debe tener el formato 1234-5678."
            else -> null
        }
    }

    //////////////////////////////////////////////////////



    fun setTextChangedCorreo(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().trim()

                // Validar formato
                val errorMessage = validarCorreo(input)
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }
            }
        })
    }

    fun setTextChangedTelefono(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private var oldText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                oldText = s.toString() // Guardamos el estado anterior del texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                var input = s.toString().replace("-", "") // Eliminamos cualquier guión para procesar el número limpio

                // Si estamos eliminando caracteres, permitimos borrar normalmente
                if (oldText.length > input.length) {
                    isUpdating = false
                    return
                }

                // Si el input tiene más de 4 caracteres, añadimos el guión después del cuarto carácter
                if (input.length > 4) {
                    input = input.substring(0, 4) + "-" + input.substring(4)
                }

                // Actualizamos el texto en el campo de entrada
                editText.setText(input)
                editText.setSelection(input.length) // Colocamos el cursor al final

                // Validar el formato del número
                val errorMessage = validarTelefono(input)
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }

                isUpdating = false
            }
        })
    }

    fun setTextChangedNombreApellido(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                val input = s.toString()
                var sanitizedInput = input

                // Bloquear doble espacio
                sanitizedInput = sanitizedInput.replace("\\s{2,}".toRegex(), " ")

                // Verificar si hay cambios
                if (input != sanitizedInput) {
                    editText.setText(sanitizedInput)
                    editText.setSelection(sanitizedInput.length)  // Coloca el cursor al final
                }

                // Validar el formato
                val errorMessage = validarNombreApellido(sanitizedInput.trim())
                if (errorMessage != null) {
                    editText.error = errorMessage
                    editText.requestFocus()
                }

                isUpdating = false
            }
        })
    }

}
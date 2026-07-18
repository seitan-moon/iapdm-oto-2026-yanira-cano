package com.example.registroempleados

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.registroempleados.ui.theme.RegistroEmpleadosTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// clase que representa un empleado
data class Empleado(
    val nombreCompleto: String,
    val cargo: String,
    val departamento: String,
    val salario: String,
    val fechaContratacion: String
)

// ViewModel: guarda la lista de empleados y sobrevive a cambios de configuracion
class EmpleadosViewModel : ViewModel() {

    private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
    val empleados: StateFlow<List<Empleado>> = _empleados.asStateFlow()

    fun agregarEmpleado(empleado: Empleado) {
        _empleados.update { listaActual -> listaActual + empleado }
    }

    fun eliminarEmpleado(empleado: Empleado) {
        _empleados.update { listaActual -> listaActual - empleado }
    }
}

private const val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroEmpleadosTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Registro de Empleados") },
                            navigationIcon = {
                                Icon(
                                    Icons.Filled.Groups,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { paddingInterno ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingInterno)
                    ) {
                        PantallaPrincipal()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }
}

@Composable
fun PantallaPrincipal(empleadosViewModel: EmpleadosViewModel = viewModel()) {
    val empleados by empleadosViewModel.empleados.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            FormularioEmpleado(alAgregar = { empleadosViewModel.agregarEmpleado(it) })
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(empleados, key = { it.hashCode() }) { empleado ->
            ItemEmpleado(empleado, alEliminar = { empleadosViewModel.eliminarEmpleado(it) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FormularioEmpleado(alAgregar: (Empleado) -> Unit) {
    var nombreCompleto by rememberSaveable { mutableStateOf("") }
    var cargo by rememberSaveable { mutableStateOf("") }
    var departamento by rememberSaveable { mutableStateOf("") }
    var salario by rememberSaveable { mutableStateOf("") }
    var fechaContratacion by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = nombreCompleto, onValueChange = { nombreCompleto = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cargo, onValueChange = { cargo = it },
                label = { Text("Cargo") },
                leadingIcon = { Icon(Icons.Filled.Badge, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = departamento, onValueChange = { departamento = it },
                label = { Text("Departamento") },
                leadingIcon = { Icon(Icons.Filled.People, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = salario, onValueChange = { salario = it },
                label = { Text("Salario") },
                leadingIcon = { Icon(Icons.Filled.AttachMoney, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fechaContratacion, onValueChange = { fechaContratacion = it },
                label = { Text("Fecha de contratacion") },
                leadingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    alAgregar(
                        Empleado(nombreCompleto, cargo, departamento, salario, fechaContratacion)
                    )
                    nombreCompleto = ""; cargo = ""; departamento = ""
                    salario = ""; fechaContratacion = ""
                },
                enabled = nombreCompleto.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Empleado")
            }
        }
    }
}

@Composable
fun ItemEmpleado(empleado: Empleado, alEliminar: (Empleado) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            // nombre destacado arriba
            Text(empleado.nombreCompleto, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // demas datos en fila horizontal con LazyRow
            val datos = listOf(
                "Cargo: ${empleado.cargo}",
                "Departamento: ${empleado.departamento}",
                "Salario: ${empleado.salario}",
                "Contratado: ${empleado.fechaContratacion}"
            )
            LazyRow {
                items(datos) { dato ->
                    AssistChip(onClick = {}, label = { Text(dato) })
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // boton de eliminacion abajo del todo
            Button(onClick = { alEliminar(empleado) }) {
                Text("Eliminar")
            }
        }
    }
}
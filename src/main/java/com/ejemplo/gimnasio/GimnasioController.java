package com.ejemplo.gimnasio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class GimnasioController {

    private List<Miembro> miembros;
    private Map<String, String> horarios;

    public GimnasioController() {

        miembros = new ArrayList<>();

        miembros.add(new Miembro("Carlos", 30));
        miembros.add(new MiembroPremium("Ana", 25, "Andrés"));
        miembros.add(new MiembroEstudiante("Laura", 20, "Universidad Nacional"));
        miembros.add(new MiembroPremium("Pedro", 35, "Camila"));

        horarios = new HashMap<>();

        horarios.put("yoga", "Lunes y miércoles - 8:00 a. m.");
        horarios.put("spinning", "Martes y jueves - 6:00 p. m.");
        horarios.put("crossfit", "Lunes, miércoles y viernes - 7:00 p. m.");
    }

    // Pasi 1
    @GetMapping("/miembros")
    public List<Miembro> listarMiembros() {
        return miembros;
    }

    // Paso 2
    @GetMapping("/miembros/buscar/{nombre}")
    public String buscarMiembro(@PathVariable String nombre) {

        for (Miembro miembro : miembros) {

            if (miembro.getNombre().equalsIgnoreCase(nombre)) {
                return miembro.descripcion()
                        + " - cuota mensual: $"
                        + miembro.cuotaMensual();
            }
        }

        return "No se encontró al miembro " + nombre;
    }

    // Paso 3
    @GetMapping("/miembros/cuota/{minima}")
    public List<Miembro> buscarPorCuota(@PathVariable double minima) {

        List<Miembro> resultado = new ArrayList<>();

        for (Miembro miembro : miembros) {

            if (miembro.cuotaMensual() >= minima) {
                resultado.add(miembro);
            }
        }

        return resultado;
    }

    // Paso 4
    @GetMapping("/clases/{nombre}")
    public String consultarHorario(@PathVariable String nombre) {

        String clave = nombre.toLowerCase(Locale.ROOT);
        String horario = horarios.get(clave);

        if (horario != null) {
            return horario;
        }

        return "Clase no disponible";
    }
}

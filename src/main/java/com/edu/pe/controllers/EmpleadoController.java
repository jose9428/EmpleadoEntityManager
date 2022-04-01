package com.edu.pe.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edu.pe.dao.IEmpleadoDao;
import com.edu.pe.models.Empleado;

@Controller
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoDao empleadoDao;
	
	@GetMapping("/")
	public String listar(Model model) {
		List<Empleado> lista = empleadoDao.ListProc(2000);
		System.out.println(lista.toString());
		//for(Empleado e: lista) {
			//System.out.println(e);
	//	}
		
		//System.out.println(lista.get(0).getCorreo());
		model.addAttribute("empleados",empleadoDao.findAll());
		return "index";		
	}
	
	@GetMapping("/nuevo")
	public String Nuevo(Model model) {
		model.addAttribute("empleado",new Empleado());
		return "nuevo";		
	}
	
	@PostMapping("/guardar")
	public String Guardar(@ModelAttribute Empleado e , RedirectAttributes attributes) {
		if(e.getId_empleado() == 0) {
			e.setFecha_registro(Calendar.getInstance());
			empleadoDao.Guardar(e);
		}else {
			empleadoDao.Actualizar(e);
		}
		attributes.addFlashAttribute("success", "Los datos del empleado se guardaron correctamente.");

		return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public String Editar(Model model , @PathVariable("id") int id , RedirectAttributes attributes) {

		Empleado e = empleadoDao.BuscarPorId(id);
		
		if(e != null) {
			model.addAttribute("titulo", "Editar");
			model.addAttribute("empleado",e);
			return "nuevo";	
		}else {
			attributes.addFlashAttribute("danger", "No se encontraron datos con el id "+id);
			return "redirect:/";
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String Eliminar(Model model , @PathVariable("id") int id , RedirectAttributes attributes) {
		try {
			empleadoDao.Eliminar(id);
			attributes.addFlashAttribute("success", "El empleado con el id "+id+" se eliminaron correctamente.");
		}catch(Exception ex) {
			attributes.addFlashAttribute("danger", "No se pueden eliminar datos con el id "+id);
			ex.printStackTrace();
		}
		return "redirect:/";
	}
	
	@ModelAttribute
	public void CargarDatos(Model model , Empleado e) {
		model.addAttribute("titulo", e.getId_empleado() == 0 ? "Nuevo": "Editar");
		model.addAttribute("generos", new String[] {"Femenino","Masculino" , "Otro"});
	}
}

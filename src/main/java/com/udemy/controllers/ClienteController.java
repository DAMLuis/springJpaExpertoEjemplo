package com.udemy.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udemy.entity.Cliente;
import com.udemy.service.IClienteService;
import com.udemy.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	

	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String list(@RequestParam(name="page" , defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page<Cliente> clientes = clienteService.findAllPage(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo","listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page",pageRender);
		return "listar";
	}
	
	/**
	 * formulario de crear un cliente
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		return "form";
	}
	
	/**
	 * Formulario editar cliente
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model,RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if(id>0) {
			cliente= clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error","el id del cliente no existe en la Base de datos ");
				return "redirect:/listar";
			}
			
		}else {
			flash.addFlashAttribute("error", "Cliente con ese ID no existe ");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result,Model model,@RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo","formulario de cliente");
			return "form";
		}
		if(!foto.isEmpty()) {
			Path directorioRecursosPath = Paths.get("src//main//resources//static/uploads");
			String rootPath= directorioRecursosPath.toFile().getAbsolutePath();
			
			try {
				byte[] bytes=foto.getBytes();
				Path rutaCompletaPath = Paths.get(rootPath + "//" + foto.getOriginalFilename() );
				Files.write(rutaCompletaPath, bytes);
				flash.addFlashAttribute("info","has subido corretamente " + foto.getOriginalFilename());
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		}
		
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con exito" : "Cliente creado con exito";
		
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("sucess", mensajeFlash);
		return "redirect:listar";
	}
	
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id,RedirectAttributes flash) {
		
		if(id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("sucess", "Cliente  eliminado con exito ");
		}
		
		return "redirect:/listar";
		
	}
	
	
	
	
}

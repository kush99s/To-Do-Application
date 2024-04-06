package com.example.ToDoApp.controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ToDoApp.model.ToDo;
import com.example.ToDoApp.service.toDoService;


@Controller
public class toDoController {

	@Autowired
	toDoService service;
	
	@GetMapping({"/","viewList"})
	public String viewAllItems(Model model, @ModelAttribute("message") String message) {
		
		model.addAttribute("list",service.getAllToDoItems());
		model.addAttribute("msg",message);
		
		return "ViewToDoList";
	}
	
	@GetMapping("/updateSatus/{id}")
	public String updateStatus(@PathVariable Long id, RedirectAttributes redirect) {
		
		if(service.updateStatus(id)) {
			redirect.addFlashAttribute("message", "Update success");
			return "redirect:/viewList";
		}
		
		redirect.addFlashAttribute("message", "Update failure");
		return "";
	}
	
	@GetMapping("/addItem")
	public String addItem(Model model) {
		
		model.addAttribute("todo",new ToDo());
		return "AddToDoItem";
	}
	
	@PostMapping("/saveItem")
	public String saveItem(ToDo todo, RedirectAttributes redirect) {
		
		if(service.saveOrUpdateToDoItem(todo)) {	
			redirect.addFlashAttribute("message","Save success");
			return "redirect:/viewList";			
		}
			
		redirect.addFlashAttribute("message","Save failure");
		return "redirect:/addItem";
	}
	
	@GetMapping("/editItem/{id}")
	public String editItem(@PathVariable Long id, Model model) {
		
		model.addAttribute("todo",service.getToDoItembyId(id));
		return "EditToDoItem";
	}
	
	@PostMapping("/editSave")
	public String editSaveItem(ToDo todo, RedirectAttributes redirect) {
		
		if(service.saveOrUpdateToDoItem(todo)) {	
			redirect.addFlashAttribute("message","Edit success");
			return "redirect:/viewList";			
		}
			
		redirect.addFlashAttribute("message","Edit failure");
		return "redirect:/editItem/"+ todo.getId();
	}
	
	@GetMapping("/deleteItem/{id}")
	public String deleteItem(@PathVariable Long id, RedirectAttributes redirect) {
		
		if(service.deleteToDoItem(id)) {
			redirect.addFlashAttribute("message","Delete success");
			return "redirect:/viewList";
		}
		
		redirect.addFlashAttribute("message","Delete failure");
		return "redirect:/viewList";
	}
}

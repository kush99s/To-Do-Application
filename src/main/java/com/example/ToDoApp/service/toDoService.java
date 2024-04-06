package com.example.ToDoApp.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ToDoApp.model.ToDo;
import com.example.ToDoApp.repo.ToDoRepo;

@Service
public class toDoService {
	
	@Autowired
	ToDoRepo repo;
	
	public List<ToDo> getAllToDoItems(){
		List<ToDo> todoList = new ArrayList<>();
		
		repo.findAll().forEach(todo -> todoList.add(todo));
		return todoList;
	}
	
	public ToDo getToDoItembyId(Long id){
		return repo.findById(id).get();
	}
	
	public boolean updateStatus(Long id){
		ToDo todo = getToDoItembyId(id);
		todo.setStatus("Completed");
		
		return saveOrUpdateToDoItem(todo);
	}
	
	public boolean saveOrUpdateToDoItem(ToDo todo){
		ToDo updated = repo.save(todo);
		if(getToDoItembyId(updated.getId())!=null) {
			return true;
		}
		
		return false;
	}
	
	public boolean deleteToDoItem(Long id){
		repo.deleteById(id);
		
		if(repo.findById(id)==null)
			return true;
		
		return false;
	}

}

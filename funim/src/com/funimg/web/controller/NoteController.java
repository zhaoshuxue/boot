package com.funimg.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funimg.web.entity.Note;
import com.funimg.web.entity.Notebook;
import com.funimg.web.service.NoteService;
/**
 * 记事本
 *
 */
@Controller
@RequestMapping("note")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	
	/**
	 * 
	 */
	@RequestMapping("notebook")
	public String toNotebookList(HttpServletRequest request, HttpServletResponse response){
		List<Notebook> notebook = noteService.getNotebookList();
		List<Note> note = noteService.getNoteListByBookId("1");
		request.setAttribute("notebook", notebook);
		request.setAttribute("note", note);
		
		return "jsp/note/notebook";
	}
	
	
	
}

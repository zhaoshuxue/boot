package com.funimg.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funimg.web.dao.NoteDao;
import com.funimg.web.dao.NotebookDao;
import com.funimg.web.entity.Note;
import com.funimg.web.entity.Notebook;
import com.funimg.web.service.NoteService;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NotebookDao	notebookDao;
	@Autowired
	private NoteDao noteDao;
	
	public List<Notebook> getNotebookList(){
		return notebookDao.find("from Notebook p order by p.addTime desc ");
	}

	@Override
	public List<Note> getNoteListByBookId(String bookId) {
		return noteDao.find("from Note n where n.bookId = '" + bookId + "'");
	}
	



}

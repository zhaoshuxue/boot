package com.funimg.web.service;

import java.util.List;

import com.funimg.web.entity.Note;
import com.funimg.web.entity.Notebook;



/**
 * 记事本接口
 */
public interface NoteService {
	
	List<Notebook> getNotebookList();
	
	List<Note> getNoteListByBookId(String bookId);
}

package com.example.myroompersistence.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val noteDatabase = NoteDatabase.getDatabase(application)
    private val noteDAO = noteDatabase.NoteDao()

    private val _allnotes = MutableLiveData<List<Note>>()
    val allNote : LiveData<List<Note>> = _allnotes

    init {
        fetchNotes()
    }
    private fun fetchNotes(){
        viewModelScope.launch {
            try {
                val notes = noteDAO.getAllNotes()
                _allnotes.postValue(notes)
            } catch (e: Exception) {
                _allnotes.postValue(emptyList())
            }
        }
    }
}
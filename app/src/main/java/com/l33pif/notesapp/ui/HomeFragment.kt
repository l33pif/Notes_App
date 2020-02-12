package com.l33pif.notesapp.ui


import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.l33pif.notesapp.R
import com.l33pif.notesapp.db.Note
import com.l33pif.notesapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_note.setHasFixedSize(true)
        recycler_view_note.layoutManager = GridLayoutManager(context, 2 )

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recycler_view_note.adapter = NotesAdapter(notes)
            }

        }

        btn_add.setOnClickListener{
            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

}

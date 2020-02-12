package com.l33pif.notesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.l33pif.notesapp.R
import com.l33pif.notesapp.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    var isClickable = true

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        )

    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.view.date_text.text = notes[position].date
        holder.view.title_text.text = notes[position].title
        holder.view.desc_text.text = notes[position].note



        if(isClickable == true){

            holder.view.setOnClickListener{

                val action = HomeFragmentDirections.actionAddNote()
                action.note = notes[position]
                Navigation.findNavController(it).navigate(action)

            }
        }





    }


}
package com.l33pif.notesapp.ui


import android.app.AlertDialog
import android.app.DatePickerDialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import androidx.navigation.Navigation

import com.l33pif.notesapp.R
import com.l33pif.notesapp.db.Note
import com.l33pif.notesapp.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note_frgment.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */

class AddNoteFrgment : BaseFragment() {

    private var note: Note? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note_frgment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            note = AddNoteFrgmentArgs.fromBundle(it).note
            edit_title.setText(note?.title)
            edit_note.setText(note?.note)
            PlannedDate.setOnClickListener {
                showDatePickerDialog()
            }

        }





        btn_save.setOnClickListener{view ->

            val noteTitle = edit_title.text.toString().trim()
            val noteBody = edit_note.text.toString().trim()
            val noteDate = PlannedDate.text.toString().trim()

            if(noteTitle.isEmpty()){
                edit_title.error = "Title is required"
                edit_title.requestFocus()
                return@setOnClickListener
            }

            if(noteBody.isEmpty()){
                edit_note.error = "The note is required"
                edit_note.requestFocus()
                return@setOnClickListener
            }

            if(noteDate.isEmpty()){
                PlannedDate.error = "The date is required"
                PlannedDate.requestFocus()
                return@setOnClickListener
            }


            launch {

                context?.let {
                    val mnote = Note(noteTitle, noteBody, noteDate)

                    if (note == null){
                        NoteDatabase(it).getNoteDao().addNote(mnote)
                        it.toast("Note Saved")
                    }else
                    {
                        mnote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mnote)
                        it.toast("Note Updated")
                    }

                    val action = AddNoteFrgmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }



        }
    }


    private fun deleteNote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){ _,_ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFrgmentDirections.actionSaveNote()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No"){_,_ ->

            }

        }.create().show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Delete -> if (note != null) deleteNote() else context?.toast("Cannot delete")

        }


        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

    }

     fun showDatePickerDialog() {
        var formate = SimpleDateFormat("dd, MMM, YYYY", Locale.US)
        val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                var odate = formate.format(selectedDate.time).toString()
                PlannedDate.setText(odate)
            },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

    }

}

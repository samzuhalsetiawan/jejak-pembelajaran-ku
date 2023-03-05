package com.example.latihanarchitecturecomponent

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanarchitecturecomponent.adapter.NoteListAdapter
import com.example.latihanarchitecturecomponent.databinding.ActivityMainBinding
import com.example.latihanarchitecturecomponent.models.Note
import com.example.latihanarchitecturecomponent.viewmodel.NoteViewModel

const val TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "com.example.latihanarchitecturecomponent.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.latihanarchitecturecomponent.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.example.latihanarchitecturecomponent.EXTRA_PRIORITY"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        noteListAdapter = NoteListAdapter()
        setContentView(binding.root)

        addNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                activityResult.data?.let { saveNoteFromIntentResult(it) }
            } else {
                Toast.makeText(this, "Gagal Membuat Note", Toast.LENGTH_SHORT).show()
            }
        }
        setupRecyclerView()
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.getAllNote().observe(this) { noteListAdapter.noteList = it }
        binding.fabAddNote.setOnClickListener { fabClickHandler() }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteListAdapter.getNoteAtPosition(viewHolder.adapterPosition)
                Log.d(TAG, "Position: ${viewHolder.adapterPosition}")
                Log.d(TAG, "Title: ${note?.title}")

                if (note != null) {
                    noteViewModel.deleteNote(note)
                    Toast.makeText(this@MainActivity, "Note Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }).attachToRecyclerView(binding.rvNoteList)
    }

    private fun saveNoteFromIntentResult(intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "ERR: NO TITLE"
        val description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: "ERR: NO DESCRIPTION"
        val priority = intent.getIntExtra(EXTRA_PRIORITY, 1)
        val note = Note(id = null, title, priority, description)
        noteViewModel.insertNote(note)
        Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
    }

    private fun fabClickHandler() {
        addNoteLauncher.launch(Intent(this, AddNoteActivity::class.java))
    }

    private fun setupRecyclerView() {
        binding.rvNoteList.adapter = noteListAdapter
        binding.rvNoteList.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuDeleteAllNote -> {
                noteViewModel.deleteAllNote()
                true
            }
            else -> false
        }
    }
}
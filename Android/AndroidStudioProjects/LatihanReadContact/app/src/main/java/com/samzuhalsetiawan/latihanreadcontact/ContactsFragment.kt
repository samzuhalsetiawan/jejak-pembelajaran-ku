package com.samzuhalsetiawan.latihanreadcontact

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

private val FROM_COLUMNS = arrayOf(
    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
)
private val TO_IDS = intArrayOf(
    android.R.id.text1
)
private val PROJECTION = arrayOf(
    ContactsContract.Contacts._ID,
    ContactsContract.Contacts.LOOKUP_KEY,
    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
)
// The column index for the _ID column
private const val CONTACT_ID_INDEX: Int = 0
// The column index for the CONTACT_KEY column
private const val CONTACT_KEY_INDEX: Int = 1
private val SELECTION = "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"

class ContactsFragment() :
    Fragment(R.layout.fragment_contacts),
    LoaderManager.LoaderCallbacks<Cursor>,
    AdapterView.OnItemClickListener {

    lateinit var contactsList: ListView
    var contactId: Long = 0
    var contactKey: String? = null
    var contactUri: Uri? = null
    var cursorAdapter: SimpleCursorAdapter? = null

    // Defines a variable for the search string
    private val searchString: String = "%Sam%"
    // Defines the array to hold values that replace the ?
    private val selectionArgs = arrayOf<String>(searchString)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loaderManager.initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
//        selectionArgs[0] = "%$mSearchString%"
        // Starts the query
        return activity?.let {
            return CursorLoader(
                it,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                null
            )
        } ?: throw IllegalStateException()
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        cursorAdapter?.swapCursor(null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.also {
            contactsList = it.findViewById(R.id.lvFragmentContacts)

            cursorAdapter = SimpleCursorAdapter(
                it,
                R.layout.contacts_list,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0
            )

            contactsList.adapter = cursorAdapter
            contactsList.onItemClickListener = this

        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val cursor = (parent?.adapter as CursorAdapter).cursor.apply {
            moveToPosition(position)
            contactId = getLong(CONTACT_ID_INDEX)
            contactKey = getString(CONTACT_KEY_INDEX)
            contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey)
            Toast.makeText(context, contactKey, Toast.LENGTH_SHORT).show()
        }
    }
}
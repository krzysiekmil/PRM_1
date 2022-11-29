package pjwstk.s20124.prm_1.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModel


class DataProvider : ContentProvider() {

    private val PROVIDER_NAME = "pjwstk.s20124.prm_1.providers"
    private val URL = "content://$PROVIDER_NAME/expanses"
    private val URI_CODE = 1
    private val CONTENT_NAME = "USER_EXPENSE"

    private lateinit var uriMatcher: UriMatcher
    public lateinit var viewModel: UserExpenseViewModel

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(PROVIDER_NAME, CONTENT_NAME, URI_CODE)
        return true
    }


    @Synchronized
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val code = uriMatcher.match(uri)

        if (code != URI_CODE) {
            throw IllegalArgumentException("Unknown URI: $uri")
        }

        val cursor: Cursor = viewModel.cursor
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    @Synchronized
    override fun getType(uri: Uri): String {
        val code = uriMatcher.match(uri)

        if (code != URI_CODE) {
            throw IllegalArgumentException("Unknown URI: $uri")
        } else {
            return "vnd.android.cursor.dir/$PROVIDER_NAME.$CONTENT_NAME"
        }
    }

    @Synchronized
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.i(DataProvider::PROVIDER_NAME.toString(), "Operation on data is not allowed")
        return null
    }


    @Synchronized
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.i(DataProvider::PROVIDER_NAME.toString(), "Operation on data is not allowed")

        return -1
    }

    @Synchronized
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.i(DataProvider::PROVIDER_NAME.toString(), "Operation on data is not allowed")

        return -1
    }
}
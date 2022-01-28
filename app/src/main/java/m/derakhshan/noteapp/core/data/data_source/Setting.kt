package m.derakhshan.noteapp.core.data.data_source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Setting @Inject constructor(
    @ApplicationContext context: Context
) {

    private val share = context.getSharedPreferences("share_preferences", Context.MODE_PRIVATE)
    private val edit = share.edit()

    val lastNoteId: Int
        get() {
            var id = share.getInt("lastNoteId", 0)
            id++
            edit.putInt("lastNoteId", id)
            edit.apply()
            return id
        }

    var isUserLoggedIn: Boolean
        set(value) {
            edit.putBoolean("is_logged_in", value)
            edit.apply()
        }
        get() = share.getBoolean("is_logged_in", false)


}
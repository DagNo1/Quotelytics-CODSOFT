package codsoft.dagno1.quotelytics.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.InputStream
import kotlin.random.Random


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private val inputStream: InputStream = context.assets.open("data.sql")

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                CONTENT + " TEXT," +
                AUTHOR + " TEXT" + ")")
        db.execSQL(query)


        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val sqlCommands = String(buffer)

        val commands = sqlCommands.split("\n")
        for (command in commands) {
            db.execSQL(command.trim())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addName(name: String, age: String) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(CONTENT, name)
        values.put(AUTHOR, age)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun getQuote(): Quote {
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor!!.moveToFirst()
        return Quote(
            content = cursor.getString(cursor.getColumnIndex(CONTENT)),
            author = cursor.getString(cursor.getColumnIndex(AUTHOR))
        )
    }

    @SuppressLint("Range", "Recycle")
    fun getRandomQuote(): Quote {
        val db = this.readableDatabase
        val randomNumber = Random.nextInt(1, 1001)

        val sqlQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = ?"

        val selectionArgs = arrayOf(randomNumber.toString())

        val cursor = db.rawQuery(sqlQuery, selectionArgs)

        cursor!!.moveToFirst()
        return Quote(
            content = cursor.getString(cursor.getColumnIndex(CONTENT)),
            author = cursor.getString(cursor.getColumnIndex(AUTHOR))
        )
    }

    companion object {
        private const val DATABASE_NAME = "Daily_Quote_App"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "quotes"
        const val ID = "id"
        const val CONTENT = "content"
        const val AUTHOR = "author"
    }
}
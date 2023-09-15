package codsoft.dagno1.quotelytics.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.InputStream


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private val inputStream: InputStream = context.assets.open("data.sql")

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                CONTENT + " TEXT," +
                AUTHOR + " TEXT," +
                IS_READ + " INT DEFAULT 0," +
                IS_FAVORITE + " INT DEFAULT 0," +
                FAVORITE_ORDER + " INT DEFAULT -1" + ")")
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
            id = cursor.getInt(cursor.getColumnIndex(ID)),
            content = cursor.getString(cursor.getColumnIndex(CONTENT)),
            author = cursor.getString(cursor.getColumnIndex(AUTHOR)),
            isRead = cursor.getInt(cursor.getColumnIndex(IS_READ)) == 1,
            isFavorite = cursor.getInt(cursor.getColumnIndex(IS_FAVORITE)) == 1,
            favoriteOrder = cursor.getInt(cursor.getColumnIndex(FAVORITE_ORDER))
        )
    }

    @SuppressLint("Range", "Recycle")
    fun getRandomUnreadQuote(): Quote {
        val db = this.readableDatabase

        val sqlQuery = "SELECT * FROM $TABLE_NAME WHERE $IS_READ = 0 ORDER BY RANDOM() LIMIT 1"

        val cursor = db.rawQuery(sqlQuery, null)

        if (cursor.moveToFirst()) {
            return Quote(
                id = cursor.getInt(cursor.getColumnIndex(ID)),
                content = cursor.getString(cursor.getColumnIndex(CONTENT)),
                author = cursor.getString(cursor.getColumnIndex(AUTHOR))
            )
        }

        return Quote(-1, "All Quotes Read", "Dagim")
    }

    fun markQuoteAsRead(quoteId: Int): Boolean {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(IS_READ, 1)
        val whereClause = "$ID = ?"
        val whereArgs = arrayOf(quoteId.toString())

        val rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs)
        return rowsUpdated > 0
    }

    fun markQuoteAsFavorite(quoteId: Int): Boolean {
        val db = this.writableDatabase

        val maxFavoriteOrderQuery = "SELECT MAX($FAVORITE_ORDER) FROM $TABLE_NAME"
        val cursor = db.rawQuery(maxFavoriteOrderQuery, null)
        var maxFavoriteOrder = 0
        if (cursor.moveToFirst()) {
            maxFavoriteOrder = cursor.getInt(0)
        }
        val newFavoriteOrder = maxFavoriteOrder + 1

        val values = ContentValues()
        values.put(IS_FAVORITE, 1)
        values.put(FAVORITE_ORDER, newFavoriteOrder)
        val whereClause = "$ID = ?"
        val whereArgs = arrayOf(quoteId.toString())

        val rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs)
        return rowsUpdated > 0
    }

    fun markQuoteAsNotFavorite(quoteId: Int): Boolean {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(IS_FAVORITE, 0)
        val whereClause = "$ID = ?"
        val whereArgs = arrayOf(quoteId.toString())

        val rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs)
        return rowsUpdated > 0
    }

    @SuppressLint("Range")
    fun getFavoriteQuotesSorted(): List<Quote> {
        val favoriteQuotes = mutableListOf<Quote>()
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME WHERE $IS_FAVORITE = 1 ORDER BY $FAVORITE_ORDER DESC"

        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val content = cursor.getString(cursor.getColumnIndex(CONTENT))
                val author = cursor.getString(cursor.getColumnIndex(AUTHOR))
                val isRead = cursor.getInt(cursor.getColumnIndex(IS_READ)) == 1
                val isFavorite = cursor.getInt(cursor.getColumnIndex(IS_FAVORITE)) == 1
                val favoriteOrder = cursor.getInt(cursor.getColumnIndex(FAVORITE_ORDER))

                val quote = Quote(id, content, author, isRead, isFavorite, favoriteOrder)
                favoriteQuotes.add(quote)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return favoriteQuotes
    }

    @SuppressLint("Range")

    fun searchQuotes(search: String): List<Quote> {
        val db: SQLiteDatabase = this.readableDatabase

        val query = "SELECT * FROM $TABLE_NAME WHERE $CONTENT LIKE ? OR $AUTHOR LIKE ?"
        val selectionArgs = arrayOf("%$search%","%$search%")

        val cursor: Cursor = db.rawQuery(query, selectionArgs)

        val quotes = mutableListOf<Quote>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val content = cursor.getString(cursor.getColumnIndex(CONTENT))
                val author = cursor.getString(cursor.getColumnIndex(AUTHOR))
                val isRead = cursor.getInt(cursor.getColumnIndex(IS_READ)) == 1
                val isFavorite = cursor.getInt(cursor.getColumnIndex(IS_FAVORITE)) == 1
                val favoriteOrder = cursor.getInt(cursor.getColumnIndex(FAVORITE_ORDER))
Log.d("here", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 1 $author")
                val quote = Quote(
                    id = id,
                    content = content,
                    author = author,
                    isRead = isRead,
                    isFavorite = isFavorite,
                    favoriteOrder = favoriteOrder
                )
                quotes.add(quote)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return quotes
    }


    companion object {
        private const val DATABASE_NAME = "Daily_Quote_App"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "quotes"
        const val ID = "id"
        const val CONTENT = "content"
        const val AUTHOR = "author"
        const val IS_READ = "is_read"
        const val IS_FAVORITE = "is_favorite"
        const val FAVORITE_ORDER = "favorite_rank"
    }
}
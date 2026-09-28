package com.unir.appcamil.room

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performBlocking
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room3.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL", "MemberExtensionConflict"])
internal class ReviewDAO_Impl(
  __db: RoomDatabase,
) : ReviewDAO {
  private val __db: RoomDatabase

  private val __insertAdapterOfReview: EntityInsertAdapter<Review>

  private val __deleteAdapterOfReview: EntityDeleteOrUpdateAdapter<Review>
  init {
    this.__db = __db
    this.__insertAdapterOfReview = object : EntityInsertAdapter<Review>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `local_reviews` (`id`,`titulo`,`nota`,`review`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Review) {
        statement.bindLong(1, entity.id.toLong())
        val _tmpTitulo: String? = entity.titulo
        if (_tmpTitulo == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpTitulo)
        }
        statement.bindDouble(3, entity.nota.toDouble())
        val _tmpReview: String? = entity.review
        if (_tmpReview == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpReview)
        }
      }
    }
    this.__deleteAdapterOfReview = object : EntityDeleteOrUpdateAdapter<Review>() {
      protected override fun createQuery(): String = "DELETE FROM `local_reviews` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Review) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
  }

  public override fun inserir(review: Review?): Unit = performBlocking(__db, false, true) { _connection ->
    __insertAdapterOfReview.insert(_connection, review)
  }

  public override fun deletar(review: Review?): Unit = performBlocking(__db, false, true) { _connection ->
    __deleteAdapterOfReview.handle(_connection, review)
  }

  public override fun obterTodas(): MutableList<Review?>? {
    val _sql: String = "SELECT * FROM local_reviews"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitulo: Int = getColumnIndexOrThrow(_stmt, "titulo")
        val _columnIndexOfNota: Int = getColumnIndexOrThrow(_stmt, "nota")
        val _columnIndexOfReview: Int = getColumnIndexOrThrow(_stmt, "review")
        val _result: MutableList<Review?> = mutableListOf()
        while (_stmt.step()) {
          val _item: Review?
          _item = Review()
          _item.id = _stmt.getLong(_columnIndexOfId).toInt()
          if (_stmt.isNull(_columnIndexOfTitulo)) {
            _item.titulo = null
          } else {
            _item.titulo = _stmt.getText(_columnIndexOfTitulo)
          }
          _item.nota = _stmt.getDouble(_columnIndexOfNota).toFloat()
          if (_stmt.isNull(_columnIndexOfReview)) {
            _item.review = null
          } else {
            _item.review = _stmt.getText(_columnIndexOfReview)
          }
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredColumnConverters(): List<KClass<*>> = emptyList()

    public fun getRequiredDaoReturnTypeConverters(): List<KClass<*>> = emptyList()
  }
}

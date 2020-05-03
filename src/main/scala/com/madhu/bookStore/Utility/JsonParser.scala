package com.madhu.bookStore.Utility

import com.google.gson.Gson
import com.madhu.bookStore.Model.Book

object JsonParser {
  def objectToJson(obj: Any): String = new Gson().toJson(obj)

  def jsonToBook(response: String): Book = new Gson().fromJson(response, classOf[Book])
}
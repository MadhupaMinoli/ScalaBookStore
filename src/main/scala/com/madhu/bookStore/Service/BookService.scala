package com.madhu.bookStore.Service

import com.google.gson.Gson
import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Repository.BookRepository


object BookService {



  def getAllBooks():String={
    new Gson().toJson(BookRepository.getAllBooks.values.toArray)

  }

  def searchByISBN(isbn:Long):Option[Book]={
    BookRepository.searchByISBN(isbn)
  }

  def insertBook(b:Book):Unit={
    BookRepository.insertBook(b)
  }


}

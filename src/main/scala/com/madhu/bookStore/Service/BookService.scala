package com.madhu.bookStore.Service

import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Repository.BookRepository

import scala.collection.mutable

object BookService {

  def addBook(b:Book):Any={
    BookRepository.addBook(b)
  }
  def getAllBooks():Unit={
    BookRepository.getAllBooks
  }

}

package com.madhu.bookStore.Repository

import com.madhu.bookStore.Model.Book

import scala.collection.mutable

object BookRepository {
  private val bookList = mutable.HashMap[Int, Book]();
  private var id: Int = 0;

  def getAllBooks:Unit = {
    bookList
    println(bookList)
  }

  def addBook(book:Book):mutable.HashMap[Int,Book]={
    id+=1
    bookList+=(id->book)
  }




}

package com.madhu.bookStore

import com.google.gson.Gson
import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Service.BookService

object Main {
  def main(args: Array[String]):Unit = {

//    val b1 =Book(9783161484100L, "HarryPotter", "JK Rowling")
//    val b2 =Book(9781444907957L, "Famous Five", "Enid Blyton")
//    val b3 =Book(9781444907957L, "Famous Five", "Enid Blyton")
//
//
//
//    BookService.insertBook(b1)
//    BookService.insertBook(b2)
//    BookService.insertBook(b3)



    println(BookService.getAllBooks())
  }
}

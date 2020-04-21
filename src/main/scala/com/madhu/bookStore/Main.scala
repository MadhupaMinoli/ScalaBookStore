package com.madhu.bookStore

import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Service.BookService

object Main {
  def main(args: Array[String]):Unit = {

    val b1 = Book("978-3161484100", "HarryPotter", "JK Rowling")
    val b2 = Book("978-1444907957", "Famous Five", "Enid Blyton")



    BookService.addBook(b1)
    BookService.addBook(b2)


    BookService.getAllBooks();
  }
}

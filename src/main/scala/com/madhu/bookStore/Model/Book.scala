package com.madhu.bookStore.Model

case class Book( isbn:Long, title: String,  author: String, var quantity : Int =1 )


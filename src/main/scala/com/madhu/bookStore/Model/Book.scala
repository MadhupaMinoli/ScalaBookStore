package com.madhu.bookStore.Model

case class Book( val isbn:Long, val title: String, val  author: String, var quantity : Int =1 )


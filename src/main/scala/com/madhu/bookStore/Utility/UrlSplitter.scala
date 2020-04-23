package com.madhu.bookStore.Utility

import java.net.URI

import scala.collection.mutable


object UrlSplitter {

    def pathToList(path : String): List[String] ={
        path.split("/").toList.tail
    }

    def queryToList(query : String): mutable.HashMap[String, String]={
      val queryList = mutable.HashMap[String, String]();
      for (param <- query.split("&")) {
        val pair = param.split("=")
          if (pair.length==2) queryList+=(pair(0)->pair(1))
      }
      queryList
      }



}

package com.madhu.bookStore.Utility


object UrlSplitter {
    def pathToList(path : String): List[String] = path.split("/").toList.tail

    def queryToList(query : String):List[String] = query.split("=").toList
}

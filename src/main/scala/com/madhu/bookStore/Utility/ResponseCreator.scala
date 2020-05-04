package com.madhu.bookStore.Utility

import com.madhu.bookStore.Model.Response

object ResponseCreator {
  private val RESPONSE_TYPE_JSON = "application/json;charset=utf-8"
  private val RESPONSE_TYPE_TEXT = "text/plain"

  def successResponse(string: String): Response = new Response(string, 200 , RESPONSE_TYPE_JSON)

  def InvalidResponse: Response = new Response("Invalid Request", 400,RESPONSE_TYPE_TEXT)

  def emptyRequestBodyResponse:Response = new Response("Invalid Request", 400,RESPONSE_TYPE_TEXT)
}

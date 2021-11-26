package com.template.models

data class Model(
    val info: Info,
    val item: List<Item>,
    val variable: List<Variable>
)

data class Info(
    val _postman_id: String,
    val name: String,
    val schema: String
)

data class Item(
    val name: String,
    val protocolProfileBehavior: ProtocolProfileBehavior,
    val request: Request,
    val response: List<Response>
)

data class Variable(
    val key: String,
    val value: String
)

data class ProtocolProfileBehavior(
    val disableBodyPruning: Boolean
)

data class Request(
    val body: Body,
    val header: List<Any>,
    val method: String,
    val url: Url
)

data class Response(
    val _postman_previewlanguage: String,
    val body: String,
    val code: Int,
    val cookie: List<Any>,
    val header: List<Header>,
    val name: String,
    val originalRequest: OriginalRequest,
    val status: String
)

data class Body(
    val formdata: List<Formdata>,
    val mode: String,
    val raw: String
)

data class Url(
    val host: List<String>,
    val path: List<String>,
    val port: String,
    val protocol: String,
    val query: List<Query>,
    val raw: String
)

data class Formdata(
    val disabled: Boolean,
    val key: String,
    val src: String,
    val type: String,
    val value: String
)

data class Query(
    val key: String,
    val value: String
)

data class Header(
    val key: String,
    val value: String
)

data class OriginalRequest(
    val body: BodyX,
    val header: List<Any>,
    val method: String,
    val url: UrlX
)

data class BodyX(
    val formdata: List<FormdataX>,
    val mode: String,
    val raw: String
)

data class UrlX(
    val host: List<String>,
    val path: List<String>,
    val port: String,
    val protocol: String,
    val raw: String
)

data class FormdataX(
    val disabled: Boolean,
    val key: String,
    val src: String,
    val type: String,
    val value: String
)
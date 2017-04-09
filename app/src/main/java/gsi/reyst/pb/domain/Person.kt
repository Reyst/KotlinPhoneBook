package gsi.reyst.pb.domain

import java.io.Serializable

data class Person(var name:String = "", var surname: String = "",
                  val phones: MutableList<Phone> = ArrayList()) : Serializable


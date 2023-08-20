package com.saddict.reinvent.utils

//import androidx.room.TypeConverter
//import com.fasterxml.jackson.core.type.TypeReference
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.saddict.reinvent.model.remote.ImageDetail
//
//class Converters {
//    private val objectMapper = ObjectMapper()
//
//    @TypeConverter
//    fun fromString(value: String): List<ImageDetail> {
//        return objectMapper.readValue(value, object : TypeReference<List<ImageDetail>>() {})
//    }
//
//    @TypeConverter
//    fun fromList(list: List<ImageDetail>): String {
//        return objectMapper.writeValueAsString(list)
//    }
//}
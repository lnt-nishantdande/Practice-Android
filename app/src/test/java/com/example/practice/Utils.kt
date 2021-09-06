package com.example.practice

import java.io.File

/**
 * Utility Class
 */
class Utils {
    companion object{
        /**
         * Function reads input file and converts to json
         * @param : path of file in string
         * @return : return file data in json format
         */
        fun getJson(path : String) : String {
            val uri = javaClass.classLoader!!.getResource(path)
            val file = File(uri.path)
            return String(file.readBytes())
        }
    }
}
package com.pdrxcode.engine

import android.content.Context
import org.json.JSONObject

object LanguageLoader {

    fun load(context: Context, fileName: String): LanguageConfig {

        val jsonString = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val json = JSONObject(jsonString)

        val keywords = mutableMapOf<String, LanguageItem>()
        val functions = mutableMapOf<String, LanguageItem>()
        val patterns = mutableMapOf<String, String>()
        val colors = mutableMapOf<String, String>()

        // 🔥 KEYWORDS
        val keywordsJson = json.getJSONObject("keywords")
        keywordsJson.keys().forEach { key ->
            val item = keywordsJson.getJSONObject(key)

            keywords[key] = LanguageItem(
                description = item.getString("description"),
                syntax = item.getString("syntax"),
                example = item.getString("example")
            )
        }

        // 🔥 FUNCTIONS
        val functionsJson = json.getJSONObject("functions")
        functionsJson.keys().forEach { key ->
            val item = functionsJson.getJSONObject(key)

            functions[key] = LanguageItem(
                description = item.getString("description"),
                syntax = item.getString("syntax"),
                example = item.getString("example")
            )
        }

        // 🔥 PATTERNS
        val patternsJson = json.getJSONObject("patterns")
        patternsJson.keys().forEach { key ->
            patterns[key] = patternsJson.getString(key)
        }

        // 🔥 COLORS
        val colorsJson = json.getJSONObject("colors")
        colorsJson.keys().forEach { key ->
            colors[key] = colorsJson.getString(key)
        }

        return LanguageConfig(
            language = json.getString("language"),
            patterns = patterns,
            keywords = keywords,
            functions = functions,
            colors = colors
        )
    }
}
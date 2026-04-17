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
        val characters = mutableMapOf<String, Character>()

        // 🔥 KEYWORDS
        val keywordsJson = json.getJSONObject("keywords")
        keywordsJson.keys().forEach { key ->
            val item = keywordsJson.getJSONObject(key)

            keywords[key] = LanguageItem(
                name = key,
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
                name = key,
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

        // 🔥 CHARACTERS (NOVO)
        val charactersJson = json.optJSONObject("characters")
        charactersJson?.keys()?.forEach { key ->
            val item = charactersJson.getJSONObject(key)

            characters[key] = Character(
                name = key,
                color = item.getString("color")
            )
        }

        return LanguageConfig(
            language = json.getString("language"),
            patterns = patterns,
            keywords = keywords,
            functions = functions,
            colors = colors,
            characters = characters
        )
    }
}
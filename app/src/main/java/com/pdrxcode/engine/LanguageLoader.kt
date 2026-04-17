package com.pdrxcode.engine

import android.content.Context
import org.json.JSONObject

object LanguageLoader {

    fun load(context: Context, fileName: String): LanguageConfig {

        val json = context.assets.open("languages/$fileName")
            .bufferedReader()
            .use { it.readText() }

        val obj = JSONObject(json)

        val patterns = obj.getJSONObject("patterns").toMap()
        val keywords = obj.getJSONObject("keywords").toTooltips()
        val functions = obj.getJSONObject("functions").toTooltips()
        val colors = obj.getJSONObject("colors").toMap()

        return LanguageConfig(
            language = obj.getString("language"),
            patterns = patterns,
            keywords = keywords,
            functions = functions,
            colors = colors
        )
    }

    private fun JSONObject.toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        keys().forEach { key ->
            map[key] = getString(key)
        }
        return map
    }

    private fun JSONObject.toTooltips(): Map<String, Tooltip> {
        val map = mutableMapOf<String, Tooltip>()
        keys().forEach { key ->
            val obj = getJSONObject(key)
            map[key] = Tooltip(
                name = key,
                description = obj.getString("description"),
                syntax = obj.getString("syntax"),
                example = obj.getString("example")
            )
        }
        return map
    }
}
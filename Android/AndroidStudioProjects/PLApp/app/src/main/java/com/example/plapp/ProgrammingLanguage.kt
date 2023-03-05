package com.example.plapp

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class ProgrammingLanguage(
    val name: String,
    @IdRes @DrawableRes val logo: Int,
    val shortDescription: String,
    val createdAt: String,
    val inventor: String,
    val fileExtension: String,
    val fullDescription: String
) : Serializable {
    companion object {
        fun asListFromResArray(context: Context): List<ProgrammingLanguage> {
            val listProgrammingLanguageNames =
                context.resources.getStringArray(R.array.programming_language_names)
            val typedArrayImageResourcesId =
                context.resources.obtainTypedArray(R.array.programming_language_logos)
            return List(listProgrammingLanguageNames.size) { index ->
                ProgrammingLanguage(
                    listProgrammingLanguageNames[index],
                    typedArrayImageResourcesId.getResourceId(index, R.drawable.ic_question_mark),
                    context.resources.getStringArray(R.array.programming_language_short_descriptions)[index],
                    context.resources.getStringArray(R.array.programming_language_date_created)[index],
                    context.resources.getStringArray(R.array.programming_language_inventors)[index],
                    context.resources.getStringArray(R.array.programming_language_ext_file_names)[index],
                    context.resources.getStringArray(R.array.programming_language_descriptions)[index]
                )
            }.also { typedArrayImageResourcesId.recycle() }
        }
    }
}

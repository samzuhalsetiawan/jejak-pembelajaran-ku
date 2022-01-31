package com.example.todoapp.models

data class TodoList(
    var hari: Hari,
    var title: String,
    var sudahDilakukan: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TodoList -> this.hari == other.hari && this.title == other.title
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = hari.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}

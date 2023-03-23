package com.example.githubuser.data.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "user_table")
data class User(

    @PrimaryKey
    @ColumnInfo("id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo("avatar_url")
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo("bio")
    @SerializedName("bio")
    val bio: String? = null,

    @ColumnInfo("blog")
    @SerializedName("blog")
    val blog: String? = null,

    @ColumnInfo("company")
    @SerializedName("company")
    val company: String? = null,

    @ColumnInfo("created_at")
    @SerializedName("created_at")
    val createdAt: String? = null,

    @ColumnInfo("email")
    @SerializedName("email")
    val email: String? = null,

    @ColumnInfo("events_url")
    @SerializedName("events_url")
    val eventsUrl: String? = null,

    @ColumnInfo("followers")
    @SerializedName("followers")
    val followers: Int? = null,

    @ColumnInfo("followers_url")
    @SerializedName("followers_url")
    val followersUrl: String? = null,

    @ColumnInfo("following")
    @SerializedName("following")
    val following: Int? = null,

    @ColumnInfo("following_url")
    @SerializedName("following_url")
    val followingUrl: String? = null,

    @ColumnInfo("gists_url")
    @SerializedName("gists_url")
    val gistsUrl: String? = null,

    @ColumnInfo("gravatar_id")
    @SerializedName("gravatar_id")
    val gravatarId: String? = null,

    @ColumnInfo("hireable")
    @SerializedName("hireable")
    val hireAble: String? = null,

    @ColumnInfo("html_url")
    @SerializedName("html_url")
    val htmlUrl: String? = null,

    @ColumnInfo("location")
    @SerializedName("location")
    val location: String? = null,

    @ColumnInfo("login")
    @SerializedName("login")
    val login: String,

    @ColumnInfo("name")
    @SerializedName("name")
    val name: String? = null,

    @ColumnInfo("node_id")
    @SerializedName("node_id")
    val nodeId: String? = null,

    @ColumnInfo("organizations_url")
    @SerializedName("organizations_url")
    val organizationsUrl: String? = null,

    @ColumnInfo("public_gists")
    @SerializedName("public_gists")
    val publicGists: Int? = null,

    @ColumnInfo("public_repos")
    @SerializedName("public_repos")
    val publicRepos: Int? = null,

    @ColumnInfo("received_events_url")
    @SerializedName("received_events_url")
    val receivedEventsUrl: String? = null,

    @ColumnInfo("repos_url")
    @SerializedName("repos_url")
    val reposUrl: String? = null,

    @ColumnInfo("site_admin")
    @SerializedName("site_admin")
    val siteAdmin: Boolean? = null,

    @ColumnInfo("starred_url")
    @SerializedName("starred_url")
    val starredUrl: String? = null,

    @ColumnInfo("subscriptions_url")
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = null,

    @ColumnInfo("twitter_username")
    @SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @ColumnInfo("type")
    @SerializedName("type")
    val type: String? = null,

    @ColumnInfo("updated_at")
    @SerializedName("updated_at")
    val updated_at: String? = null,

    @ColumnInfo("url")
    @SerializedName("url")
    val url: String? = null,

    @ColumnInfo("score")
    @SerializedName("score")
    val score: Double? = null,

    @ColumnInfo("is_favorite", defaultValue = "1")
    @SerializedName("is_favorite")
    var isFavorite: Boolean = false

) : Parcelable
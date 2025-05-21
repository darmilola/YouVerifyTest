package com.assignment.youverifytest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class AppUIStates(
    val isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    val isFailed: Boolean = false,
    val isEmpty: Boolean = false,
    val isDefault: Boolean = false,
    val loadingMessage: String = "",
    val successMessage: String = "",
    val emptyMessage: String = "",
    val errorMessage: String = "",
    val defaultMessage: String = ""): Parcelable
package com.college.courseevaluation.ui.theme.model
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.college.courseevaluation.ui.theme.ApiClient
import com.college.courseevaluation.ui.theme.data.DashboardData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    private val _dashboardData = mutableStateOf<DashboardData?>(null)
    val dashboardData: State<DashboardData?> = _dashboardData

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        _isLoading.value = true
        ApiClient.apiService.getDashboardData().enqueue(object : Callback<DashboardData> {
            override fun onResponse(call: Call<DashboardData>, response: Response<DashboardData>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dashboardData.value = response.body()
                } else {
                    _errorMessage.value = "Failed to fetch dashboard data: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<DashboardData>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Error fetching dashboard data: ${t.message}"
            }
        })
    }
}
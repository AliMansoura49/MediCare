package com.example.medicare.data.model.date

import com.example.medicare.core.toMonth
import com.example.medicare.core.enums.Month
import java.util.Calendar

data class FullDate(
    val year: Int,
    val month: Month,
    val day: Int
){
    constructor() : this(1000, Month.JAN,1)
    companion object{
        fun getCurrentDate() : FullDate{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Note: Month is zero-based
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return FullDate(year,month.toMonth(),day)
        }
        fun compareFullDates(date1: FullDate, date2: FullDate): Int {
            // Compare years
            val yearComparison = date1.year.compareTo(date2.year)
            if (yearComparison != 0) {
                return yearComparison
            }

            // Compare months
            val monthComparison = date1.month.compareTo(date2.month)
            if (monthComparison != 0) {
                return monthComparison
            }

            // Compare days
            return date1.day.compareTo(date2.day)
        }


    }

}

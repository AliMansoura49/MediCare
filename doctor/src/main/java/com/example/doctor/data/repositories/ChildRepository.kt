package com.example.doctor.data.repositories

import com.example.doctor.data.model.child.Child
import com.example.doctor.data.model.child.VaccineTableItem
import kotlinx.coroutines.flow.Flow

interface ChildRepository {

    /** Get all user's children*/
    val children : Flow<List<Child>>
    /**Add a new child to the user's children
     *  collection*/
    suspend fun addChild(child : Child)
    /**Add new vaccineTableItem to child's vaccines collection*/
    suspend fun addVaccineTableItem(vaccineTableItem: VaccineTableItem, childId: String)
    /**Get vaccine table for a child by its Id*/
    suspend fun getVaccineTable(childId: String) :List<VaccineTableItem>
}

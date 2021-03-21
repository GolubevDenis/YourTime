package com.your.time.app.data.database.habits

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_FRIDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_MONDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_SATURDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_SUNDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_THURSDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_TUESDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.IS_WEDNESDAY
import com.your.time.app.data.database.habits.HabitRoomModel.Companion.TABLE_NAME
import io.reactivex.Flowable

@Dao
interface HabitsDao {

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun selectAll(): Flowable<List<HabitRoomModel>>

    @Insert
    fun insert(habitRoomModel: HabitRoomModel)

    @Delete
    fun delete(habitRoomModel: HabitRoomModel)


    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_MONDAY} = 1")
    fun selectForMonday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_TUESDAY} = 1")
    fun selectForTuesday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_WEDNESDAY} = 1")
    fun selectForWednesday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_THURSDAY} = 1")
    fun selectForThursday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_FRIDAY} = 1")
    fun selectForFriday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_SATURDAY} = 1")
    fun selectForSaturday(): Flowable<List<HabitRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${IS_SUNDAY} = 1")
    fun selectForSunday(): Flowable<List<HabitRoomModel>>
}
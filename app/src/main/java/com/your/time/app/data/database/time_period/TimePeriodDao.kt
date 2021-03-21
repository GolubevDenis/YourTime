package com.your.time.app.data.database.time_period

import android.arch.persistence.room.*
import com.your.time.app.data.database.time_period.TimePeriodRoomModel.Companion.END_TIME
import com.your.time.app.data.database.time_period.TimePeriodRoomModel.Companion.START_TIME
import com.your.time.app.data.database.time_period.TimePeriodRoomModel.Companion.TABLE_NAME
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface TimePeriodDao {


    @Query("SELECT * FROM ${TABLE_NAME};")
    fun selectAll(): Flowable<List<TimePeriodRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${END_TIME} = " +
            "(SELECT MAX(${END_TIME}) FROM ${TABLE_NAME});")
    fun selectLastest(): Maybe<TimePeriodRoomModel>

    @Query("SELECT * FROM (SELECT * FROM ${TABLE_NAME} ORDER BY ${START_TIME} ASC LIMIT :count) ORDER BY ${START_TIME} DESC;")
    fun selectLast(count: Int): Flowable<List<TimePeriodRoomModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg timePeriod: TimePeriodRoomModel)

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${START_TIME} = :time;")
    fun selectByStartTime(time: Long): Flowable<List<TimePeriodRoomModel>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ${END_TIME} = :time;")
    fun selectByEndTime(time: Long): Flowable<List<TimePeriodRoomModel>>

    @Delete
    fun delete(period: TimePeriodRoomModel)

    @Update
    fun update(period: TimePeriodRoomModel)

    @Query("SELECT * FROM ${TABLE_NAME} WHERE (${END_TIME} <= :endTime AND ${END_TIME} >= :startTime) " +
            "OR ${START_TIME} <= :endTime AND ${START_TIME} >= :startTime;")
    fun selectInInterval(startTime: Int, endTime: Int): Flowable<List<TimePeriodRoomModel>>

}
package com.your.time.app.data.database.action

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface ActionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAction(action: ActionRoomModel): Long

    @Delete
    fun delete(actions: List<ActionRoomModel>)

    @Update
    fun update(actions: List<ActionRoomModel>)

    @Update
    fun update(actions: ActionRoomModel)

    @Query("SELECT * FROM ${ActionRoomModel.TABLE_NAME};")
    fun selectAllActions(): Flowable<List<ActionRoomModel>>

    @Query("SELECT * FROM ${ActionRoomModel.TABLE_NAME} WHERE ${ActionRoomModel.IS_ACTIVE} = 1;")
    fun selectAllActiveActions(): Flowable<List<ActionRoomModel>>

    @Query("SELECT * FROM ${ActionRoomModel.TABLE_NAME} WHERE ${ActionRoomModel.ID} = :id;")
    fun selectActionById(id: Long): Maybe<ActionRoomModel>

    @Query("SELECT * FROM ${ActionRoomModel.TABLE_NAME} WHERE ${ActionRoomModel.ACTION} LIKE '%' || :title || '%' AND ${ActionRoomModel.IS_ACTIVE} = 1;;")
    fun queryActiveActionsByTitle(title: String): Flowable<List<ActionRoomModel>>
}
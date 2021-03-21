package com.your.time.app.presentation.view.adapters.list

import android.support.v7.widget.RecyclerView
import android.view.View
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(ListForAdapterBaseTest.TestAdapter::class)
class ListForAdapterBaseTest : Assert(){

    @Test
    fun testAddItems(){
        val list = ListForAdapterBase<Int,  TestHolder>()

        list.addItems(listOf(1))
        Assert.assertEquals(1, list.getItems().size)

        list.addItems(listOf(2,3))
        assertEquals(3, list.getItems().size)
    }

    @Test
    fun testSetItems(){
        val list = ListForAdapterBase<Int, TestHolder>()

        list.addItems(listOf(1))

        list.setItems(listOf(2, 3))
        assertEquals(2, list.getItems().size)
    }

    @Test
    fun testClearItems(){
        val list = ListForAdapterBase<Int, TestHolder>()
        list.addItems(listOf(1, 2))

        list.clearItems()
        assertEquals(0, list.getItems().size)
    }

    @Test
    fun testDeleteItems(){
        val list = ListForAdapterBase<Int, TestHolder>()
        list.addItems(listOf(1, 2))

        list.deleteItems(listOf(1))
        assertEquals(2, list.getItems()[0])
    }

    @Test
    fun testMoveItems(){
        val list = ListForAdapterBase<Int, TestHolder>()

        list.addItems(listOf(1, 2, 3))

        list.move(1, 0)
        assertArrayEquals(arrayOf(2, 1, 3).toIntArray(), list.getItems().toIntArray())

        list.move(1, 0)
        assertArrayEquals(arrayOf(1, 2, 3).toIntArray(), list.getItems().toIntArray())
    }

    @Test
    fun testClearItems_CallNotify(){
        val adapter = newAdapter()
        val list = ListForAdapterBase<Int, TestHolder>()
        list.setupAdapter(adapter)

        list.clearItems()
        Mockito.verify(adapter).notifyDataSetChanged()
    }

    @Test
    fun testDeleteItems_CallNotify(){
        val adapter = newAdapter()
        val list = ListForAdapterBase<Int, TestHolder>()
        list.setupAdapter(adapter)

        list.deleteItems(listOf())
        Mockito.verify(adapter).notifyDataSetChanged()
    }

    @Test
    fun testSetItems_CallNotify(){
        val adapter = newAdapter()
        val list = ListForAdapterBase<Int, TestHolder>()
        list.setupAdapter(adapter)

        list.setItems(listOf())
        Mockito.verify(adapter).notifyDataSetChanged()
    }

    @Test
    fun testAddItems_CallNotify(){
        val adapter = newAdapter()
        val list = ListForAdapterBase<Int, TestHolder>()
        list.setupAdapter(adapter)

        list.addItems(listOf(1,2))
        Mockito.verify(adapter).notifyItemRangeInserted(0, 2)

        list.addItems(listOf(3,4))
        Mockito.verify(adapter).notifyItemRangeInserted(2, 2)
    }

    @Test
    fun testMove_CallNotify(){
        val adapter = newAdapter()
        val list = ListForAdapterBase<Int, TestHolder>()

        list.setupAdapter(adapter)
        list.addItems(listOf(0, 1, 2))

        list.move(0, 1)
        Mockito.verify(adapter).notifyItemMoved(0, 1)
    }

    fun newAdapter(): TestAdapter{
        val adapter = PowerMockito.mock(TestAdapter::class.java)

        PowerMockito.doNothing().`when`(adapter).notifyDataSetChanged()
        PowerMockito.doNothing().`when`(adapter).notifyItemRangeChanged(Mockito.anyInt(), Mockito.anyInt())

        return adapter
    }

    abstract class TestAdapter : RecyclerView.Adapter<TestHolder>()
    class TestHolder(view: View) : RecyclerView.ViewHolder(view)
}
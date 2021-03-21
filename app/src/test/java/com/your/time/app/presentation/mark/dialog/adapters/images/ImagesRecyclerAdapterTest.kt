package com.your.time.app.presentation.mark.dialog.adapters.images

import android.content.Context
import com.your.time.app.domain.model.ImageModel
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class ImagesRecyclerAdapterTest {

    private lateinit var adapter: ImagesRecyclerAdapter
    private lateinit var listImages: ListForAdapter<ImageModel, ImageHolder>
    private lateinit var mockContext: Context

    @Before
    fun init(){
        mockContext = Mockito.mock(Context::class.java)
        listImages = Mockito.mock(ListForAdapter::class.java) as ListForAdapter<ImageModel, ImageHolder>
        adapter = ImagesRecyclerAdapterImpl(mockContext, listImages)
    }

    @Test
    fun getSelectedImageId() {
        val selected = ImageModel("iconName1", true)
        Mockito.`when`(listImages.getItems()).thenReturn(listOf(
                ImageModel("iconName2"),
                selected,
                ImageModel("iconName3")
        ))

        assertEquals(selected.imageName, adapter.getSelectedImageName())
    }

}
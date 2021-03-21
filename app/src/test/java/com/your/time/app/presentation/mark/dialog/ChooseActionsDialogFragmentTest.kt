package com.your.time.app.presentation.mark.dialog

import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import com.hsalf.smilerating.BaseRating
import com.hsalf.smilerating.SmileRating
import com.your.time.app.R
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenter
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsView
import com.your.time.app.presentation.mark.dialog.adapters.images.ImagesRecyclerAdapter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment

@RunWith(RobolectricTestRunner::class)
class ChooseActionsDialogFragmentTest : Assert(){

    private lateinit var fragment: ChooseActionsDialogFragment
    private lateinit var chooseActionsView: ChooseActionsView
    private lateinit var recycler: RecyclerView
    private lateinit var ok: TextView
    private lateinit var search: SearchView
    private lateinit var presenter: ChooseActionsPresenter
    private lateinit var usefulness: SmileRating
    private lateinit var isHarmfully: CheckBox
    private lateinit var isNeutrally: CheckBox
    private lateinit var editTitleAction: EditText
    private lateinit var addConfirm: TextView
    private lateinit var addCancel: TextView
    private lateinit var addContainer: ConstraintLayout
    private lateinit var actionsContainer: ConstraintLayout

    private val action = ActionModel("action", color = Color.BLUE)

    @Before
    fun init(){
        fragment = ChooseActionsDialogFragment.newInstance()
        chooseActionsView = fragment
        startFragment(fragment)

        ok = fragment.view!!.findViewById<TextView>(R.id.ok)
        recycler = fragment.view!!.findViewById<RecyclerView>(R.id.list_actions)
        search = fragment.view!!.findViewById<SearchView>(R.id.search)
        usefulness = fragment.view!!.findViewById<SmileRating>(R.id.usefulnesBar)
        editTitleAction = fragment.view!!.findViewById<EditText>(R.id.edit_action_title)
        addConfirm = fragment.view!!.findViewById<TextView>(R.id.add_confirm)
        addCancel = fragment.view!!.findViewById<TextView>(R.id.add_cancel)
        addContainer = fragment.view!!.findViewById<ConstraintLayout>(R.id.add_container)
        actionsContainer = fragment.view!!.findViewById<ConstraintLayout>(R.id.actions_contaier)

        presenter = Mockito.mock(ChooseActionsPresenter::class.java)
    }

    @Test
    fun testClickOk_ObserverEmitted(){
        val listener = Mockito.mock(ChooseActionsDialogFragment.OnChooseActionsDialogListeners::class.java)
        fragment.listener = listener

        Mockito.verifyZeroInteractions(listener)
        ok.performClick()
        Mockito.verify(listener).onActionsSelected(Mockito.anyList())
    }

    @Test
    fun testShowActions(){
        assertEquals(0, recycler.childCount)

        chooseActionsView.showActions(getListActions(3))
        Thread.sleep(100) // RecuclerView computing items... It's necessary only for test
        assertEquals(3, recycler.childCount)

        chooseActionsView.showActions(getListActions(2))
        Thread.sleep(100) // RecuclerView computing items... It's necessary only for test
        assertEquals(2, recycler.childCount)
    }

    @Test
    fun testShowMoreActions(){
        assertEquals(0, recycler.childCount)

        chooseActionsView.showMoreActions(getListActions(3))
        Thread.sleep(100) // RecuclerView computing items... It's necessary only for test
        assertEquals(3, recycler.childCount)

        chooseActionsView.showMoreActions(getListActions(2))
        Thread.sleep(100) // RecuclerView computing items... It's necessary only for test
        assertEquals(5, recycler.childCount)
    }

    @Test
    fun testAddConfirm_SendCorrectDataToPresenter(){
        fragment.presenter = presenter

        editTitleAction.setText("running")
        usefulness.setSelectedSmile(BaseRating.GOOD, false)

        val adapter = Mockito.mock(ImagesRecyclerAdapter::class.java)
        Mockito.`when`(adapter.getSelectedImageName()).thenReturn(null)
        recycler.adapter = adapter

        val expected = ActionModel("running", null, ActionModel.Usefulness.USEFULLY, color = Color.BLUE)

        addConfirm.performClick()
        Mockito.verify(presenter).onClickAddAction(expected)

    }

    private fun getListActions(count: Int): List<ActionModel> {
        return List(count) { i -> ActionModel("action $i", color = Color.BLUE) }
    }
}
package com.your.time.app.presentation.mark.dialog

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.your.time.app.MyApplication
import com.your.time.app.R
import com.your.time.app.databinding.FragmentChooseActionDialogBinding
import com.your.time.app.di.presentation.ActivityContextModule
import com.your.time.app.di.presentation.mark.dialog.ChooseActionsComponent
import com.your.time.app.di.presentation.mark.dialog.DaggerChooseActionsComponent
import com.your.time.app.domain.model.ImageModel
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.mark.dialog.adapters.images.ImagesRecyclerAdapter
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenter
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsView
import com.your.time.app.presentation.view.decorators.ListMarginDecoration
import com.your.time.app.presentation.view.dialogs.MvpDialogFragment
import com.hsalf.smilerating.BaseRating
import com.jakewharton.rxbinding2.widget.textChanges
import com.your.time.app.presentation.explansions.*
import com.your.time.app.presentation.mark.dialog.adapters.actions.ActionsRecyclerViewAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class ChooseActionsDialogFragment :
        MvpDialogFragment<ChooseActionsView, ChooseActionsPresenter>(0.95f, 0.95f),
        ChooseActionsView {

    private lateinit var binder: FragmentChooseActionDialogBinding
    private lateinit var actionsAdapter: ActionsRecyclerViewAdapter
    private lateinit var imagesAdapter: ImagesRecyclerAdapter
    private lateinit var actionFactory: ActionFactory

    var listener: OnChooseActionsDialogListeners? = null

    interface OnChooseActionsDialogListeners{
        fun onActionsSelected(actions: List<ActionModel>)
    }

    companion object {
        fun newInstance() = ChooseActionsDialogFragment()
    }

    override fun createPresenter(): ChooseActionsPresenter = component.getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        actionsAdapter = component.getActionsAdapter()
        imagesAdapter = component.getImagesAdapter()
        actionFactory = component.getActionFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fun initListeners(){
            binder.close.setOnClickListener {
                this.dismiss()
            }

            binder.ok.setOnClickListener {
                listener?.onActionsSelected(actionsAdapter.getListItems().getActiveActions())
                this.dismiss()
            }
        }

        fun initAdapter(){
            val chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                    .setScrollingEnabled(true)
                    .setGravityResolver { Gravity.START }
                    .build()
            binder.listActions.layoutManager = chipsLayoutManager
            binder.listActions.addItemDecoration(ListMarginDecoration(context!!, 3))
            binder.listActions.adapter = actionsAdapter
            actionsAdapter.onLongClickListener = { action ->
                switchToEdit(action)
            }
        }

        fun initSearch(){
            binder.search.textChanges()
                    .skipInitialValue()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        presenter.onClickSearch(it.toString())
                    }
        }

        fun initImagesList(){
            binder.imagesList.layoutManager = GridLayoutManager(context, 2, LinearLayout.HORIZONTAL, false)
            binder.imagesList.adapter = imagesAdapter
        }

        fun initDeleteButton(){
            binder.delete.setOnClickListener {
                presenter.onDeleteActions(actionsAdapter.getListItems().getActiveActions())
            }
        }

        fun initAddBtn(){
            binder.add.setOnClickListener {
                switchToAdding()
            }
        }

        fun initAddCancelBtn(){
            binder.addCancel.setOnClickListener {
                switchToActions()
            }
        }

        binder = FragmentChooseActionDialogBinding.inflate(inflater, container, true)

        initAdapter()
        initListeners()
        initSearch()
        initImagesList()
        initAddEditPanel()
        initDeleteButton()
        initAddBtn()
        initAddCancelBtn()

        return binder.root
    }

    override fun showMarkingTime(markingTime: String){
        binder.markingTime.text = markingTime
    }

    /* ADD EDIT PANEL */
    private fun initAddEditPanel(){

        fun setActionInConstructor(action: ActionModel){
            binder.actionForAdding.action = action
            if(action.action.isEmpty() && action.iconName == null) binder.actionForAdding.invisible()
            else binder.actionForAdding.visible()
        }

        binder.usefulnesBar.setNameForSmile(BaseRating.TERRIBLE, getString(R.string.short_very_harmful))
        binder.usefulnesBar.setNameForSmile(BaseRating.BAD, getString(R.string.harmful))
        binder.usefulnesBar.setNameForSmile(BaseRating.OKAY, getString(R.string.neutral))
        binder.usefulnesBar.setNameForSmile(BaseRating.GOOD, getString(R.string._useful))
        binder.usefulnesBar.setNameForSmile(BaseRating.GREAT, getString(R.string.short_very_useful))

        binder.usefulnesBar.setOnSmileySelectionListener { number, _ ->
            val usefulness = when(number){
                BaseRating.TERRIBLE -> ActionModel.Usefulness.VERY_HARMFULLY
                BaseRating.BAD -> ActionModel.Usefulness.HARMFULLY
                BaseRating.OKAY -> ActionModel.Usefulness.NEUTRALLY
                BaseRating.GOOD -> ActionModel.Usefulness.USEFULLY
                BaseRating.GREAT -> ActionModel.Usefulness.VERY_USEFULLY
                else -> ActionModel.Usefulness.NEUTRALLY
            }
            val action = binder.actionForAdding.action.copy(usefulness = usefulness)
            binder.actionForAdding.action = action
        }
        binder.usefulnesBar.selectedSmile = BaseRating.OKAY

        binder.editActionTitle.textChanges()
                .subscribe {
                    val action = binder.actionForAdding.action.copy(action = it.toString())
                    setActionInConstructor(action)
                }

        imagesAdapter.setOnSelectedImageChanged { iconName: String? ->
            val action = binder.actionForAdding.action.copy(iconName = iconName)
            setActionInConstructor(action)
        }

        binder.searchImages.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe {
                    presenter.onClickSearchImages(it.toString())
                }
    }

    fun validation(): Boolean {

        if(binder.editActionTitle.text.toString().isEmpty()) {
            context!!.shortToast("Введите название")
            return false
        }

        if(binder.editActionTitle.text.toString().length > 18) {
            context!!.shortToast("Название должно быть не длиннее 18 символов")
            return false
        }

        return true
    }

    private var actionForEdit: ActionModel? = null
    private fun switchToEdit(action: ActionModel){
        clearAddEditPanel()
        presenter.onClickModeEdit(action)

        actionForEdit = action
        binder.title.text = getString(R.string.editing_tag)
        binder.actionsContaier.visibility = View.INVISIBLE
        binder.addContainer.visibility = View.VISIBLE

        binder.editActionTitle.setText(action.action)
        val rating = when(action.usefulness){
            ActionModel.Usefulness.VERY_HARMFULLY -> 0
            ActionModel.Usefulness.HARMFULLY -> 1
            ActionModel.Usefulness.NEUTRALLY -> 2
            ActionModel.Usefulness.USEFULLY -> 3
            ActionModel.Usefulness.VERY_USEFULLY -> 4
            else -> 2
        }
        binder.usefulnesBar.setSelectedSmile(rating, false)

        if(action.iconName != null){
            imagesAdapter.selectImage(action.iconName)
        }

        binder.actionForAdding.action = action

        binder.addConfirm.setOnClickListener {
            if(validation()){
                val editedAction = binder.actionForAdding.action
                presenter.onClickEditAction(editedAction)
            }
        }
    }

    private fun clearAddEditPanel(){
        binder.editActionTitle.setText("")
        binder.usefulnesBar.setSelectedSmile(BaseRating.OKAY, false)
        imagesAdapter.unselectImages()
        binder.actionForAdding.action = actionFactory.createActionModel("")
    }

    override fun showImages(images: List<ImageModel>) {
        ui {
            imagesAdapter.getListImages().setItems(images)
        }
    }

    private fun switchToActions(){
        binder.title.text = getString(R.string.what_did_you_do)
        binder.addContainer.visibility = View.INVISIBLE
        binder.actionsContaier.visibility = View.VISIBLE
    }

    private fun switchToAdding(){
        clearAddEditPanel()
        binder.editActionTitle.setText(binder.search.text.toString())
        presenter.onClickModeAdding()
        binder.title.text = getString(R.string.creating_tag)
        binder.addContainer.visibility = View.VISIBLE
        binder.actionsContaier.visibility = View.INVISIBLE
        binder.addConfirm.setOnClickListener {
            if(validation()){
                val action = binder.actionForAdding.action
                presenter.onClickAddAction(action)
                clearAddEditPanel()
            }
        }
    }

    private val component: ChooseActionsComponent by lazy {
        DaggerChooseActionsComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityContextModule(ActivityContextModule(activity!!))
                .build()
    }

    override fun showNoActiveActions(actions: List<ActionModel>) {
        actionsAdapter.getListItems().setNoActiveActions(actions)
    }

    override fun showActions(actions: List<ActionModel>) {
        actionsAdapter.getListItems().setItems(actions)
    }


    private var createActionButtonX: Float? = null
    private var createActionButtonY: Float? = null
    override fun showSearchResult(actions: List<ActionModel>) {

        fun showEmptySearchResultAnimation(){
            if(createActionButtonX == null){
                createActionButtonX = binder.add.x
                createActionButtonY = binder.add.y
            }

            val x = binder.emptySearchResultText.x + binder.emptySearchResultText.width/2 - binder.add.width/2
            val y = binder.emptySearchResultText.y + binder.emptySearchResultText.height

            binder.add.startMoveAnimationTo(x, y, 700)
        }
        fun showNotEmptySearchResultAnimation(){
            binder.add.startMoveAnimationTo(createActionButtonX!!, createActionButtonY!!, 700)
        }

//        actionsAdapter.getListItems().clearItems()
        actionsAdapter.getListItems().clearNoActiveActions()
        Observable.fromIterable(actions)
                .concatMap { i -> Observable.just(i).delay(60, TimeUnit.MILLISECONDS) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    actionsAdapter.getListItems().addNoActiveAction(it)
                }.subscribe()

        if(actions.isEmpty()){
            if(!binder.emptySearchResultText.isVisible()) {
                binder.emptySearchResultText.visible()
                showEmptySearchResultAnimation()
            }
        }else{
            if(binder.emptySearchResultText.isVisible()){
                binder.emptySearchResultText.invisible()
                showNotEmptySearchResultAnimation()
            }
        }
    }

    override fun showMoreActions(actions: List<ActionModel>) {
        actionsAdapter.getListItems().addItems(actions)
    }

    override fun showError(message: String) {
        Log.d("TAG", message)
    }

    override fun showActionAdded(action: ActionModel) {
        actionsAdapter.getListItems().addActiveItem(action)
        switchToActions()
    }

    override fun deleteActions(actions: List<ActionModel>) {
        actionsAdapter.getListItems().deleteItems(actions)
        actionsAdapter.getListItems().markAllActionsAsNotActive()
    }
}

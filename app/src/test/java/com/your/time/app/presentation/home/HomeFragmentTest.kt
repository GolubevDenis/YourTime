package com.your.time.app.presentation.home

//@RunWith(RobolectricTestRunner::class)
//class HomeFragmentTest : Assert(){
//
//    private lateinit var fragment: HomeFragment
//    private lateinit var presenter: HomePresenter
//    private lateinit var onMarkClickListener: HomeFragment.OnMarkClickListener
//    private lateinit var chart: PieChart
//
//    private lateinit var markBtn: Button
//
//    @Before
//    fun init(){
//        fragment = HomeFragment.newInstance()
//
//        presenter = Mockito.mock(HomePresenter::class.java)
//        startFragment(fragment)
//        fragment.presenter = presenter
//
//        onMarkClickListener = Mockito.mock(HomeFragment.OnMarkClickListener::class.java)
//        fragment.onMarkClickListener = onMarkClickListener
//
//        chart = fragment.view!!.findViewById(R.id.chart)
//        markBtn = fragment.view!!.findViewById(R.id.mark_btn)
//    }
//
//    fun newPeriods(count: Int)
//            = List(count) { i -> TimePeriodModel(ActionModel(count.toString(), color = Color.BLUE), 1 * i, 5 * (i + 1))}
//}
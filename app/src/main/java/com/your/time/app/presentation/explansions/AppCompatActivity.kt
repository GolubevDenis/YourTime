package com.your.time.app.presentation.explansions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.your.time.app.R


fun AppCompatActivity.setUpToolbar(
        toolbar: Toolbar,
        title: String = "",
        subtitle: String = "",
        titleColor: Int = R.color.white_primary_text,
        subtitleColor: Int = R.color.white_primary_text
) {
    toolbar.title = title
    toolbar.subtitle = subtitle
    toolbar.setTitleTextColor(resources.getColor(titleColor))
    toolbar.setSubtitleTextColor(resources.getColor(subtitleColor))
    setSupportActionBar(toolbar)
}

/*
* Add fragment to the container with id = idContainer.
* If container with id = idContainer doesn't exist will be thrown the Exception.
* If container has already contained a fragment then the new fragment won't be added. */
fun AppCompatActivity.addFragment(idContainer: Int, fragment: Fragment, isAddToBackStack: Boolean = false){
    val oldFragment = supportFragmentManager.findFragmentById(idContainer)
    if(oldFragment == null){
        if(isAddToBackStack){
            supportFragmentManager
                    .beginTransaction()
                    .add(idContainer, fragment)
                    .addToBackStack(null)
                    .commit()
        }else{
            supportFragmentManager
                    .beginTransaction()
                    .add(idContainer, fragment)
                    .commit()
        }
    }
}

/*
* Add fragment to the container with id = idContainer.
* If container with id = idContainer doesn't exist will be thrown the Exception.
* If container has already contained a fragment then new fragment will replace the old fragment.
* If container has not contained a fragment yet then new fragment will be added to the container. */
fun AppCompatActivity.setFragment(idContainer: Int, fragment: Fragment, isAddToBackStack: Boolean = false){
    val oldFragment = supportFragmentManager.findFragmentById(idContainer)
    if(oldFragment == null){
        addFragment(idContainer, fragment, isAddToBackStack)
    }else{
        if(isAddToBackStack){
            supportFragmentManager
                    .beginTransaction()
                    .replace(idContainer, fragment)
                    .addToBackStack(null)
                    .commit()
        }else{
            supportFragmentManager
                    .beginTransaction()
                    .replace(idContainer, fragment)
                    .commit()
        }
    }
}
package com.tobibur.ktxcommonextensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object FragmentTools {

    fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {

        val backStateName = fragment.javaClass.name
        val fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0)

        if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            val ft = fragmentManager.beginTransaction()
            ft.replace(containerId, fragment, backStateName)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }
}
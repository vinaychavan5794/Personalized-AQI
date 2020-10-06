package com.fitbitsample.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

public class FragmentStackHandler {
    private FragmentManager fragmentManager;
    private FragmentStack fragmentStack;

    public FragmentStackHandler(FragmentManager fragmentManager, FragmentStack fragmentStack) {
        this.fragmentManager = fragmentManager;
        this.fragmentStack = fragmentStack;
    }

    public void startAndAddFirstFragmentToStack(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        fragmentStack.addFragmentToStack(fragment);
        ft.commitAllowingStateLoss();
    }

    public void startFragmentToStack(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        ft.commitAllowingStateLoss();
    }

    public void startAndAddFragmentToStack(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        fragmentStack.pauseLastFragmentFromStack();
        ft.hide(fragmentStack.getLastFragmentFromStack());
        fragmentStack.addFragmentToStack(fragment);
        ft.commitAllowingStateLoss();
    }

    public void startFragment(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        fragmentStack.pauseLastFragmentFromStack();
        ft.hide(fragmentStack.getLastFragmentFromStack());
        ft.commitAllowingStateLoss();
    }

    public boolean goToPreviousFragment() {
        if (fragmentStack.fragmentStackSize() > 1) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            fragmentStack.pauseLastFragmentFromStack();
            ft.remove(fragmentStack.removeLastFragmentFromStack());
            fragmentStack.resumeLastFragmentFromStack();
            ft.show(fragmentStack.getLastFragmentFromStack());
            ft.commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    public boolean goToSpecificFragment(int position) {
        if (fragmentStack.fragmentStackSize() > position) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            fragmentStack.pauseLastFragmentFromStack();
            ft.remove(fragmentStack.removeLastFragmentFromStack());
            Fragment fragment = fragmentStack.getFragmentFromStackOfSpecificPosition(position);
            if (fragment != null) {
                ft.show(fragment);
                ft.commit();
                return true;
            }
        }
        return false;
    }

    public void clearEverythingExceptDashboard() {
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            while (fragmentStack.fragmentStackSize() > 1) {
                fragmentStack.pauseLastFragmentFromStack();
                ft.remove(fragmentStack.removeLastFragmentFromStack());
            }
            if (!fragmentManager.isDestroyed()) {
                ft.commitAllowingStateLoss();
            }
        }
    }

    public void clearFragments() {
        if (!fragmentManager.isDestroyed()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            while (fragmentStack.fragmentStackSize() > 0) {
                fragmentStack.pauseLastFragmentFromStack();
                ft.remove(fragmentStack.removeLastFragmentFromStack());
            }
            if (!fragmentManager.isDestroyed()) {
                ft.commitAllowingStateLoss();
            }
            fragmentStack.killFragmentStack();
        }
    }

    public void startAndAddFragmentAndCloseLastFragmentInStack(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        fragmentStack.pauseLastFragmentFromStack();
        ft.hide(fragmentStack.getLastFragmentFromStack());
        if (fragmentStack.fragmentStackSize() > 0) {
            ft.remove(fragmentStack.removeLastFragmentFromStack());
        }
        fragmentStack.addFragmentToStack(fragment);
        ft.commitAllowingStateLoss();
    }

    public void startAndAddFragmentAndCloseAllLastFragmentInStack(Fragment fragment, int container) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, fragment);
        if(fragmentStack.getFragmentStack().size() > 0) {
            fragmentStack.pauseLastFragmentFromStack();
            ft.hide(fragmentStack.getLastFragmentFromStack());
            while (true) {
                if (fragmentStack.fragmentStackSize() > 0) {
                    ft.remove(fragmentStack.removeLastFragmentFromStack());
                } else {
                    break;
                }
            }
        }
        fragmentStack.addFragmentToStack(fragment);
        ft.commitAllowingStateLoss();
    }

    public Fragment closeLastFragmentToStack() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentStack.pauseLastFragmentFromStack();
        Fragment fragment = fragmentStack.getLastFragmentFromStack();
        if (fragment != null) {
            ft.hide(fragment);
            if (fragmentStack.fragmentStackSize() > 0) {
                ft.remove(fragmentStack.removeLastFragmentFromStack());
            }
            ft.commitAllowingStateLoss();
        }
        return fragment;
    }

    public Fragment getLastFragment() {
        if (fragmentStack.fragmentStackSize() == 0) {
            return null;
        }
        return fragmentStack.getLastFragmentFromStack();
    }
}
package com.fitbitsample.util;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */


import androidx.fragment.app.Fragment;

import java.util.Stack;

public class FragmentStack {
    private Stack<Fragment> fragmentStack;

    //    WeakReference<Fragment>
    public FragmentStack() {
        this.fragmentStack = new Stack<>();
    }

    public Stack<Fragment> getFragmentStack() {
        return this.fragmentStack;
    }

    public Fragment getLastFragmentFromStack() {
        return this.fragmentStack.lastElement();
    }

    public void addFragmentToStack(Fragment fragment) {
        this.fragmentStack.push(fragment);
    }

    public void pauseLastFragmentFromStack() {
        this.fragmentStack.lastElement().onPause();
    }

    public void resumeLastFragmentFromStack() {
        this.fragmentStack.lastElement().onResume();
    }

    public void resumeSpecificFragmentFromStack(int position) {
        this.fragmentStack.get(position).onResume();
    }

    public Fragment removeLastFragmentFromStack() {
        return this.fragmentStack.pop();
    }

    public int fragmentStackSize() {
        return this.fragmentStack.size();
    }

    public boolean isFragmentStackEmpty() {
        return this.fragmentStack.empty();
    }

    public void killFragmentStack() {
        this.fragmentStack.removeAllElements();
    }

    public Fragment getFragmentFromStackOfSpecificPosition(int position) {
        if (fragmentStackSize() > position) {
            return this.fragmentStack.get(position);
        }
        return null;
    }

    public Fragment getFirstFragmentFromStack() {
        if (fragmentStackSize() > 0) {
            return this.fragmentStack.firstElement();
        }
        return null;
    }

    public boolean contains(Fragment fragment) {
        return fragmentStack.contains(fragment);
    }

    public void pauseFragmentFromStack(Fragment fragment) {
        this.fragmentStack.get(indexOfFragment(fragment)).onPause();
    }

    public int indexOfFragment(Fragment fragment) {
        return fragmentStack.indexOf(fragment);
    }

    public int getFragmentPositionFromStack(Fragment fragment) {
        return fragmentStack.indexOf(fragment);
    }

    public void deleteFragment(Fragment fragment) {
        fragmentStack.remove(fragment);
    }

    public void clear() {
        while (fragmentStack.size() > 0) {
            fragmentStack.pop();
        }
    }
}
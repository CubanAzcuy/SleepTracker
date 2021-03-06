package com.dev925.sleeptracker.features;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.dev925.sleeptracker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        getConfirmButton().check(matches(isDisplayed()));
        onView(withId(R.id.title)).check(matches(withText("Did you just wake up?")));
        getConfirmButton().perform(click());
        onView(withId(R.id.title)).check(matches(withText("Did you just wake up?")));

    }
    private ViewInteraction getConfirmButton() {
        return onView(withId(R.id.confirmButton));
    }
}

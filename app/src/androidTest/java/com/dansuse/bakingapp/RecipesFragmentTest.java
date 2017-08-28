package com.dansuse.bakingapp;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.dansuse.bakingapp.recipedetail.RecipeDetailActivity;
import com.dansuse.bakingapp.recipes.RecipesActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by LENOVO on 25/08/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipesFragmentTest  {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<RecipesActivity> mActivityRule = new IntentsTestRule<>(
            RecipesActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getSimpleIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickItem_openRecipeDetailActivity() {
        // First, scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.rv_recipe_card))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        intended(allOf(
                hasComponent(RecipeDetailActivity.class.getName())));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void checkSecondItemInRecyclerView(){
        onView(withRecyclerView(R.id.rv_recipe_card).atPosition(1))
                .check(matches(hasDescendant(withText("Brownies"))));
    }

    @Test
    public void checkRecyclerViewItemCount(){
        onView(ViewMatchers.withId(R.id.rv_recipe_card)).check(new RecyclerViewItemCountAssertion(12));
    }

//    @Test
//    public void checkVideoShowUp(){
//        onView(ViewMatchers.withId(R.id.rv_recipe_card))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
//                        click()));
//        onView(ViewMatchers.withId(R.id.rv_recipe_detail))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
//                        click()));
////        onView(allOf(withId(R.id.playerView), isDescendantOfA(firstChildOf(withId(R.id.view_pager)))))
////                .check(matches(isCompletelyDisplayed()));
//        onView(allOf(withId(R.id.playerView), isCompletelyDisplayed())).check(matches(isCompletelyDisplayed()));
//    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with first child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {

                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(0).equals(view);

            }
        };
    }
}

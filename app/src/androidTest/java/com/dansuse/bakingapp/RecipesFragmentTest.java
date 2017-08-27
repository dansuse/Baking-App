package com.dansuse.bakingapp;

import android.app.Activity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.dansuse.bakingapp.recipedetail.RecipeDetailActivity;
import com.dansuse.bakingapp.recipes.RecipesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by LENOVO on 25/08/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipesFragmentTest  {
    @Rule
    public IntentsTestRule<RecipesActivity> mActivityRule = new IntentsTestRule<>(
            RecipesActivity.class);

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
}

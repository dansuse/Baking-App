package com.dansuse.bakingapp.common.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Daniel on 20/08/2017.
 */

public interface Presenter {
    /**
     * Starts the presentation. This should be called in the view's (Activity or Fragment)
     * onCreate() or onViewStatedRestored() method respectively.
     *
     * @param savedInstanceState the saved instance state that contains state saved in
     *                           {@link #onSaveInstanceState(Bundle)}
     */
    void onStart(@Nullable Bundle savedInstanceState);

    /**
     * Resumes the presentation. This should be called in the view's (Activity or Fragment)
     * onResume() method.
     */
    void onResume();

    /**
     * Pauses the presentation. This should be called in the view's Activity or Fragment)
     * onPause() method.
     */
    void onPause();

    /**
     * Save the state of the presentation (if any). This should be called in the view's
     * (Activity or Fragment) onSaveInstanceState().
     *
     * @param outState the out state to save instance state
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * Ends the presentation. This should be called in the view's (Activity or Fragment)
     * onDestroy() or onDestroyView() method respectively.
     */
    void onEnd();
}

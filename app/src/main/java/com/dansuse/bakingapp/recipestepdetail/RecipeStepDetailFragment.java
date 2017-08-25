package com.dansuse.bakingapp.recipestepdetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dansuse.bakingapp.R;
import com.dansuse.bakingapp.common.view.BaseViewFragment;
import com.dansuse.bakingapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends BaseViewFragment<RecipeStepDetailContract.Presenter>
        implements RecipeStepDetailContract.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE_STEP = "RecipeStepDetailFragment.arg_recipe_step";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Step mStep;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Nullable
    @BindView(R.id.tv_step_description)
    TextView mStepDescriptionTextView;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_no_video_available)
    TextView mNoVideoAvailTextView;

    private Target mTarget;
    private SimpleExoPlayer mExoPlayer;
    private long mExoPlayerPosition = -1L;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeStepDetailFragment newInstance(Step step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    //onCreate dipanggil setelah onAttach
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE_STEP)) {
            mStep = getArguments().getParcelable(ARG_RECIPE_STEP);
            //Log.d("tes123", mStep.getShortDescription() + "| " + String.valueOf(presenter == null) + " |onCreate");
            presenter.setRecipeStep(mStep);
            presenter.setSDKInt(Util.SDK_INT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        viewUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mStep != null) {
            if(mStep.getDescription() != null && mStepDescriptionTextView != null){
                mStepDescriptionTextView.setText(mStep.getDescription());
            }
            if (mStep.getThumbnailURL() != null && !TextUtils.isEmpty(mStep.getThumbnailURL())) {
                mTarget = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mPlayerView.setDefaultArtwork(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(activityContext).load(mStep.getThumbnailURL()).into(mTarget);
            }
        }
    }

    @Override
    public void onPause() {
        //pengecekan dibawah karena kita menggunakan viewpager di activity,
        // dan viewpager akan menginisiasi fragment di kanan dan kiri dari fragment yang aktif.
        // Dimana fragment tetangga bisa jadi memiliki player yang sudah di init ataupun belum (blm init shg bernilai null).
        //oleh karena itu karena ketidakpastian ini, alagkah baiknya memberi pengecekan.
        if(presenter != null && mExoPlayer != null){
            presenter.setExoPlayerPosition(mExoPlayer.getCurrentPosition());
        }
        //Log.d("tes123", "Fragment| " + String.valueOf(mStep.getShortDescription()) + " |onPause");
        super.onPause();
    }

    @Override
    public void initializePlayer(Uri mediaUri) {
        Log.d("tes123", "Fragment| " + String.valueOf(mStep.getShortDescription()) + " |initializePlayer");
        mPlayerView.requestFocus();
        if (mExoPlayer == null) {
            // 1. Create a default TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            mExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(activityContext, trackSelector);

            mPlayerView.setPlayer(mExoPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activityContext,
                    Util.getUserAgent(activityContext, activityContext.getString(R.string.app_name)), (TransferListener<? super DataSource>) bandwidthMeter);
            // Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource(mediaUri,
                    dataSourceFactory, extractorsFactory, null, null);
            // Prepare the player with the source.
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
            if(mExoPlayerPosition != -1L){
                mExoPlayer.seekTo(mExoPlayerPosition);
            }
        }
    }

    @Override
    public void setExoPlayerPosition(long position) {
        mExoPlayerPosition = position;
        Log.d("tes123", "Fragment| " + String.valueOf(mStep.getShortDescription()) + " |setExoPlayerPosition");
    }

    @Override
    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void showExoPlayer() {
        mNoVideoAvailTextView.setVisibility(View.INVISIBLE);
        mPlayerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideExoPlayer() {
        mNoVideoAvailTextView.setVisibility(View.VISIBLE);
        mPlayerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isExoPlayerNull() {
        return mExoPlayer == null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void showMessageNoInternetConnection() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showMessageNoDataAvailable() {

    }
}

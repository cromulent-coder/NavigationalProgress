package com.example.navprogress;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NavigationalProgress extends LinearLayout {

    private Drawable navBack;
    private Drawable navForward;
    private ImageView imageNavBack;
    private ImageView imageNavForward;
    private Drawable[] progressDrawables;
    private ImageView[] progressImages;
    private int progressTabNum;

    //layout parameters
    private LinearLayout.LayoutParams layoutParams;

    public NavigationalProgress(Context context) {
        super(context);
        init(null);
    }

    public NavigationalProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NavigationalProgress,
                0, 0);
        init(typedArray);
    }

    public NavigationalProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NavigationalProgress,
                0, 0);
        init(typedArray);
    }


    private void init(final TypedArray arr) {

        this.setBackground(null);
        this.setOrientation(HORIZONTAL);

        ColorStateList colors = getResources()
                                .getColorStateList(R.color.color_click_states,
                                        getContext().getTheme());

        if(arr!=null) {
            navBack = arr.getDrawable(R.styleable.NavigationalProgress_navBack);
            navForward = arr.getDrawable(R.styleable.NavigationalProgress_navForward);
            progressTabNum = arr.getInteger(R.styleable.NavigationalProgress_progressTabNum, 1);
            progressImages = new ImageView[progressTabNum];
            progressDrawables = new Drawable[progressTabNum];
            /*this.setWeightSum(progressTabNum + 2);*/
            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f);

            if(navBack!=null){
                navBack = DrawableCompat.wrap(navBack);
                DrawableCompat.setTintList(navBack, colors);
                imageNavBack = new ImageView(getContext());
                imageNavBack.setLayoutParams(layoutParams);
                imageNavBack.setImageDrawable(navBack);
                //imageNavBack.setClickable(true);
                imageNavBack.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            if(navForward!=null){
                navForward = DrawableCompat.wrap(navForward);
                DrawableCompat.setTintList(navForward, colors);
                imageNavForward = new ImageView(getContext());
                imageNavForward.setLayoutParams(layoutParams);
                imageNavForward.setImageDrawable(navForward);
                //imageNavForward.setClickable(true);
                imageNavForward.setScaleType(ImageView.ScaleType.FIT_XY);
            }

            initProgress(arr);
            arr.recycle();
            setProgress(progressTabNum/2);
        }

        /*setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // TODO: Detect the touch in RTL or LTR layout direction.
                    // Detect the touch in RTL or LTR layout direction.
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = mButtonImage.getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mButtonImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }
                    // TODO: Check for actions if the button is tapped.
                    // Check for actions if the button is tapped.
                    if (isClearButtonClicked) {
                        // Check for ACTION_DOWN (always occurs before ACTION_UP).
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Switch to the black version of clear button.
                            mButtonImage = arr.getDrawable(R.styleable.EditTextWithButton_imageTag);
                            showButtonImage();
                        }
                        // Check for ACTION_UP.
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Switch to the opaque version of clear button.
                            mButtonImage = arr.getDrawable(R.styleable.EditTextWithButton_imageTag);
                            // Clear the text and hide the clear button.
                            getText().clear();
                            if(!mImageAlways) {
                                hideButtonImage();
                            }
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showButtonImage();
                if(textResults!=null){
                    textResults.textResult(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s==null || s.toString().equals("")){
                    if(!mImageAlways) {
                        hideButtonImage();
                    }
                    if(textResults!=null) {
                        textResults.textResult(null);
                    }
                }
            }
        });
        // TODO: If the clear (X) button is tapped, clear the text.
        // TODO: If the text changes, show or hide the clear (X) button.*/
    }

    private void addForward(){
        imageNavForward.setVisibility(VISIBLE);
    }

    private void addBack(){
        imageNavBack.setVisibility(VISIBLE);
    }

    private void removeForward(){
        imageNavForward.setVisibility(GONE);
    }

    private void removeBack(){
        imageNavBack.setVisibility(GONE);
    }

    private void initProgress(TypedArray arr){
        this.addView(imageNavBack);
        for(int i=0; i<progressImages.length; i++){
            progressDrawables[i] = arr.getDrawable(R.styleable.NavigationalProgress_progressTabImage);
            progressDrawables[i] = DrawableCompat.wrap(progressDrawables[i]);
            DrawableCompat.setTintList(progressDrawables[i], getResources()
                    .getColorStateList(R.color.color_progress,
                            getContext().getTheme()));
            progressImages[i] = new ImageView(getContext());
            progressImages[i].setLayoutParams(layoutParams);
            progressImages[i].setImageDrawable(progressDrawables[i]);
            this.addView(progressImages[i]);
            progressImages[i].setSelected(false);
            progressImages[i].setScaleType(ImageView.ScaleType.FIT_XY);
            progressImages[i].setClickable(true);
        }
        this.addView(imageNavForward);
    }

    private void setProgress(int till){
        till = till%progressTabNum;
        for(int j=0; j<=till; j++){
            progressImages[j].setSelected(true);
        }
    }



}

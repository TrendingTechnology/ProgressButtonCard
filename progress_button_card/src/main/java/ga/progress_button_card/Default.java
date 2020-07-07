package ga.progress_button_card;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.Arrays;


public class Default extends CardView {
    boolean loading = false;
    LinearLayout linearLayout;
    TextView textView;
    ProgressBar progressBar;
    String PBCText;
    float PBCTextSize;
    int PBCTextStyle;
    String PBCTextColor;
    float PBCRadius;



    private static final int[] PBC_TEXT = {R.attr.PBC_Text};
    private static final int[] PBC_TEXT_COLOR = {R.attr.PBC_TextColor};
    private static final int[] PBC_TEXT_STYLE = {R.attr.PBC_TextStyle};
    private static final int[] PBC_RADIUS = {R.attr.PBC_Radius};

    public Default(Context context) {

        super(context);

    }


    public Default(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setClickable(true);

        CardView.LayoutParams cwParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(cwParams);
        addView(linearLayout);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL;

        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(params);

        textView.setIncludeFontPadding(false);
        linearLayout.addView(textView);
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(GONE);
        progressBar.setLayoutParams(params);

        linearLayout.addView(progressBar);

        int[] attrsArray = new int[] {
                android.R.attr.layout_height,
        };


        //get view height, and set text size
        TypedArray ta0 = context.obtainStyledAttributes(attrs,  attrsArray, 0, 0);

        try {

            int tmpSize = ta0.getLayoutDimension(0, 1);

            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

            int dp = Math.round(tmpSize / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

            progressBar.setPadding(dp / 2,dp / 2,dp / 2,dp / 2);
            textView.setPadding(dp / 2,dp / 2,dp / 2,dp / 2);

            PBCTextSize = (dp / 2) - (dp / 9);

        }finally {
            ta0.recycle();
        }

        //get custom attributes
        TypedArray ta = context.obtainStyledAttributes(attrs,  R.styleable.Default, 0, 0);
        try {

            PBCRadius = ta.getDimension(R.styleable.Default_PBC_Radius,0);

            PBCText = ta.getString(R.styleable.Default_PBC_Text);
            PBCTextColor = ta.getString(R.styleable.Default_PBC_TextColor);
            PBCTextStyle = ta.getInt(R.styleable.Default_PBC_TextStyle,0);

            textView.setText(PBCText);
            textView.setTextSize(PBCTextSize);


             switch (PBCTextStyle){
                    case 1:
                        textView.setTypeface(textView.getTypeface(),Typeface.BOLD);
                        break;
                    case 2:
                        textView.setTypeface(textView.getTypeface(),Typeface.ITALIC);
                        break;
                    default:
                        textView.setTypeface(textView.getTypeface(),Typeface.NORMAL);
                        break;
             }



            //set highlight color depend on background tint
            if(getBackgroundTintList() != null){
                if(isWhite(getBackgroundTintList().getColorForState(new int[]{android.R.attr.state_enabled},Color.parseColor("#ffffff")))){
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.ripple_dark));
                }
                else{
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.ripple_light));

                }
            }
            else{
                if(getCardBackgroundColor() != null){
                    if(isWhite(getCardBackgroundColor().getColorForState(new int[]{android.R.attr.state_enabled},Color.parseColor("#ffffff")))){
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.ripple_dark));
                    }
                    else{
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.ripple_light));

                    }
                }
                else{
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.ripple_dark));
                }


            }



            textView.setTextColor(PBCTextColor == null ? Color.BLACK : Color.parseColor(PBCTextColor));

            progressBar.getIndeterminateDrawable()
                    .setColorFilter(PBCTextColor == null ? Color.BLACK : Color.parseColor(PBCTextColor), PorterDuff.Mode.SRC_IN );

            setRadius(PBCRadius);
            refreshDrawableState();

        } finally {
            ta.recycle();
        }


    }

    public Default(Context context, @Nullable AttributeSet attrs, int defStyle){

        super(context, attrs, defStyle);

    }

    public void Dispose(){

        setOnTouchListener(null);
        setOnClickListener(null);

    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {

        final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);
        mergeDrawableStates(drawableState, PBC_TEXT);
        mergeDrawableStates(drawableState, PBC_TEXT_COLOR);
        mergeDrawableStates(drawableState, PBC_TEXT_STYLE);
        mergeDrawableStates(drawableState, PBC_RADIUS);
        return drawableState;
    }

    public void loading(){
        loading = true;
        this.textView.setVisibility(GONE);
        this.progressBar.setVisibility(VISIBLE);
        this.setClickable(false);
        refreshDrawableState();
    }

    public void notLoading(){
        loading = false;
        this.textView.setVisibility(VISIBLE);
        this.progressBar.setVisibility(GONE);
        this.setClickable(true);
        refreshDrawableState();
    }

    //if color is white or not
    public boolean isWhite(int color){
        double rgb = (Color.red(color) + Color.green(color) + Color.blue(color));
        if(rgb > 700){
            return true;
        }else{
            return false;
        }
    }
}

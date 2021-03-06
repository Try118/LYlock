package com.LY.project.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LY.project.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MyFastMenuBar extends LinearLayout {
    private LayoutInflater inflater;
    private SparseArray<View> views;
    private View fmb_view;

    private LinearLayout fmb_menu;
    private ImageView fmb_menu_icon;
    private ImageView fmb_menu_arrow;
    private TextView fmb_menu_title;
    private TextView fmb_menu_message;
    private View fmb_menu_top_line;
    private View fmb_menu_bot_line;

    private Drawable fmb_icon;
    private Drawable fmb_arrow;
    private String fmb_title;
    private String fmb_message;
    private boolean fmb_icon_enable;
    private boolean fmb_arrow_enable;
    private boolean fmb_title_enable;
    private boolean fmb_message_enable;
    private boolean fmb_top_line_enable;
    private boolean fmb_bot_line_enable;
    private float fmb_bot_line_margin;
    private int fmb_title_color;
    private int fmb_message_color;
    private int fmb_animation_type;

    interface TYPE {
        int TYPE_NONE = 0x00;
        int TYPE_NORMAL = 0x01;
        int TYPE_FADE = 0x02;
        int TYPE_JUMP = 0x03;
    }

    public MyFastMenuBar(Context context) {
        this(context, null);
    }

    public MyFastMenuBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFastMenuBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initRootViews(context);
        initAttrs(context, attrs, defStyleAttr);
        initViews();
        addAnimation();
    }

    private void initRootViews(Context context) {
        views = new SparseArray<>();
        inflater = LayoutInflater.from(context);
        fmb_view = inflater.inflate(R.layout.menu_bar_normal, null);
        addView(fmb_view);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FastMenuBar, defStyleAttr, 0);
        fmb_icon = ta.getDrawable(R.styleable.FastMenuBar_fmb_icon);
        fmb_arrow = ta.getDrawable(R.styleable.FastMenuBar_fmb_arrow);
        fmb_title = ta.getString(R.styleable.FastMenuBar_fmb_title);
        fmb_message = ta.getString(R.styleable.FastMenuBar_fmb_message);
        fmb_icon_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_icon_enable, true);
        fmb_arrow_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_arrow_enable, true);
        fmb_title_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_title_enable, true);
        fmb_message_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_message_enable, true);
        fmb_top_line_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_top_line_enable, false);
        fmb_bot_line_enable = ta.getBoolean(R.styleable.FastMenuBar_fmb_bot_line_enable, true);
        fmb_bot_line_margin = ta.getDimension(R.styleable.FastMenuBar_fmb_bot_line_margin, 0);
        fmb_title_color = ta.getColor(R.styleable.FastMenuBar_fmb_title_color, 0);
        fmb_message_color = ta.getColor(R.styleable.FastMenuBar_fmb_message_color, 0);
        fmb_animation_type = ta.getInteger(R.styleable.FastMenuBar_fmb_animation_type, TYPE.TYPE_NONE);
        ta.recycle();
    }

    private void initViews() {
        fmb_menu = getView(R.id.fmb_menu);
        fmb_menu_icon = getView(R.id.fmb_menu_icon);
        fmb_menu_arrow = getView(R.id.fmb_menu_arrow);
        fmb_menu_title = getView(R.id.fmb_menu_title);
        fmb_menu_message = getView(R.id.fmb_menu_message);
        fmb_menu_top_line = getView(R.id.fmb_menu_top_line);
        fmb_menu_bot_line = getView(R.id.fmb_menu_bot_line);

        if (fmb_icon != null) {
            fmb_menu_icon.setImageDrawable(fmb_icon);
        }
        if (fmb_arrow != null) {
            fmb_menu_arrow.setImageDrawable(fmb_arrow);
        }
        if (fmb_title != null) {
            fmb_menu_title.setText(fmb_title);
        }
        if (fmb_message != null) {
            fmb_menu_message.setText(fmb_message);
        }
        if (fmb_title_color != 0) {
            fmb_menu_title.setTextColor(fmb_title_color);
        }
        if (fmb_message_color != 0) {
            fmb_menu_message.setTextColor(fmb_message_color);
        }
        if (!fmb_icon_enable) {
            fmb_menu_icon.setVisibility(GONE);
        }
        if (!fmb_arrow_enable) {
            fmb_menu_arrow.setVisibility(GONE);
        }
        if (!fmb_title_enable) {
            fmb_menu_title.setVisibility(GONE);
        }
        if (!fmb_message_enable) {
            fmb_menu_message.setVisibility(GONE);
        }
        if (!fmb_bot_line_enable) {
            fmb_menu_bot_line.setVisibility(INVISIBLE);
        }
        if (fmb_top_line_enable) {
            fmb_menu_top_line.setVisibility(VISIBLE);
        }
        if (fmb_bot_line_enable) {
            LayoutParams lp = (LayoutParams) fmb_menu_bot_line.getLayoutParams();
            lp.setMargins((int) fmb_bot_line_margin, 0, 0, 0);
            fmb_menu_bot_line.setLayoutParams(lp);
        }
    }


    private void addAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        if (fmb_animation_type == TYPE.TYPE_FADE) {
            animationSet.addAnimation(createFadeAnimation());
        } else if (fmb_animation_type == TYPE.TYPE_JUMP) {
            animationSet.addAnimation(createJumpAnimation());
        } else if (fmb_animation_type == TYPE.TYPE_NORMAL) {
            animationSet.addAnimation(createFadeAnimation());
            animationSet.addAnimation(createJumpAnimation());
        }
        startAnimation(animationSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fmb_menu.setBackgroundColor(Color.LTGRAY);
                break;
            case MotionEvent.ACTION_MOVE:
                fmb_menu.setBackgroundColor(Color.LTGRAY);
                break;
            case MotionEvent.ACTION_UP:
                fmb_menu.setBackgroundColor(Color.WHITE);

                if (listener != null) {
                    listener.onMenuBarClick(this);
                }
                break;
        }
        return true;
    }

    private onMenuBarClickListener listener;

    public void setOnMenuBarClickListener(onMenuBarClickListener listener) {
        this.listener = listener;
    }

    public interface onMenuBarClickListener {
        void onMenuBarClick(MyFastMenuBar view);
    }

    private AlphaAnimation createFadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    private TranslateAnimation createJumpAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 40, 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public String getMenuBarTitle() {
        return fmb_title;
    }

    public String getMenuBarMessage() {
        return fmb_message;
    }

    public void setMenuBarTitle(String title) {
        fmb_menu_title.setText(title);
    }

    public void setMenuBarMessage(String message) {
        fmb_menu_message.setText(message);
    }

    private <T> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = fmb_view.findViewById(viewId);
            if (view != null) {
                views.put(viewId, view);
            }
        }
        return (T) view;
    }

}

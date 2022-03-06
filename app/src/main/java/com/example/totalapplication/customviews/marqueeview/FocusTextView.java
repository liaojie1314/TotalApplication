package com.example.totalapplication.customviews.marqueeview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
/*布局
<com.example.totalapplication.customviews.marqueeview.FocusTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ellipsize="marquee"
        android:focusable="true"
        android:marqueeRepeatLimit="marquee_forever"
        android:singleLine="true"
        android:text="hello world!hello world!hello world!hello world!hello world!hello world!hello world!hello world!hello world!hello world!" />
 */
public class FocusTextView extends AppCompatTextView {
    public FocusTextView(Context context) {
        this(context, null);
    }

    public FocusTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

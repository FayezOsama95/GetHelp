package com.am.gethelp;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimelineSimple extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_simple);
        initComponent();

        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                showCustomDialog();
            }
        });
    }



    private void initComponent() {
        NestedScrollView nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateFab(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateFab(true);
                }
            }
        });
    }

    boolean isFabHide = false;
    private void animateFab(final boolean hide) {
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        if (isFabHide && hide || !isFabHide && !hide) return;
        isFabHide = hide;
        int moveY = hide ? (2 * fab_add.getHeight()) : 0;
        fab_add.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }




    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_post);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        final AppCompatButton bt_submit = dialog.findViewById(R.id.bt_submit);
        ((EditText) dialog.findViewById(R.id.et_post)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bt_submit.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Post Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }




}

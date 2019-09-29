package com.bobomee.android.mentionedittextdemo.text.parser;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.bobomee.android.mentionedittextdemo.edit.User;
import com.bobomee.android.mentions.text.listener.ParserConverter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class CustomParser implements ParserConverter {
    private static final String TAG = "CustomParser";

    public CustomParser() {
    }

    @Override
    public Spanned convert(CharSequence source) {
        if (TextUtils.isEmpty(source)) return new SpannableString("");


        String result = source.toString();

        String[] strs = result.split(User.Separator);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (final String str : strs) {

            JSONObject data = getData(str);
            if (data == null) {
                spannableStringBuilder.append(str);
            } else {
                String username = "@"+ data.optString("name");
                SpannableString spannableString = new SpannableString(username);
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Log.e(TAG, "onClick: 111" );
                        Log.e(TAG, "onClick: " + str);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setColor(Color.parseColor("#ffff0000")); //设置颜色
                    }
                }, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                spannableStringBuilder.append(spannableString);
            }
        }
        return spannableStringBuilder;
    }


    public JSONObject getData(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}

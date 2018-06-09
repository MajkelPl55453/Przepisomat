package pl.przepisomat.przepisomat.components;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;

public class BulletTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String SPLITTER_CHAR = "--";
    private static final String NEWLINE_CHAR = "<br/>";
    private static final String HTML_BULLETPOINT = "&#8226;";

    public BulletTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BulletTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        checkForBulletPointSplitter();
    }

    private void checkForBulletPointSplitter() {
        String text = (String) getText();
        if (text.contains(SPLITTER_CHAR)) {
            injectBulletPoints(text);
        }
    }

    public void injectBulletPoints(String text) {
        String newLinedText = addNewLinesBetweenBullets(text);
        String htmlBulletText = addBulletPoints(newLinedText);
        setText(Html.fromHtml(htmlBulletText));
    }

    private String addNewLinesBetweenBullets(String text) {
        String newLinedText = text.replace(SPLITTER_CHAR, NEWLINE_CHAR + SPLITTER_CHAR);
        newLinedText = newLinedText.replaceFirst(NEWLINE_CHAR, "");
        return newLinedText;
    }

    private String addBulletPoints(String newLinedText) {
        return newLinedText.replace(SPLITTER_CHAR, HTML_BULLETPOINT);
    }

}


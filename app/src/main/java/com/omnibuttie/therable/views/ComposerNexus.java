package com.omnibuttie.therable.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormActivity;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.LabeledFieldController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.omnibuttie.therable.R;

import java.util.Arrays;

public class ComposerNexus extends FormActivity {

    private final static String MOOD_INDEX = "moodIndex";
    private final static String INTENSITY = "intensity";
    private final static String CAUSE = "cause";
    private final static String CONTENT = "content";

    @Override
    protected void initForm() {
        FormSectionController section = new FormSectionController(this, "Care to elaborate");
        MoodButton moodButton = new MoodButton(this, MOOD_INDEX, "How do you feel", true);
        moodButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        section.addElement(moodButton);
        EditTextController editTextController = new EditTextController(this, CONTENT, "Why?", "Why?", true);
        editTextController.setMultiLine(true);

        section.addElement(editTextController);
        section.addElement(new SelectionController(this, CAUSE, "Cause?", false, "Select", Arrays.asList("nothing", "work", "family", "friends", "other people"), true));

        addSection(section);

        setTitle("Care to elaborate?");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.composer_nexus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

class MoodButton extends LabeledFieldController {
    MoodButton(Context ctx, String name, String labelText, boolean isRequired) {
        super(ctx, name, labelText, isRequired);
    }

    @Override
    protected View createFieldView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(R.layout.composer_moodbutton, null);
    }

    public Button getButton() {
        return (Button) getView().findViewById(R.id.btn);
    }

    @Override
    public void refresh() {

    }
}

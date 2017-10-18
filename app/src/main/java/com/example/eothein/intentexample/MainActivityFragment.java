package com.example.eothein.intentexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

/**
 * The fragment containing the Buttons which will send their corresponding intents.
 */
public class MainActivityFragment extends Fragment {

    /**
     * Id's for the activities which return results
     */
    private static final int PICK_CONTACT = 1;
    private static final int PICK_NUMBER = 2;
    private static final int SPEECH_INPUT = 3;


    /**
     * Validator for the EditText (https://github.com/thyrlian/AwesomeValidation)
     */
    private AwesomeValidation mAwesomeValidation;

    /**
     * The open contacts button (example, is not realy needed in this implementation)
     */
    @BindView(R.id.button_contacts)
    Button contactButton;

    /**
     * The Edittext where the URL should be inserted
     */
    @BindView(R.id.url_text)
    EditText urlText;


    /**
     * The result is shown in this textview when the speech option is selected.
     */
    @BindView(R.id.txt_result)
    TextView txtResult;

    /**
     * Will set the view to null in onDestroyView()
     */
    private Unbinder unbinder;


    public MainActivityFragment() {
    }


    /**
     * Creates the view. See the Fragment Life Cycle.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    /**
     * Is called when the underlying Activity has been created.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(getActivity());  // mandatory for UNDERLABEL style
        mAwesomeValidation.addValidation(getActivity(),R.id.url_text, Patterns.WEB_URL,R.string.url);
    }

    /**********************************************************************************
     * METHOD ASSOCIATED WITH THE BUTTONS
     * ********************************************************************************
     */
    
    /**
     *
     * Opens the contact app from the phone
     */
    @OnClick(R.id.button_contacts)
    public void startContact(){
        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * Open the dialer for the phone
     */
    @OnClick(R.id.button_dialer)
    public void startDialer(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivityForResult(intent, PICK_NUMBER);
    }


    /**
     * Start the browser if a valid URL has been provided.
     */
    @OnClick(R.id.button_url)
    public void startBrowser(){
        //Test whether the inserted text is an URL
       if(mAwesomeValidation.validate()){
           Uri uri = Uri.parse(urlText.getText().toString());
           Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
           startActivity(myIntent);
       }

    }

    /**
     * Open the Google search app
     */
    @OnClick(R.id.button_google)
    public void goToGoogleSearch() {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        startActivity(intent);
    }

    /**
     * Start the activity which is able to interpret text
     */
    @OnClick(R.id.button_speak)
    public void startSpeak(){
        startSpeech();

    }

    /**
     * Start the speech application. (https://developer.android.com/reference/android/speech/RecognizerIntent.html)
     */
    private void startSpeech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // takes user’s speech input and returns it
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // Considers input in free form English
        startActivityForResult(intent, SPEECH_INPUT);
    }


    /**
     *  Allows the fragment to clean up resources associated with its View. (Memory Management)
     */

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Listening to results provided by other activities.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_CONTACT:
                    Log.i(this.getClass().getName(), "Received Contact");
                    Toast.makeText(getActivity(),"Received Contact",Toast.LENGTH_LONG).show();
                    break;
                case PICK_NUMBER:
                    Log.i(this.getClass().getName(), "Received Number");
                    Toast.makeText(getActivity(),"Received number",Toast.LENGTH_LONG).show();
                    break;
                case SPEECH_INPUT:
                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtResult.setText(result.get(0));
                    break;
            }
        }
    }
}

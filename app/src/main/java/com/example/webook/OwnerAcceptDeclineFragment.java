package com.example.webook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.CreateDocumentRequest;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class OwnerAcceptDeclineFragment extends DialogFragment {
    private TextView BookRequestTitleText;
    private TextView BookRequestAuthorText;
    private TextView BookRequestOwnerText;
    private TextView BookRequestRequesteeText;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onAcceptPressed();
        void onDeclinePressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public static OwnerAcceptDeclineFragment newInstance(BookRequest selectRequest, int position) {
        OwnerAcceptDeclineFragment fragment = new OwnerAcceptDeclineFragment();
        Bundle args = new Bundle();
        args.putSerializable("selectRequest", selectRequest);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_owner_accept_decline, null);
//        if (getArguments() != null) {
//        }
        final BookRequest selectRequest = (BookRequest) getArguments().getSerializable("selectRequest");
        final int position = getArguments().getInt("position");
        BookRequestTitleText = view.findViewById(R.id.book_request_title_text);
        BookRequestAuthorText = view.findViewById(R.id.book_request_author_text);
        BookRequestOwnerText = view.findViewById(R.id.book_request_owner_text);
        BookRequestRequesteeText = view.findViewById(R.id.book_request_requestee_text);

        BookRequestTitleText.setText("Title: " + selectRequest.getBook().getTitle());
        BookRequestAuthorText.setText("Author: " + selectRequest.getBook().getAuthor());
        BookRequestOwnerText.setText("Owner: " + selectRequest.getRequestee());
        BookRequestRequesteeText.setText("Borrower: " + selectRequest.getRequester().get(position));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNeutralButton("Cancel", null)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<String> acceptRequester = new ArrayList<>();
                        acceptRequester.add(selectRequest.getRequester().get(position));
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("requests").document(selectRequest.getBook().getISBN())
                                .update(
                                        "requester", acceptRequester
                                );
                        db.collection("books").document(selectRequest.getBook().getISBN())
                                .update(
                                        "status", "accepted"
                                );
                        listener.onAcceptPressed();
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference document = db
                                .collection("requests")
                                .document(selectRequest.getBook().getISBN());
                        document.update("requester", FieldValue.arrayRemove(selectRequest.getRequester().get(position)));
                        listener.onDeclinePressed();
                    }
                })
                .create();
    }
}
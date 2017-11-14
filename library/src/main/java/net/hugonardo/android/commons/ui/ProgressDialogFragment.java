package net.hugonardo.android.commons.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;

public class ProgressDialogFragment extends AppCompatDialogFragment {

    public interface Callbacks {

        void onProgressCancelled(int id);
    }

    public static final String ARG_ID = "id";

    public static final String ARG_MESSAGE = "message";

    public static final String ARG_TITLE = "title";

    public static final String ARG_CANCELLABLE = "cancellable";

    @Nullable
    private Callbacks mCallbacks;

    private int mId;

    private int mMessage;

    private int mTitle;

    public static ProgressDialogFragment newInstance(int id, int title, int message, boolean cancellable) {
        ProgressDialogFragment frag = new ProgressDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putInt(ARG_TITLE, title);
        args.putInt(ARG_MESSAGE, message);
        args.putBoolean(ARG_CANCELLABLE, cancellable);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mId = args.getInt(ARG_ID);
        mTitle = args.getInt(ARG_TITLE);
        mMessage = args.getInt(ARG_MESSAGE);
        setCancelable(args.getBoolean(ARG_CANCELLABLE, true));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mCallbacks != null) {
            mCallbacks.onProgressCancelled(mId);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progress = new ProgressDialog(getActivity());
        if (mTitle != 0) {
            progress.setTitle(mTitle);
        }
        if (mMessage != 0) {
            progress.setMessage(getText(mMessage));
        }
        progress.setIndeterminate(true);
        return progress;
    }

    public void setCallbacks(@Nullable Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public void setMessage(int message) {
        mMessage = message;
        ProgressDialog progress = (ProgressDialog) getDialog();
        progress.setMessage(getString(message));
    }

    public void setTitle(int title) {
        mTitle = title;
        ProgressDialog progress = (ProgressDialog) getDialog();
        progress.setTitle(title);
    }

}

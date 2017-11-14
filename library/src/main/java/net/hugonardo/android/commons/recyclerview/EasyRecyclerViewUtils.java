package net.hugonardo.android.commons.recyclerview;

import android.support.annotation.Nullable;
import com.jude.easyrecyclerview.EasyRecyclerView;

public class EasyRecyclerViewUtils {

    public static void showEmpty(@Nullable EasyRecyclerView recyclerView) {
        if (recyclerView != null && !isEmptyShown(recyclerView)) {
            recyclerView.showEmpty();
        }
    }

    public static void showError(@Nullable EasyRecyclerView recyclerView) {
        if (recyclerView != null && !isErrorShown(recyclerView)) {
            recyclerView.showError();
        }
    }

    public static void showList(@Nullable EasyRecyclerView recyclerView) {
        if (recyclerView != null && !isListShown(recyclerView)) {
            recyclerView.showRecycler();
        }
    }

    public static void showProgress(@Nullable EasyRecyclerView recyclerView) {
        if (recyclerView != null && !isProgressShown(recyclerView)) {
            recyclerView.showProgress();
        }
    }

    private static boolean isEmptyShown(@Nullable EasyRecyclerView recyclerView) {
        return recyclerView != null && recyclerView.getRecyclerView() != null
                && recyclerView.getEmptyView().isShown();
    }

    private static boolean isErrorShown(@Nullable EasyRecyclerView recyclerView) {
        return recyclerView != null && recyclerView.getRecyclerView() != null
                && recyclerView.getErrorView().isShown();
    }

    private static boolean isListShown(@Nullable EasyRecyclerView recyclerView) {
        return recyclerView != null && recyclerView.getRecyclerView() != null
                && recyclerView.getRecyclerView().isShown();
    }

    private static boolean isProgressShown(@Nullable EasyRecyclerView recyclerView) {
        return recyclerView != null && recyclerView.getRecyclerView() != null
                && recyclerView.getProgressView().isShown();
    }
}

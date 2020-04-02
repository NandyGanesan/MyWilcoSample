package com.android.wilcoconnect.network_interface;

import android.view.View;

import com.android.wilcoconnect.model.leave.ApprovePost;
import com.android.wilcoconnect.model.leave.Onduty.OnDutyApprovePost;

public interface RecyclerViewListener {

    void onClick(View view, String value);

    void onClick(View view, ApprovePost post);

    void OnStore(View view, OnDutyApprovePost postData);

}

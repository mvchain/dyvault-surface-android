package com.mvc.topay.and.topay_android.listener;

/***
 *  Callback for the verification code countdown
 */
public interface OnTimeEndCallBack {
    //update time
    void updata(int time);
    
    //End of time
    void exit();
}

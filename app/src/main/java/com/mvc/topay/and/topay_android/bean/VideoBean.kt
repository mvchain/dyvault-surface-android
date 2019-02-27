package com.mvc.topay.and.topay_android.bean

/**
 * Created by admin on 2017/3/15.
 */

class VideoBean {

    /**
     * code : 200
     * message :
     * data : {"video_analysis_url":"http://mvvideo2.meitudata.com/58946a189b38d280.mp4"}
     */
    var code: Int = 0
    var message: String? = null
    var data: DataBean? = null

    class DataBean {
        /**
         * video_analysis_url : http://mvvideo2.meitudata.com/58946a189b38d280.mp4
         */

        var video_analysis_url: String? = null
    }
}

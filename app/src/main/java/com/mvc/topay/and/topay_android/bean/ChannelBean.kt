package com.mvc.topay.and.topay_android.bean

data class ChannelBean(

        /**
         * code : 0
         * data : [{"channelName":"string","contact":"string","createdAt":0,"id":0,"info":"string","updatedAt":0}]
         * message : string
         */

        var code: Int,
        var message: String,
        var data: ArrayList<DataBean>
) {
    data class DataBean(
            /**
             * channelName : string
             * contact : string
             * createdAt : 0
             * id : 0
             * info : string
             * updatedAt : 0
             */

            var channelName: String,
            var contact: String,
            var createdAt: Long,
            var id: Int,
            var info: String,
            var updatedAt: Long
    )
}

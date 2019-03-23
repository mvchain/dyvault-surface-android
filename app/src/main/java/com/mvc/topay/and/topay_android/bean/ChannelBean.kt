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

            var amount: Double,
            var createdAt: Long,
            var id: Int,
            var orderNumber: String,
            var orderStatus: Int,
            var orderType: Int,
            var stopAt: Long,
            var tokenId: String,
            var tokenName: String,
            var tokenValue: Double,
            var userId: Int
    )
}

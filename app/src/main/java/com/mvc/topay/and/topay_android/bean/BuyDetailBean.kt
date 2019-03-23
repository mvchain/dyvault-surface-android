package com.mvc.topay.and.topay_android.bean

data class BuyDetailBean(
        /**
         * code : 0
         * data : {"amount":0,"buyUserId":0,"buyUsername":"string","createdAt":0,"id":0,"limitTime":0,"orderNumber":"string","orderStatus":0,"orderType":0,"payAccount":"string","payType":0,"price":0,"sellUserId":0,"sellUsername":"string","stopAt":0,"tokenId":"string","tokenName":"string","tokenValue":0,"userId":0}
         * message : string
         */

        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * amount : 0
             * buyUserId : 0
             * buyUsername : string
             * createdAt : 0
             * id : 0
             * limitTime : 0
             * orderNumber : string
             * orderStatus : 0
             * orderType : 0
             * payAccount : string
             * payType : 0
             * price : 0
             * sellUserId : 0
             * sellUsername : string
             * stopAt : 0
             * tokenId : string
             * tokenName : string
             * tokenValue : 0
             * userId : 0
             */
            var amount: Double,
            var buyUserId: Int,
            var buyUsername: String,
            var createdAt: Long,
            var id: Int,
            var limitTime: Long,
            var orderNumber: String,
            var orderStatus: Int,
            var orderType: Int,
            var payAccount: String,
            var payType: Int,
            var price: Double,
            var sellUserId: Int,
            var sellUsername: String,
            var stopAt: Int,
            var tokenId: String,
            var tokenName: String,
            var tokenValue: Double,
            var userId: Int
    )
}

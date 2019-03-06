package com.mvc.topay.and.topay_android.bean

data class DetailBean(
        /**
         * code : 0
         * data : {"blockHash":"string","classify":0,"createdAt":0,"fee":0,"feeTokenType":"string","fromAddress":"string","hashLink":"string","height":0,"orderRemark":"string","status":0,"toAddress":"string","tokenName":"string","transactionType":0,"updatedAt":0,"value":0}
         * message : string
         */
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * blockHash : string
             * classify : 0
             * createdAt : 0
             * fee : 0
             * feeTokenType : string
             * fromAddress : string
             * hashLink : string
             * height : 0
             * orderNumber :String
             * orderRemark : string
             * status : 0
             * toAddress : string
             * tokenName : string
             * transactionType : 0
             * updatedAt : 0
             * value : 0
             */
            var blockHash: String,
            var classify: Int,
            var createdAt: Long,
            var fee: Double,
            var feeTokenType: String,
            var fromAddress: String,
            var hashLink: String,
            var height: Int,
            var orderNumber: String,
            var orderRemark: String,
            var status: Int,
            var toAddress: String,
            var tokenName: String,
            var transactionType: Int,
            var updatedAt: Long,
            var value: Double
    )
}

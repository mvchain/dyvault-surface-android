package com.mvc.topay.and.topay_android.bean

data class TransactionsBean(
        /**
         * code : 0
         * data : [{"classify":0,"createdAt":0,"id":0,"orderRemark":"string","ratio":0,"status":0,"tokenId":0,"tokenName":"string","transactionType":0,"updatedAt":0,"value":0}]
         * message : string
         */
        var code: Int,
        var message: String,
        var data: ArrayList<DataBean>
) {
    data class DataBean(
            /**
             * classify : 0
             * createdAt : 0
             * id : 0
             * orderRemark : string
             * ratio : 0
             * status : 0
             * tokenId : 0
             * tokenName : string
             * transactionType : 0
             * updatedAt : 0
             * value : 0
             */
            var classify: Int,
            var createdAt: Long,
            var id: Int,
            var orderRemark: String,
            var ratio: Double,
            var status: Int,
            var tokenId: Int,
            var tokenName: String,
            var transactionType: Int,
            var updatedAt: Long,
            var value: Double
    )
}

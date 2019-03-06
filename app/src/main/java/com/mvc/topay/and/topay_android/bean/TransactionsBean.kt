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
             * fromAddress:601905931@qq.com
             * id : 0
             * orderRemark : string
             * ratio : 0
             * status : 0
             * toAddress:0x00c7c06e450ed64a6dbf388e08be3e999ff94563
             * tokenId : 0
             * tokenName : string
             * transactionType : 0
             * updatedAt : 0
             * value : 0
             */
            var classify: Int,
            var createdAt: Long,
            var fromAddress: String,
            var id: Int,
            var orderRemark: String,
            var ratio: Double,
            var status: Int,
            var tokenId: Int,
            var toAddress: String,
            var tokenName: String,
            var transactionType: Int,
            var updatedAt: Long,
            var value: Double
    )
}

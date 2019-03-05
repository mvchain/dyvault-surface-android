package com.mvc.topay.and.topay_android.bean

data class IDToTransferBean(
        /**
         * code : 0
         * data : {"balance":0,"fee":0,"feeTokenName":"string"}
         * message : string
         */
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * balance : 0
             * fee : 0
             * feeTokenName : string
             */
            var balance: Double,
            var fee: Double,
            var feeTokenName: String
    )
}

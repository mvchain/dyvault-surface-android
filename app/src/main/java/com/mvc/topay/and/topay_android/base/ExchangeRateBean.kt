package com.mvc.topay.and.topay_android.base

data class ExchangeRateBean(
        /**
         * code : 200
         * data : [{"name":"CNY","value":1},{"name":"USD","value":6.8939},{"name":"EUR","value":7.8284}]
         * message :
         */
        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
   data class DataBean(
            /**
             * name : CNY
             * value : 1
             */
            var name: String,
            var value: Double
    )
}

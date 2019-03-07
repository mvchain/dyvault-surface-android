package com.mvc.topay.and.topay_android.base


data class AssetLanguageBean(
        /**
         * code : 0
         * data : [{"ratio":0,"tokenId":0,"tokenImage":"string","tokenName":"string","value":0}]
         * message : string
         */
        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
  data  class DataBean(
            /**
             * ratio : 0
             * tokenId : 0
             * tokenImage : string
             * tokenCnName : string
             * tokenEnName : string
             * tokenName : string
             * value : 0
             */
            var ratio: Double,
            var tokenId: Int,
            var tokenImage: String,
            var tokenCnName: String,
            var tokenEnName: String,
            var tokenName: String,
            var value: Double
    )
}

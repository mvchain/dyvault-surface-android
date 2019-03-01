package com.mvc.topay.and.topay_android.base


data class CurrencyBean(
        /**
         * code : 200
         * data : [{"timestamp":0,"tokenCnName":"VRT","tokenEnName":"VRT","tokenId":1,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"VRT","tokenType":1},{"timestamp":0,"tokenCnName":"余额","tokenEnName":"Balance","tokenId":2,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"余额","tokenType":0},{"timestamp":0,"tokenCnName":"以太坊","tokenEnName":"ethernum","tokenId":3,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"ETH","tokenType":2},{"timestamp":0,"tokenCnName":"泰达币","tokenEnName":"USDT","tokenId":4,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"USDT","tokenType":2},{"timestamp":0,"tokenCnName":"JYWD","tokenEnName":"JYWD","tokenId":5,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"JYWD","tokenType":2}]
         * message :
         */
        var code: Int,
        var message: String,
        var data: List<DataBean>
){ data   class DataBean(
        /**
         * timestamp : 0
         * tokenCnName : VRT
         * tokenEnName : VRT
         * tokenId : 1
         * tokenImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg
         * tokenName : VRT
         * tokenType : 1
         */
        var timestamp: Int,
        var tokenCnName: String,
        var tokenEnName: String,
        var tokenId: Int,
        var tokenImage: String,
        var tokenName: String,
        var tokenType: Int,
        var visible: Int
)
}

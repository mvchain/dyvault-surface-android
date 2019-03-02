package com.mvc.topay.and.topay_android.bean

data class UserInfoBean(

        /**
         * code : 0
         * data : {"nickname":"string","username":"string"}
         * message : string
         */
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * nickname : string
             * username : string
             */
            var nickname: String,
            var username: String)

}

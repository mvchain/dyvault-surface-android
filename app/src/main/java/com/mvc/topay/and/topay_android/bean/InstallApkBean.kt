package com.mvc.topay.and.topay_android.bean

data class InstallApkBean(
        /**
         * code : 0
         * data : {"appPackage":"string","appType":"string","appVersion":"string","appVersionCode":0,"httpUrl":"string"}
         * message : string
         */

        var code: Int,
        var data: DataBean,
        var message: String
) {


    data class DataBean(
            /**
             * appPackage : string
             * appType : string
             * appVersion : string
             * appVersionCode : 0
             * httpUrl : string
             */
            var appPackage: String,
            var appType: String,
            var appVersion: String,
            var appVersionCode: Int,
            var httpUrl: String
    )
}

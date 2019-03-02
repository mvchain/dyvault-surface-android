package com.mvc.topay.and.topay_android.bean

data class MsgBean(
        /**
         * code : 200
         * data : [{"createdAt":1542358128486,"id":2,"message":"测试消息2","messageId":1,"messageType":"TEST","read":0,"status":1,"updatedAt":1542358128486},{"createdAt":1542358128485,"id":1,"message":"测试消息1","messageId":1,"messageType":"TEST","read":1,"status":1,"updatedAt":1542358128485}]
         * message :
         */
        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
    data class DataBean(
            /**
             * createdAt : 1542358128486
             * id : 2
             * message : 测试消息2
             * messageId : 1
             * messageType : TEST
             * read : 0
             * status : 1
             * updatedAt : 1542358128486
             */

            var createdAt: Long,
            var id: Int,
            var message: String,
            var messageId: Int,
            var messageType: String,
            var read: Int,
            var status: Int,
            var updatedAt: Long = 0
    )
}

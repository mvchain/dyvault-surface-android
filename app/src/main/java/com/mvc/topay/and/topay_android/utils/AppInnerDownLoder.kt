package com.mvc.topay.and.topay_android.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.util.Log

import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.MyApplication

import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author Administrator
 * @date 2017/10/30
 */

object AppInnerDownLoder {
    val SD_FOLDER = Environment.getExternalStorageDirectory().toString() + "/VersionChecker/"
    private val TAG = AppInnerDownLoder::class.java.simpleName

    /**
     * Download APK from the server
     */
    fun downLoadApk(mContext: Context, downURL: String, appName: String) {
        val pd: ProgressDialog // 进度条对话框
        pd = ProgressDialog(mContext)
        // 必须一直下载完，不可取消
        pd.setCancelable(false)
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        pd.setMessage("正在下载安装包，请稍后")
        pd.setTitle("版本升级")
        pd.show()
        object : Thread() {
            override fun run() {
                try {
                    val file = downloadFile(downURL, appName, pd)
                    Thread.sleep(1000)
                    installApk(mContext, file)
                    // 结束掉进度条对话框
                    pd.dismiss()
                } catch (e: Exception) {
                    LogUtils.e(e.message)
                    pd.dismiss()
                }

            }
        }.start()
    }

    /**
     * Download the latest update file from the server
     *
     * @param path Download path
     * @param pd  progress bar
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun downloadFile(path: String, appName: String, pd: ProgressDialog): File {
        // If they are equal, the current sdcard is mounted on the phone and is available.
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val url = URL(path)
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            // Get the size of the file
            pd.max = conn.contentLength / 1024
            val `is` = conn.inputStream
            val fileName = "$SD_FOLDER$appName.apk"
            val file = File(fileName)
            // Directory does not exist to create a directory
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            val fos = FileOutputStream(file)
            val bis = BufferedInputStream(`is`)
            val buffer = ByteArray(1024)
            var len: Int
            var total = 0
            while (true) {
                len = bis.read(buffer)
                if (len == -1) {
                    break
                }
                fos.write(buffer, 0, len)
                total += len
                // Get current downloads
                pd.progress = total / 1024
            }
            fos.close()
            bis.close()
            `is`.close()
            return file
        } else {
            throw IOException("未发现有SD卡")
        }
    }

    /**
     * install apk
     */
    private fun installApk(mContext: Context, file: File) {
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //在AndroidManifest中的android:authorities值
            val apkUri = FileProvider.getUriForFile(mContext, MyApplication.getApplication().packageName + ".fileprovider", file)
            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            install.setDataAndType(apkUri, "application/vnd.android.package-archive")
            mContext.startActivity(install)
        } else {
            val fileUri = Uri.fromFile(file)
            val it = Intent()
            it.action = Intent.ACTION_VIEW
            it.setDataAndType(fileUri, "application/vnd.android.package-archive")
            // 防止打不开应用
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(it)
        }

    }

    /**
     * Get the app version（versionName）
     *
     * @return This application version number
     */

    private fun getLocalVersion(context: Context): Double {
        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Failed to get the app version，reason：" + e.message)
            return 0.0
        }

        return java.lang.Double.valueOf(info!!.versionName)
    }

    /**
     * Get the app versionName
     *
     * @return This application version name
     */
    fun getVersionName(context: Context): String {
        return getPackageInfo(context)!!.versionName
    }

    private fun getPackageInfo(context: Context): PackageInfo? {
        var pi: PackageInfo? = null
        try {
            val pm = context.packageManager
            pi = pm.getPackageInfo(context.packageName,
                    PackageManager.GET_CONFIGURATIONS)

            return pi
        } catch (e: Exception) {
            LogUtils.d("AppInnerDownLoder", e.message)
        }

        return pi
    }

    /**
     * byte: Convert to kb (kilobytes) and mb (megabytes) according to length
     *
     * @param bytes
     * @return
     */
    fun bytes2kb(bytes: Long): String {
        val filesize = BigDecimal(bytes)
        val megabyte = BigDecimal(1024 * 1024)
        var returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .toFloat()
        if (returnValue > 1) {
            return returnValue.toString() + "MB"
        }
        val kilobyte = BigDecimal(1024)
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).toFloat()
        return returnValue.toString() + "KB"
    }
}
